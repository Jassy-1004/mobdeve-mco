package com.mobdeve.s13.Group17.MCO2

import android.annotation.SuppressLint
import android.app.Activity
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityLoginBinding
import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

import com.mobdeve.s13.Group17.MCO2.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    companion object{
        const val FIRSTNAME_TITLE_KEY = "FIRSTNAME_TITLE_KEY"
        const val LASTNAME_KEY = "LASTNAME_KEY"
        const val USERNAME_KEY = "USERNAME_KEY"
        const val GENDER_KEY = "GENDER_KEY"
        const val BIRTHDAY_KEY = "BIRTHDAY_KEY"
        const val BIO_KEY = "BIO_KEY"
    }

    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var editProfile:Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val homepage : Intent = Intent(this, MainActivity::class.java);
        val logout : Intent = Intent(this, StartPage::class.java);
        val library: Intent = Intent (this,MyLibraryActivity::class.java);
        val profile: Intent = Intent (this,ProfileActivity::class.java);

        val viewBinding: ActivityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        editProfile = viewBinding.editProfile
        editProfile.setOnClickListener {
            val intent: Intent = Intent(this, EditProfile::class.java);
            startActivity(intent)
            finish()

    }

        bottomNavigationView= viewBinding.bottomNavigationView

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home-> {
                    startActivity(homepage)
                    finish()
                }
                R.id.nav_logout->{
                    startActivity(logout)
                    finishAffinity()
                }
                R.id.nav_books-> {
                    startActivity(library)
                    finish()
                }
                R.id.nav_profile-> {
                    startActivity(profile)
                    finish()
                }
            }
            true
        }

    }
}