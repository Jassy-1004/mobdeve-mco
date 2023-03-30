package com.mobdeve.s13.Group17.MCO2

import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.UserInfo
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityEditprofileBinding


class EditProfile : AppCompatActivity() {
    private lateinit var done: Button
    private lateinit var discard: Button

    companion object {
        const val USERNAME_KEY = "USERNAME_KEY"
        const val NAME_KEY = "NAME_KEY"
        const val BIO_KEY = "BIO_KEY"
        private const val UNAME = "Username"
    }

    private var datePickerDialog: DatePickerDialog? = null
    private lateinit var dateButton2: Button
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    val db = FirebaseFirestore.getInstance();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityEditprofileBinding =
            ActivityEditprofileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val username = this.intent.getStringExtra(MyProfileActivity.UNAME).toString()
        Log.d(TAG, "DocumentSnapshot data: $username")

        db.collection("UserInfo").whereEqualTo("Username", username)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        viewBinding.profileName.setText(document.data["Name"].toString())
                        viewBinding.profileBio.setText(document.data["Bio"].toString())
                        Log.d(TAG, document.id + " => " + document.data)
                    }
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }

        // putting data to views
        //viewBinding.profileUsername.setText(intent.getStringExtra(USERNAME_KEY))
        //viewBinding.profileName.setText(intent.getStringExtra(NAME_KEY))
        //viewBinding.profileBio.setText(intent.getStringExtra(BIO_KEY))

        // clicking the done button would start activity to MyProfileActivity
        done = viewBinding.editComplete
        done.setOnClickListener {
            val intent: Intent = Intent(this, MyProfileActivity::class.java)

            var id: String = ""
            val bio = viewBinding.profileBio.text.toString()
            val name = viewBinding.profileName.text.toString()

            db.collection("UserInfo").whereEqualTo("Username", username).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            id = document.id
                            Log.w(TAG, "id: $id")
                        }

                        db.runTransaction { transaction ->
                            val snapshot = transaction.get(db.collection("UserInfo").document(id))
                            transaction.update(db.collection("UserInfo").document(id), "Name", name,"Bio", bio)
                            // Success
                            null
                        }.addOnSuccessListener {
                            Log.d(TAG, "Transaction success!")
                            intent.putExtra(EditProfile.UNAME, this.intent.getStringExtra(Login1.UNAME).toString())
                            startActivity(intent)
                            finishAffinity()
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.exception)
                    }
                }
        }
        // clicking the discard button would finish the current activity
        discard = viewBinding.discardBtn
        discard.setOnClickListener {
            finish()
        }

/*
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
                    val home = Intent(this, MainActivity::class.java)
                    home.putExtra(MainActivity.UNAME, this.intent.getStringExtra(Login1.UNAME).toString())
                    startActivity(home)
                    finishAffinity()
                }
                R.id.nav_books-> {
                    // Do something for menu item 2
                    val lib = Intent(this, MyLibraryActivity::class.java)
                    lib.putExtra(MyLibraryActivity.UNAME, this.intent.getStringExtra(Login1.UNAME).toString())
                    startActivity(lib)
                    finishAffinity()
                }
                R.id.nav_profile->{
                    val profile = Intent(this, MyProfileActivity::class.java)
                    profile.putExtra(MyProfileActivity.UNAME, this.intent.getStringExtra(Login1.UNAME).toString())
                    startActivity(profile)
                    finishAffinity()
                }
                R.id.nav_logout->{
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
        } else super.onOptionsItemSelected(item)*/
    }

}