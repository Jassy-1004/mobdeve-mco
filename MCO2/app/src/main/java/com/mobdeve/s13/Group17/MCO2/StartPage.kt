package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class StartPage : AppCompatActivity() {
    private lateinit var homeRegBtn: Button
    private lateinit var loginbtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startpage)

        homeRegBtn = findViewById(R.id.registerbtn)
        homeRegBtn.setOnClickListener{
            val intent : Intent = Intent(this, Register1::class.java);
            startActivity(intent)
        }

        loginbtn = findViewById(R.id.loginbtn)
        loginbtn.setOnClickListener{
            val intent : Intent = Intent(this, Login::class.java);
            startActivity(intent)
        }


    }


}