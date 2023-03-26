package com.mobdeve.s13.Group17.MCO2

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shashank.login.DatabaseHelper
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityRegister1Binding

class Register1 : AppCompatActivity() {
    private lateinit var Reg1Btn: Button
    private lateinit var NameHolder: String
    private lateinit var EmailHolder: String
    private lateinit var PasswordHolder: String
    private lateinit var ConPassHolder: String
    private lateinit var FirstNameHolder: String
    private lateinit var LastNameHolder: String

    private var EditTextEmptyHolder: Int =0
    private lateinit var sqLiteDatabaseObj: SQLiteDatabase
    private lateinit var sqLiteHelper: DatabaseHelper
    private lateinit var cursor: Cursor
    private lateinit var SQLiteDataBaseQueryHolder:String
    private var F_Result = "Not_Found"


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityRegister1Binding = ActivityRegister1Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        sqLiteHelper = DatabaseHelper(this)

        NameHolder=viewBinding.usertext.text.toString()
        EmailHolder=viewBinding.emailtext.text.toString()
        PasswordHolder=viewBinding.editTextTextPassword.text.toString()
        ConPassHolder=viewBinding.editTextTextPassword2.text.toString()
        LastNameHolder=viewBinding.lastnametext.text.toString()
        FirstNameHolder=viewBinding.firstnametext.text.toString()

        // clicking the next button would start activity of register2 after checking if data entered is the correct format
        Reg1Btn = viewBinding.nextpage4
        Reg1Btn.setOnClickListener{
            /*if(!TextUtils.isEmpty(viewBinding.usertext.text.toString()) &&
                !TextUtils.isEmpty(viewBinding.emailtext.text.toString()) && !TextUtils.isEmpty(viewBinding.editTextTextPassword.text.toString()) &&
                !TextUtils.isEmpty(viewBinding.editTextTextPassword2.text.toString()) && !TextUtils.isEmpty(viewBinding.firstnametext.text.toString()) &&
                !TextUtils.isEmpty(viewBinding.lastnametext.text.toString())){
                if(Patterns.EMAIL_ADDRESS.matcher(viewBinding.emailtext.text).matches()){

                }else{
                    Toast.makeText(
                        this,
                        "please enter with the correct email format",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else if(TextUtils.isEmpty(viewBinding.usertext.text.toString()) &&
                TextUtils.isEmpty(viewBinding.emailtext.text.toString())){
                Toast.makeText(
                    this,
                    "please enter a username and an email",
                    Toast.LENGTH_LONG
                ).show()
            } else if (TextUtils.isEmpty(viewBinding.usertext.text.toString())) {
                Toast.makeText(
                    this,
                    "please enter a username",
                    Toast.LENGTH_LONG
                ).show()
            } else if (TextUtils.isEmpty(viewBinding.emailtext.text.toString())) {
                Toast.makeText(
                    this,
                    "please enter an email",
                    Toast.LENGTH_LONG
                ).show()
            }
            else if (viewBinding.editTextTextPassword.text.toString() != viewBinding.editTextTextPassword2.text.toString()){
                    Toast.makeText(
                        this,
                        "password and confirm password do not match",
                        Toast.LENGTH_LONG
                    ).show()

            } else if (TextUtils.isEmpty(viewBinding.editTextTextPassword.text.toString())) {
                Toast.makeText(
                    this,
                    "please enter a password",
                    Toast.LENGTH_LONG
                ).show()
            } else if (TextUtils.isEmpty(viewBinding.editTextTextPassword2.text.toString())) {
                Toast.makeText(
                    this,
                    "please enter the password again",
                    Toast.LENGTH_LONG
                ).show()
            }
            else if (TextUtils.isEmpty(viewBinding.firstnametext.text.toString())) {
                Toast.makeText(
                    this,
                    "please enter your first name",
                    Toast.LENGTH_LONG
                ).show()
            } else if (TextUtils.isEmpty(viewBinding.lastnametext.text.toString())) {
                Toast.makeText(
                    this,
                    "please enter your last name",
                    Toast.LENGTH_LONG
                ).show()
            }*/

            SQLiteDataBaseBuild()

            SQLiteTableBuild()

            checkEditTextStatus(viewBinding)

            CheckingEmailAlreadyExistsOrNot()

            EmptyEditTextAfterDataInsert(viewBinding)

        }
    }
    fun SQLiteDataBaseBuild() {
        sqLiteDatabaseObj = openOrCreateDatabase(DatabaseHelper.DATABASE_NAME, Context.MODE_PRIVATE, null)
    }

    // SQLite table build method.
    fun SQLiteTableBuild() {
        sqLiteDatabaseObj.execSQL("CREATE TABLE IF NOT EXISTS ${DatabaseHelper.TABLE_NAME}(${DatabaseHelper.Table_Column_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${DatabaseHelper.Table_Column_1_username} VARCHAR, ${DatabaseHelper.Table_Column_2_Email} VARCHAR, ${DatabaseHelper.Table_Column_3_Password} VARCHAR, ${DatabaseHelper.Table_Column_4_ConPassword} VARCHAR, ${DatabaseHelper.Table_Column_5_firstName} VARCHAR, ${DatabaseHelper.Table_Column_6_lastName} VARCHAR)")
    }


    // Insert data into SQLite database method.
    // Insert data into SQLite database method.
    // Insert data into SQLite database method.
    fun InsertDataIntoSQLiteDatabase() {
        // If editText is not empty then this block will executed.
        val intent : Intent = Intent(this, Login::class.java);
        if (EditTextEmptyHolder==3) {

            // SQLite query to insert data into table.
           SQLiteDataBaseQueryHolder =
                "INSERT INTO " + DatabaseHelper.TABLE_NAME + " (username,email,password,conpassword,firstname,lastname) VALUES('$NameHolder', '$EmailHolder', '$PasswordHolder', '$ConPassHolder', '$FirstNameHolder', '$LastNameHolder');"

            // Executing query.
            sqLiteDatabaseObj.execSQL(SQLiteDataBaseQueryHolder)

            // Closing SQLite database object.
            sqLiteDatabaseObj.close()

            // Printing toast message after done inserting.
            Toast.makeText(
                this@Register1,
                "User Registered Successfully",
                Toast.LENGTH_LONG

            ).show()
            startActivity(intent)
        }

       else if(EditTextEmptyHolder==1){

            Toast.makeText(
                this@Register1,
                "Please enter correct email format",
                Toast.LENGTH_LONG
            ).show()

        }

       else if (EditTextEmptyHolder==2) {
            Toast.makeText(
                this@Register1,
                "Password and Confirm Password does not match",
                Toast.LENGTH_LONG
            ).show()

        }

        else if(EditTextEmptyHolder==3) {
            // Printing toast message if any of EditText is empty.
            Toast.makeText(
                this@Register1,
                "Please Fill All The Required Fields.",
                Toast.LENGTH_LONG
            ).show()


        }
        Log.d(ContentValues.TAG,"Inserted")
    }



    // Empty edittext after done inserting process method.
    fun EmptyEditTextAfterDataInsert(viewBinding:ActivityRegister1Binding) {

        viewBinding.usertext.text.clear()
        viewBinding.emailtext.text.clear()
        viewBinding.editTextTextPassword.text.clear()
        viewBinding.editTextTextPassword2.text.clear()
        viewBinding.lastnametext.text.clear()
        viewBinding.firstnametext.text.clear()


    }

    // Method to check EditText is empty or Not.
    fun checkEditTextStatus(viewBinding: ActivityRegister1Binding) {
// Getting value from All EditText and storing into String Variables.
        NameHolder = viewBinding.usertext.text.toString()
        EmailHolder = viewBinding.emailtext.text.toString()
        PasswordHolder = viewBinding.editTextTextPassword.text.toString()
        ConPassHolder = viewBinding.editTextTextPassword2.text.toString()
        LastNameHolder = viewBinding.lastnametext.text.toString()
        FirstNameHolder= viewBinding.firstnametext.text.toString()

        if (TextUtils.isEmpty(NameHolder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder) || TextUtils.isEmpty(ConPassHolder) || TextUtils.isEmpty(FirstNameHolder) || TextUtils.isEmpty(LastNameHolder)) {
            EditTextEmptyHolder = 0
        }

       else if(!Patterns.EMAIL_ADDRESS.matcher(EmailHolder).matches()){
            EditTextEmptyHolder=1

        }

        else if (PasswordHolder != ConPassHolder) {
            EditTextEmptyHolder=2

        }

        else {
            EditTextEmptyHolder = 3
        }
    }


    // Checking Email is already exists or not.
    fun CheckingEmailAlreadyExistsOrNot() {
        // Opening SQLite database write permission.
        sqLiteDatabaseObj = sqLiteHelper.writableDatabase
        // Adding search email query to cursor.
        cursor = sqLiteDatabaseObj.query(DatabaseHelper.TABLE_NAME, null, " " + DatabaseHelper.Table_Column_2_Email + "=?", arrayOf(EmailHolder), null, null, null)
        while (cursor.moveToNext() == true) {
            if (cursor.isFirst) {
                cursor.moveToFirst()
                // If Email is already exists then Result variable value set as Email Found.
                F_Result = "Email Found"
                // Closing cursor.
                cursor.close()
            }
        }
        // Calling method to check final result and insert data into SQLite database.
        CheckFinalResult()
    }

    // Checking result
    fun CheckFinalResult() {
        // Checking whether email is already exists or not.
        if (F_Result.equals("Email Found", ignoreCase = true)) {
            // If email is exists then toast msg will display.
            Toast.makeText(this,"Email Already Exists", Toast.LENGTH_LONG).show()
        } else {
            // If email already dose n't exists then user registration details will entered to SQLite database.
            InsertDataIntoSQLiteDatabase()
        }
        F_Result = "Not_Found"
    }
}

