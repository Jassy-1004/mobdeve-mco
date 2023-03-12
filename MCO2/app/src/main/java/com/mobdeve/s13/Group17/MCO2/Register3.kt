package com.mobdeve.s13.Group17.MCO2

import android.R.layout
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityRegister3Binding



class Register3 : AppCompatActivity() {
    private lateinit var Reg3Btn: Button
    private lateinit var dpBtn: ImageButton
    private var popupWindow: PopupWindow? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding : ActivityRegister3Binding = ActivityRegister3Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        dpBtn=viewBinding.dpbtn
        dpBtn.setOnClickListener {
            // Create the popup window
            val popupWindow = PopupWindow(this)

            // Set the layout for the popup window
            val layoutInflater = LayoutInflater.from(this)
            val popupView = layoutInflater.inflate(R.layout.dp_popup, null)

            // Find the ImageButton view in the layout
            val av1 = popupView.findViewById<ImageButton>(R.id.avatar1)
            val av2 = popupView.findViewById<ImageButton>(R.id.avatar2)
            val av3 = popupView.findViewById<ImageButton>(R.id.avatar3)
            val av4 = popupView.findViewById<ImageButton>(R.id.avatar4)

            // Set a click listener for the ImageButton view
            av1.setOnClickListener {
                // Do something when the ImageButton is clicked in the popup window
                // (e.g. dismiss the popup, launch a new activity, etc.)
                dpBtn.setImageResource(R.drawable.tiger)

                popupWindow.dismiss()
                // Launch a new activity (optional)
            }

            av2.setOnClickListener {
                // Do something when the ImageButton is clicked in the popup window
                // (e.g. dismiss the popup, launch a new activity, etc.)
                dpBtn.setImageResource(R.drawable.whale)

                popupWindow.dismiss()
                // Launch a new activity (optional)
            }

            av3.setOnClickListener {
                // Do something when the ImageButton is clicked in the popup window
                // (e.g. dismiss the popup, launch a new activity, etc.)
                dpBtn.setImageResource(R.drawable.sealion)

                popupWindow.dismiss()
                // Launch a new activity (optional)
            }
            av4.setOnClickListener {
                // Do something when the ImageButton is clicked in the popup window
                // (e.g. dismiss the popup, launch a new activity, etc.)
                dpBtn.setImageResource(R.drawable.sheep)

                popupWindow.dismiss()
                // Launch a new activity (optional)
            }

            popupWindow.contentView = popupView

            // Set the size and position of the popup window
            popupWindow.width = ViewGroup.LayoutParams.WRAP_CONTENT
            popupWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT
            popupWindow.isFocusable = true
            popupWindow.showAtLocation(dpBtn, Gravity.CENTER, 0, 0)
        }



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
                    "please enter your last name",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

    }
}