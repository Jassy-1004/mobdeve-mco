package com.mobdeve.s13.Group17.MCO2

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.shashank.login.DatabaseHelper
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var signUpHere: TextView
    private lateinit var login: Button

    private lateinit var emailHolder: String
    private lateinit var passwordHolder: String
    var editTextEmptyHolder: Boolean = false
    private lateinit var sqLiteDatabaseObj: SQLiteDatabase
    private lateinit var sqLiteHelper: DatabaseHelper
    private lateinit var cursor: Cursor
    var tempPassword: String = "NOT_FOUND"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        sqLiteHelper = DatabaseHelper(this)

        emailHolder=viewBinding.loginemailtext.text.toString()
        passwordHolder=viewBinding.loginpasswordtext.text.toString()

        // clicking the signUp button would start activity for Register1
        signUpHere = viewBinding.signuphere
        signUpHere.setOnClickListener{
            val intent : Intent = Intent(this, Register1::class.java);
            startActivity(intent)
            finish()
        }

        login = viewBinding.loginbtn1
        login.setOnClickListener{

            // check if the data is empty or data has a incorrect format, then asks the user to enter the correct format
           /* if(!TextUtils.isEmpty(viewBinding.loginemailtext.text.toString()) &&
                !TextUtils.isEmpty(viewBinding.loginpasswordtext.text.toString())){ // check if edit texts are not empty
                if(Patterns.EMAIL_ADDRESS.matcher(viewBinding.loginemailtext.text).matches()){ //check if email is in the correct format
                    val intent : Intent = Intent(this, MainActivity::class.java);
                    startActivity(intent)
                    finishAffinity()
                }else{
                    Toast.makeText(
                        this,
                        "please enter with the correct email format",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else if(TextUtils.isEmpty(viewBinding.loginemailtext.text.toString()) && TextUtils.isEmpty(viewBinding.loginpasswordtext.text.toString())){
                Toast.makeText(
                    this,
                    "please enter your account credentials",
                    Toast.LENGTH_LONG
                ).show()
            } else if (TextUtils.isEmpty(viewBinding.loginemailtext.text.toString())) {
                Toast.makeText(
                    this,
                    "please enter your email",
                    Toast.LENGTH_LONG
                ).show()
            } else if (TextUtils.isEmpty(viewBinding.loginpasswordtext.text.toString())) {
                Toast.makeText(
                    this,
                    "please enter your password",
                    Toast.LENGTH_LONG
                ).show()
            }*/
            checkEditTextStatus(viewBinding)
            loginFunction()
        }
    }

    fun loginFunction() {
        if (editTextEmptyHolder) {
            // Opening SQLite database write permission.
            sqLiteDatabaseObj = sqLiteHelper.writableDatabase
            // Adding search email query to cursor.
            cursor = sqLiteDatabaseObj.query(
                DatabaseHelper.TABLE_NAME,
                null,
                " " +DatabaseHelper.Table_Column_2_Email + "=?",
                arrayOf(emailHolder),
                null,
                null,
                null
            )
            while (cursor.moveToNext()) {
                val columnIndex = cursor.getColumnIndex(DatabaseHelper.Table_Column_3_Password)
                if (columnIndex >= 0) {
                    if (cursor.isFirst) {
                        cursor.moveToFirst()
                        // Storing Password associated with entered email.
                            tempPassword = cursor.getString(columnIndex)
                        // Closing cursor.
                        cursor.close()
                    }
                }
                else {
                        Log.e("CursorError", "Column not found in cursor: ${DatabaseHelper.Table_Column_3_Password}")
                    }
            }
            // Calling method to check final result ..
            checkFinalResult()
        } else {
            //If any of login EditText empty then this block will be executed.
            Toast.makeText(this, "Please Enter Email or Password.", Toast.LENGTH_LONG).show()
        }
    }

    fun checkEditTextStatus(viewBinding: ActivityLoginBinding) {
        // Getting value from All EditText and storing into String Variables.
        emailHolder = viewBinding.loginemailtext.text.toString()
        passwordHolder = viewBinding.loginpasswordtext.text.toString()
        // Checking EditText is empty or no using TextUtils.
        editTextEmptyHolder = !TextUtils.isEmpty(emailHolder) && !TextUtils.isEmpty(passwordHolder)
    }

    // Checking entered password from SQLite database email associated password.
    fun checkFinalResult() {
        if (tempPassword.equals(passwordHolder, ignoreCase = true)) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_LONG).show()
            // Going to Dashboard activity after login success message.
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        } else {
            Toast.makeText(
                this,
                "Email or Password is Wrong, Please Try Again.",
                Toast.LENGTH_LONG
            ).show()
        }
        tempPassword = "NOT_FOUND"
    }



}