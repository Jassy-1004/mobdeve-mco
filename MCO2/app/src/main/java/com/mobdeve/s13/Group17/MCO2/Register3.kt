package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Register3 : AppCompatActivity() {
    private lateinit var Reg3Btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register3)

        Reg3Btn = findViewById(R.id.nextpage3)
        Reg3Btn.setOnClickListener{
            val intent : Intent = Intent(this, Register4::class.java);
            startActivity(intent)
        }
    }
}