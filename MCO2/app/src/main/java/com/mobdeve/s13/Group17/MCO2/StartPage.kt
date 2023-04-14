package com.mobdeve.s13.Group17.MCO2

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityStartpageBinding

/*
MCO4
GROUP 17
CHUA, JASMIN
SHI, KAYE
TAN, HAILY
*/

class StartPage : AppCompatActivity() {

    // declare the variables
    private lateinit var homeRegBtn: Button
    private lateinit var loginbtn:Button
    companion object {
        private var isLoggedIn = false

        @JvmStatic
        fun getIsLoggedIn(): Boolean {
            return isLoggedIn
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // User is logged in, shows the screen based on the users last activity
        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        isLoggedIn = sharedPrefs.getBoolean("isLoggedIn", false)
        val previousActivity = sharedPrefs.getString("previous_activity", null)
        if (isLoggedIn) {
            if (previousActivity != null) {
                val intent = Intent(this, Class.forName(previousActivity))
                startActivity(intent)
                finish()
            }
            else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        } else {
            // User is not logged in, show the start page screen
            val viewBinding: ActivityStartpageBinding =
                ActivityStartpageBinding.inflate(layoutInflater)
            setContentView(viewBinding.root)


            // clicking the register button would start the Register activity
            homeRegBtn = viewBinding.registerbtn
            homeRegBtn.setOnClickListener {
                val intent: Intent = Intent(this, Register::class.java);
                startActivity(intent)
            }

            // clicking the login button would start the Login activity
            loginbtn = viewBinding.loginbtn
            loginbtn.setOnClickListener {
                val intent: Intent = Intent(this, Login1::class.java);
                startActivity(intent)
            }


        }

    }



}