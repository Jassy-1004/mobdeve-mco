package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMylibraryBinding

class MyLibraryActivity : AppCompatActivity() {
    //data
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    private val reviewList: ArrayList<BookReview> = DataHelper.initializedData()

    private lateinit var recyclerViewLibrary: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var adapter: MyAdapterReview

    private val bookReviewResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityMylibraryBinding = ActivityMylibraryBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val homepage: Intent = Intent(this, MainActivity::class.java);
        val logout: Intent = Intent(this, StartPage::class.java);
        val library: Intent = Intent(this, MyLibraryActivity::class.java);
        val profile: Intent = Intent(this, MyProfileActivity::class.java);

        this.recyclerViewLibrary = viewBinding.recyclerViewLibrary
        this.adapter = MyAdapterReview(reviewList, bookReviewResultLauncher)
        this.recyclerViewLibrary.adapter = adapter

        this.recyclerViewLibrary.layoutManager = LinearLayoutManager(this)

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
                    finish()
                }
                R.id.nav_books -> {
                    // Do something for menu item 2
                    startActivity(Intent(this, MyLibraryActivity::class.java))
                    finish()
                }
                R.id.nav_profile->{
                    startActivity(Intent(this, MyProfileActivity::class.java))
                    finish()
                }
                R.id.nav_logout->{
                    startActivity(Intent(this, StartPage::class.java))
                    finish()
                }
                // Add more items as needed
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