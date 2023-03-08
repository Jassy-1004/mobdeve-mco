package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Register2 : AppCompatActivity() {
    private lateinit var Reg2Btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)

        Reg2Btn = findViewById(R.id.nextpage2)
        Reg2Btn.setOnClickListener{
            val intent : Intent = Intent(this, Register3::class.java);
            startActivity(intent)
        }
    }
}