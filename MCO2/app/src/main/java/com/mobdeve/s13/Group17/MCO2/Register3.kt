package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityRegister3Binding

class Register3 : AppCompatActivity() {
    private lateinit var Reg3Btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityRegister3Binding = ActivityRegister3Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        Reg3Btn = viewBinding.nextpage3
        Reg3Btn.setOnClickListener{
            val intent : Intent = Intent(this, Register4::class.java);
            startActivity(intent)
        }
    }
}