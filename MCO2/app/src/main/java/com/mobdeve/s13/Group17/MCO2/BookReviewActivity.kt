package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMyreviewBinding

class BookReviewActivity: AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    companion object {
        const val BOOK_TITLE_KEY = "BOOK_TITLE_KEY"
        const val AUTHOR_KEY = "AUTHOR_KEY"
        const val BOOK_DESCRIPTION_KEY = "BOOK_DESCRIPTION_KEY"
        const val RATING_KEY = "RATING_KEY"
        const val REVIEW_KEY = "REVIEW_KEY"
        const val POSITION_KEY = "POSITION_KEY"
        const val IMAGE_KEY="IMAGE_KEY"
    }



    //private val reviewList: ArrayList<BookReview> = DataHelper.initializedData()

    private lateinit var editBtn: Button
    private lateinit var deleteBtn: Button

    private val addBookReviewLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            //add in arraylist
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityMyreviewBinding = ActivityMyreviewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // put intent information to variable
        val title = intent.getStringExtra(BookReviewActivity.BOOK_TITLE_KEY)
        val author = intent.getStringExtra(BookReviewActivity.AUTHOR_KEY)
        val description = intent.getStringExtra(BookReviewActivity.BOOK_DESCRIPTION_KEY)
        val rating = intent.getFloatExtra(BookReviewActivity.RATING_KEY, 0F).toFloat()
        val review = intent.getStringExtra(BookReviewActivity.REVIEW_KEY)
        val image = intent.getIntExtra(BookReviewActivity.IMAGE_KEY, R.drawable.hob_logo)

        // put information to view
        viewBinding.booktitletv.text = title
        viewBinding.authortv.text = author
        viewBinding.descriptiontv.text = description
        viewBinding.myRating.rating = rating
        viewBinding.reviewTv.text= review
        viewBinding.bookImage.setImageResource(image)

        //pressing editBtn will go to Edit Book Review Activity
        editBtn = viewBinding.editbtn
        editBtn.setOnClickListener {
            val edit: Intent = Intent(this, EditBookReviewActivity::class.java)

            edit.putExtra(EditBookReviewActivity.BOOK_TITLE_KEY, title)
            edit.putExtra(EditBookReviewActivity.BOOK_DESCRIPTION_KEY, description)
            edit.putExtra(EditBookReviewActivity.RATING_KEY, rating)
            edit.putExtra(EditBookReviewActivity.REVIEW_KEY, review)
            edit.putExtra(EditBookReviewActivity.IMG_KEY, image)
            edit.putExtra(EditBookReviewActivity.AUTHOR_KEY, author)

            startActivity(edit)
        }

        deleteBtn = viewBinding.deletebtn
        viewBinding.deletebtn.setOnClickListener {
            val delete: Intent = Intent(this, DeleteBookReviewActivity::class.java)
            startActivity(delete)
        }
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
        } else super.onOptionsItemSelected(item)
    }
}
