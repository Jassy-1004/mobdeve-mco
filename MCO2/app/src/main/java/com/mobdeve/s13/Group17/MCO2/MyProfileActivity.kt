package com.mobdeve.s13.Group17.MCO2

import android.content.ContentValues
import android.content.Context
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
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.Group17.MCO2.Login1.getUserName
import com.mobdeve.s13.Group17.MCO2.StartPage.Companion.getIsLoggedIn
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityProfileBinding


class MyProfileActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MyProfileActivity"
        const val UNAME = "Username"
    }



    //navigation
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    private lateinit var editProfile: Button

    val db = FirebaseFirestore.getInstance();

    var user=getUserName()

    var isUserLoggedIn = getIsLoggedIn()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val username = sharedPrefs.getString("username", "")

        Log.d(TAG, "DocumentSnapshot data: ${username}")

        db.collection("UserInfo").whereEqualTo("Username", username)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        viewBinding.profileName.text = document.data["Name"] as CharSequence?
                        viewBinding.profileUsername.text = document.data["Username"] as CharSequence?
                        viewBinding.profileBio.text = document.data["Bio"] as CharSequence?
                        Log.d(TAG, document.id + " => " + document.data)
                    }
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }

        editProfile = viewBinding.editProfile
        editProfile.setOnClickListener {
            val intent: Intent = Intent(this, EditProfile::class.java);
            intent.putExtra(UNAME, this.intent.getStringExtra(Login1.UNAME).toString())
            startActivity(intent)
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
                    val home = Intent(this, MainActivity::class.java)
                    home.putExtra(MainActivity.UNAME, this.intent.getStringExtra(UNAME).toString())
                    startActivity(home)
                    finishAffinity()
                }
                R.id.nav_books-> {
                    // Do something for menu item 2
                    val lib = Intent(this, MyLibraryActivity::class.java)
                    lib.putExtra(
                        MyLibraryActivity.UNAME, this.intent.getStringExtra(
                            UNAME
                        ).toString())
                    startActivity(lib)
                    finishAffinity()
                }
                R.id.nav_profile->{
                    val profile = Intent(this, MyProfileActivity::class.java)
                    profile.putExtra(UNAME, this.intent.getStringExtra(
                        UNAME
                    ).toString())
                    startActivity(profile)
                    finishAffinity()
                }
                R.id.nav_logout->{
                    isUserLoggedIn = false
                    val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                    val editor = sharedPrefs.edit()
                    editor.clear();
                    editor.apply();
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

   override fun onPause() {
       super.onPause()
       val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
       val editor = sharedPrefs.edit()
       editor.putString("previous_activity", this.javaClass.name)
       editor.apply()
    }

    override fun onBackPressed() {
        // Call finishAffinity instead of super.onBackPressed
        finishAffinity()
    }
}

