package com.mobdeve.s13.Group17.MCO2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityAddoreditreviewBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMyreviewBinding

class AddBookReview : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    companion object{
        const val IMG_KEY="IMG_KEY"
        const val BOOK_TITLE_KEY = "BOOK_TITLE_KEY"
        const val AUTHOR_KEY = "AUTHOR_KEY"
        const val DESCRIPTION_KEY = "DESCRIPTION_KEY"
        const val POSITION_KEY = "POSITION_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityAddoreditreviewBinding = ActivityAddoreditreviewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //Putting different parts of the page
        viewBinding.authortv.text = intent.getStringExtra(AddBookReview.AUTHOR_KEY)
        viewBinding.booktitletv.text = intent.getStringExtra(AddBookReview.BOOK_TITLE_KEY)
        viewBinding.descriptiontv.text = intent.getStringExtra(AddBookReview.DESCRIPTION_KEY)
        viewBinding.bookimg.setImageResource(intent.getIntExtra(AddBookReview.IMG_KEY, R.drawable.hob_logo))
        viewBinding.myRatingBar.rating = 0F

        //Save button
        viewBinding.savebtn.setOnClickListener(){
            val intent: Intent = Intent()
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        //Discard button will exit the add book review activity
        viewBinding.discardbtn.setOnClickListener(View.OnClickListener{
            finish()
        })

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

        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item clicks here
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    val home = Intent(this, MainActivity::class.java)
                    startActivity(home)
                    finishAffinity()
                }
                R.id.nav_books-> {
                    // Do something for menu item 2
                    val lib = Intent(this, MyLibraryActivity::class.java)
                    startActivity(lib)
                    finishAffinity()
                }
                R.id.nav_profile->{
                    val profile = Intent(this, MyProfileActivity::class.java)
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
        } else super.onOptionsItemSelected(item)
    }
}