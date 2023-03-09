package com.mobdeve.s13.Group17.MCO2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityRegister1Binding

class Register1 : AppCompatActivity() {
    private lateinit var Reg1Btn: Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityRegister1Binding = ActivityRegister1Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        Reg1Btn = viewBinding.nextpage1
        Reg1Btn.setOnClickListener{
            val intent : Intent = Intent(this, Register2::class.java);
            startActivity(intent)
        }

    }

}