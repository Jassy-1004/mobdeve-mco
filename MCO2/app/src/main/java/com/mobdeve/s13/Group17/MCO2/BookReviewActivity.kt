package com.mobdeve.s13.Group17.MCO2

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMyreviewBinding
import com.squareup.picasso.Picasso

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
        const val UNAME="USERNAME"
    }
    
    //private val reviewList: ArrayList<BookReview> = DataHelper.initializedData()

    private lateinit var editBtn: Button
    private lateinit var deleteBtn: Button
    private lateinit var dbf: FirebaseFirestore

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
        var rating = intent.getFloatExtra(BookReviewActivity.RATING_KEY, 0F).toFloat()
        var review = intent.getStringExtra(BookReviewActivity.REVIEW_KEY)
        val image = intent.getIntExtra(BookReviewActivity.IMAGE_KEY, R.drawable.hob_logo)

        // put information to view
        viewBinding.booktitletv.text = title
        viewBinding.authortv.text = author
        viewBinding.descriptiontv.text = description
        viewBinding.myRating.rating = rating
        viewBinding.reviewTv.text= review
        viewBinding.bookImage.setImageResource(image)

        dbf = FirebaseFirestore.getInstance()

        dbf.collection("Books")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val bookTitle = document.data["Title"] as String
                        if (bookTitle == title) {
                            viewBinding.booktitletv.text = bookTitle
                            viewBinding.authortv.text = document.data["Author"] as CharSequence?
                            viewBinding.descriptiontv.text = document.data["Plot"] as CharSequence?

                            val imageUri = Uri.parse(document.data["Book Img"] as String?)
                            Picasso.get().load(imageUri).placeholder(R.drawable.hob_logo)
                                .into(viewBinding.bookImage);


                            Log.e("TAG", imageUri.toString())
                            Log.e("TAG", "${document.data["Rating"]}")
                            break
                        }
                    }
                }
                else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }


        dbf.collection("UserReviews").whereEqualTo("User", this.intent.getStringExtra(UNAME).toString()).whereEqualTo("Book Title",title)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val reviewText = document.getString("Review")
                        if (reviewText != null) {
                            viewBinding.reviewTv.text = reviewText
                            review = reviewText
                        }

                        val ratings = document.getDouble("Rating")
                        if (ratings != null) {
                            viewBinding.myRating.rating = ratings.toFloat()
                            rating = ratings.toFloat()
                        }
                    }
                    Log.w(ContentValues.TAG, "Found.")
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }

        //pressing editBtn will go to Edit Book Review Activity
        editBtn = viewBinding.editbtn
        editBtn.setOnClickListener {
            val edit: Intent = Intent(this, EditBookReviewActivity::class.java)

            edit.putExtra(EditBookReviewActivity.BOOK_TITLE_KEY, this.intent.getStringExtra(
                BOOK_TITLE_KEY).toString())
            edit.putExtra(EditBookReviewActivity.BOOK_DESCRIPTION_KEY, this.intent.getStringExtra(
                BOOK_DESCRIPTION_KEY).toString())
            edit.putExtra(EditBookReviewActivity.RATING_KEY, this.intent.getStringExtra(RATING_KEY).toString())
            edit.putExtra(EditBookReviewActivity.REVIEW_KEY, this.intent.getStringExtra(REVIEW_KEY).toString())
            edit.putExtra(EditBookReviewActivity.IMG_KEY, image)
            edit.putExtra(EditBookReviewActivity.AUTHOR_KEY, this.intent.getStringExtra(AUTHOR_KEY).toString())
            edit.putExtra(EditBookReviewActivity.UNAME, this.intent.getStringExtra(UNAME).toString())

            startActivity(edit)
            finish()
        }

        deleteBtn = viewBinding.deletebtn
        viewBinding.deletebtn.setOnClickListener {
            val delete: Intent = Intent(this, DeleteBookReviewActivity::class.java)
            delete.putExtra(DeleteBookReviewActivity.UNAME, this.intent.getStringExtra(UNAME).toString())
            delete.putExtra(DeleteBookReviewActivity.BOOK_TITLE_KEY, this.intent.getStringExtra(
                BOOK_TITLE_KEY).toString())
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
                    home.putExtra(MainActivity.UNAME, this.intent.getStringExtra(UNAME).toString())
                    startActivity(home)
                    finishAffinity()
                }
                R.id.nav_books-> {
                    // Do something for menu item 2
                    val lib = Intent(this, MyLibraryActivity::class.java)
                    lib.putExtra(MyLibraryActivity.UNAME, this.intent.getStringExtra(UNAME).toString())
                    startActivity(lib)
                    finishAffinity()
                }
                R.id.nav_profile->{
                    val profile = Intent(this, MyProfileActivity::class.java)
                    profile.putExtra(MyProfileActivity.UNAME, this.intent.getStringExtra(UNAME).toString())
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
