package com.mobdeve.s13.Group17.MCO2

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityEditprofileBinding
import java.util.*

class EditProfile : AppCompatActivity() {
    private lateinit var done: Button
    private lateinit var discard: Button

    companion object {
        const val USERNAME_KEY = "USERNAME_KEY"
        const val NAME_KEY = "NAME_KEY"
        const val BIO_KEY = "BIO_KEY"
    }

    private var datePickerDialog: DatePickerDialog? = null
    private lateinit var dateButton2: Button
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityEditprofileBinding =
            ActivityEditprofileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // putting data to views
        viewBinding.profileUsername.setText(intent.getStringExtra(USERNAME_KEY))
        viewBinding.profileName.setText(intent.getStringExtra(NAME_KEY))
        viewBinding.profileBio.setText(intent.getStringExtra(BIO_KEY))

        // clicking the done button would start activity to MyProfileActivity
        done = viewBinding.editComplete
        done.setOnClickListener {
            val intent: Intent = Intent(this, MyProfileActivity::class.java);
            startActivity(intent)
            finishAffinity()
        }

        // clicking the discard button would finish the current activity
        discard = viewBinding.discardBtn
        discard.setOnClickListener {
            finish()
        }


        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item clicks here
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }
                R.id.nav_books -> {
                    // Do something for menu item 2
                    startActivity(Intent(this, MyLibraryActivity::class.java))
                    finishAffinity()
                }
                R.id.nav_profile -> {
                    startActivity(Intent(this, MyProfileActivity::class.java))
                    finishAffinity()
                }
                R.id.nav_logout -> {
                    startActivity(Intent(this, StartPage::class.java))
                    finishAffinity()
                }
            }
            // Close the drawer
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

}