package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityRegister2Binding

class Register2 : AppCompatActivity() {
    private lateinit var Reg2Btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityRegister2Binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        Reg2Btn = viewBinding.nextpage2
        Reg2Btn.setOnClickListener{
            val intent : Intent = Intent(this, Register3::class.java);
            startActivity(intent)
        }
    }
}