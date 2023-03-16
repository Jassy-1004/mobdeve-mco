package com.mobdeve.s13.Group17.MCO2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
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
            if(!TextUtils.isEmpty(viewBinding.usertext.text.toString()) &&
                !TextUtils.isEmpty(viewBinding.emailtext.text.toString())){
                if(Patterns.EMAIL_ADDRESS.matcher(viewBinding.emailtext.text).matches()){
                    val intent : Intent = Intent(this, Register2::class.java);
                    startActivity(intent)
                }else{
                    Toast.makeText(
                        this,
                        "please enter with the correct email format",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } else if(TextUtils.isEmpty(viewBinding.usertext.text.toString()) &&
                TextUtils.isEmpty(viewBinding.emailtext.text.toString())){
                Toast.makeText(
                    this,
                    "please enter a username and an email",
                    Toast.LENGTH_LONG
                ).show()
            } else if (TextUtils.isEmpty(viewBinding.usertext.text.toString())) {
                Toast.makeText(
                    this,
                    "please enter a username",
                    Toast.LENGTH_LONG
                ).show()
            } else if (TextUtils.isEmpty(viewBinding.emailtext.text.toString())) {
                Toast.makeText(
                    this,
                    "please enter an email",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}