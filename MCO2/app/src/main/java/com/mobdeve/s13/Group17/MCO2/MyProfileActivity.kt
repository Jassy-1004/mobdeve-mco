package com.mobdeve.s13.Group17.MCO2

import android.app.ProgressDialog
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



    // Navigation drawer setup
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    // Declare variable to be used i this Activity
    private lateinit var editProfile: Button

    // Declare variables to access the Firestore database
    val db = FirebaseFirestore.getInstance();

    // Boolean indicating whether a user is logged in or not
    var isUserLoggedIn = getIsLoggedIn()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using view binding
        val viewBinding: ActivityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Retrieve the user's username from SharedPreferences
        val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val username = sharedPrefs.getString("username", "")

        Log.d(TAG, "DocumentSnapshot data: ${username}")

<<<<<<< Updated upstream
        // Query the database to retrieve the user's profile information
=======

        // process dialog while fetching the users data from db
        val progressDialog = ProgressDialog(this@MyProfileActivity)
        progressDialog.setMessage("Loading....., Please Wait")
        progressDialog.show()

>>>>>>> Stashed changes
        db.collection("UserInfo").whereEqualTo("Username", username)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        // Update the UI with the user's profile information
                        viewBinding.profileName.text = document.data["Name"] as CharSequence?
                        viewBinding.profileUsername.text = document.data["Username"] as CharSequence?
                        viewBinding.profileBio.text = document.data["Bio"] as CharSequence?
                        Log.d(TAG, document.id + " => " + document.data)
                        // Dismiss the progress dialog and notify the adapter that the data set has changed
                        progressDialog.dismiss();
                    }
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }

        // Set up the Edit Profile button
        editProfile = viewBinding.editProfile
        editProfile.setOnClickListener {
            val intent: Intent = Intent(this, EditProfile::class.java);
            intent.putExtra(UNAME, this.intent.getStringExtra(Login1.UNAME).toString())
            startActivity(intent)
        }

        // Set up the DrawerLayout and its listener
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up the NavigationView and its listener
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item clicks here
            when (menuItem.itemId) {
                // Go to the MainActivity
                R.id.nav_home -> {
                    val home = Intent(this, MainActivity::class.java)
                    home.putExtra(MainActivity.UNAME, this.intent.getStringExtra(MainActivity.UNAME).toString())
                    startActivity(home)
                    finishAffinity()
                }

                // Go to the MyLibraryActivity
                R.id.nav_books-> {
                    // Do something for menu item 2
                    val lib = Intent(this, MyLibraryActivity::class.java)
                    lib.putExtra(MyLibraryActivity.UNAME, this.intent.getStringExtra(MainActivity.UNAME).toString())
                    startActivity(lib)
                    finishAffinity()
                }

                // Go to the MyProfileActivity
                R.id.nav_profile->{
                    val profile = Intent(this, MyProfileActivity::class.java)
                    profile.putExtra(MyProfileActivity.UNAME, this.intent.getStringExtra(
                        MainActivity.UNAME
                    ).toString())
                    startActivity(profile)
                    finishAffinity()
                }

                // Go to the Logout
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

    // Handle the navigation drawer and menu button
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    // Pause the activity and store the previous activity in SharedPreferences
   override fun onPause() {
       super.onPause()
       val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
       val editor = sharedPrefs.edit()
       editor.putString("previous_activity", this.javaClass.name)
       editor.apply()
    }

    // Function to handle the "back" button press by calling, so it wouldn't go back to th login page
    override fun onBackPressed() {
        // Call finishAffinity instead of super.onBackPressed
        finishAffinity()
    }
}

