package com.mobdeve.s13.Group17.MCO2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var signUpHere: TextView
    private lateinit var login: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        signUpHere = viewBinding.signuphere
        signUpHere.setOnClickListener{
            val intent : Intent = Intent(this, Register1::class.java);
            startActivity(intent)
            finish()
        }

        login = viewBinding.loginbtn1
        login.setOnClickListener{
            if(!TextUtils.isEmpty(viewBinding.loginemailtext.text.toString()) && !TextUtils.isEmpty(viewBinding.loginpasswordtext.text.toString())){
                if(Patterns.EMAIL_ADDRESS.matcher(viewBinding.loginemailtext.text).matches()){
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
            }
        }
    }
}