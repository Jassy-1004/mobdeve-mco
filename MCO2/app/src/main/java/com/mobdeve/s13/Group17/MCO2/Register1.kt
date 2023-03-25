package com.mobdeve.s13.Group17.MCO2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityRegister1Binding

class Register1 : AppCompatActivity() {
    private lateinit var Reg1Btn: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityRegister1Binding = ActivityRegister1Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // clicking the next button would start activity of register2 after checking if data entered is the correct format
        Reg1Btn = viewBinding.nextpage4
        Reg1Btn.setOnClickListener{
            if(!TextUtils.isEmpty(viewBinding.usertext.text.toString()) &&
                !TextUtils.isEmpty(viewBinding.emailtext.text.toString()) && !TextUtils.isEmpty(viewBinding.editTextTextPassword.text.toString()) &&
                !TextUtils.isEmpty(viewBinding.editTextTextPassword2.text.toString()) && !TextUtils.isEmpty(viewBinding.firstnametext.text.toString()) &&
                !TextUtils.isEmpty(viewBinding.lastnametext.text.toString())){
                if(Patterns.EMAIL_ADDRESS.matcher(viewBinding.emailtext.text).matches()){
                    val intent : Intent = Intent(this, Login::class.java);
                    startActivity(intent)
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
            }

        }
    }

}