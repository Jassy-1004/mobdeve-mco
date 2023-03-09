package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityRegister3Binding

class Register3 : AppCompatActivity() {
    private lateinit var Reg3Btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityRegister3Binding = ActivityRegister3Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        Reg3Btn = viewBinding.nextpage3
        Reg3Btn.setOnClickListener{
            if(!TextUtils.isEmpty(viewBinding.firstnametext.text.toString()) && !TextUtils.isEmpty(viewBinding.lastnametext.text.toString())){
                val intent : Intent = Intent(this, Register4::class.java);
                startActivity(intent)
            } else if (TextUtils.isEmpty(viewBinding.firstnametext.text.toString())) {
                Toast.makeText(
                    this,
                    "please enter your first name",
                    Toast.LENGTH_LONG
                ).show()
            } else if (TextUtils.isEmpty(viewBinding.lastnametext.text.toString())) {
                Toast.makeText(
                    this,
                    "please enter your last name1",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}