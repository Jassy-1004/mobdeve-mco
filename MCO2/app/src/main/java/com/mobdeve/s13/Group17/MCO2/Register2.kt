package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityRegister2Binding

class Register2 : AppCompatActivity() {
    private lateinit var Reg2Btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityRegister2Binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        Reg2Btn = viewBinding.nextpage2
        Reg2Btn.setOnClickListener{
            if(!TextUtils.isEmpty(viewBinding.editTextTextPassword.text.toString()) && !TextUtils.isEmpty(viewBinding.editTextTextPassword2.text.toString())){
                if(viewBinding.editTextTextPassword.text.toString() == viewBinding.editTextTextPassword2.text.toString()){
                    val intent : Intent = Intent(this, Register3::class.java);
                    startActivity(intent)
                } else{
                    Toast.makeText(
                        this,
                        "password and confirm password do not match",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else if (TextUtils.isEmpty(viewBinding.editTextTextPassword.text.toString())) {
                Toast.makeText(
                    this,
                    "please enter a password",
                    Toast.LENGTH_LONG
                ).show()
            } else if (TextUtils.isEmpty(viewBinding.editTextTextPassword2.text.toString())) {
                Toast.makeText(
                    this,
                    "please enter the password again",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}