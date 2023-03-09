package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityStartpageBinding

class StartPage : AppCompatActivity() {
    private lateinit var homeRegBtn: Button
    private lateinit var loginbtn:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityStartpageBinding = ActivityStartpageBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        homeRegBtn = viewBinding.registerbtn
        homeRegBtn.setOnClickListener{
            val intent : Intent = Intent(this, Register1::class.java);
            startActivity(intent)
        }

        loginbtn = viewBinding.loginbtn
        loginbtn.setOnClickListener{
            val intent : Intent = Intent(this, Login::class.java);
            startActivity(intent)
        }


    }


}