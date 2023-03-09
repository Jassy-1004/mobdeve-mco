package com.mobdeve.s13.Group17.MCO2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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
        }

        login = viewBinding.loginbtn1
        login.setOnClickListener{
            val intent : Intent = Intent(this, MainActivity::class.java);
            startActivity(intent)
        }
    }
}