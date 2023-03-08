package com.mobdeve.s13.Group17.MCO2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Register1 : AppCompatActivity() {
    private lateinit var Reg1Btn: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register1)

        Reg1Btn = findViewById(R.id.nextpage1)
        Reg1Btn.setOnClickListener{
            val intent : Intent = Intent(this, Register2::class.java);
            startActivity(intent)
        }

    }

}