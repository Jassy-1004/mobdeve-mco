package com.mobdeve.s13.Group17.MCO2

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.Group17.MCO2.StartPage.Companion.getIsLoggedIn
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityBookinfoBinding
import com.squareup.picasso.Picasso


class BookInfoActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    var isUserLoggedIn = getIsLoggedIn()

    // Define Key that will be used to access intent extras
    companion object{
        const val IMG_KEY="IMG_KEY"
        const val BOOK_TITLE_KEY = "BOOK_TITLE_KEY"
        const val AUTHOR_KEY = "AUTHOR_KEY"
        const val PUBLICATION_DATE_KEY = "PUBLICATION_DATE_KEY"
        const val ISBN_KEY = "ISBN_KEY"
        const val DESCRIPTION_KEY = "DESCRIPTION_KEY"
        const val POSITION_KEY = "POSITION_KEY"
        const val RATING_KEY = "RATING_KEY"
        const val UNAME = "USERNAME"
    }

    // Declare variables used in this Activity
    private lateinit var  commentList: ArrayList<Comment>
    private lateinit var recyclerViewComment: RecyclerView
    private lateinit var adapter: MyAdapterComment
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using view binding
        val viewBinding: ActivityBookinfoBinding = ActivityBookinfoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        //For checking purposes
        Log.w(TAG, this.intent.getStringExtra(UNAME).toString())

        // Initialize an empty comment list
        commentList = arrayListOf()

        // Set up recycler view and its adapter
        this.recyclerViewComment = viewBinding.recyclerView2
        this.adapter = MyAdapterComment(commentList, BOOK_TITLE_KEY)
        this.recyclerViewComment.adapter = adapter
        this.recyclerViewComment.layoutManager = LinearLayoutManager(this)

        // Initialize a Firebase Firestore instance
        db = FirebaseFirestore.getInstance()


        // Extract intent extras and populate the corresponding views
        val title = intent.getStringExtra(BOOK_TITLE_KEY)
        val author = intent.getStringExtra(AUTHOR_KEY)
        val description = intent.getStringExtra(DESCRIPTION_KEY)
        val image = intent.getStringExtra(IMG_KEY)
        val date = intent.getStringExtra(PUBLICATION_DATE_KEY)
        val isbn = intent.getStringExtra(ISBN_KEY)

        // Load the book cover image using Picasso library
        if (image == null || image.isEmpty()) { Log.e("Picasso", "Image URL is empty or null!") }
        else { Picasso.get().load(image).into(viewBinding.bookimg) }

        // Populate the rest of the views with intent extras
        viewBinding.booktitletv.text = title
        viewBinding.authortv.text = author
        viewBinding.publishdatetv.text = date
        viewBinding.ISBNtv.text = isbn
        viewBinding.descriptiontv.text = description
        viewBinding.myRatingBar.rating = intent.getFloatExtra(RATING_KEY, 0F).toFloat()

        db = FirebaseFirestore.getInstance()

        // Set up the SnapshotListener to listen for changes to the UserReviews collection
        val collectionRef = db.collection("UserReviews")
        val query = collectionRef.whereEqualTo("Book Title", viewBinding.booktitletv.text)

           val listener= query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                // Map the query results to Comment objects
                val comments = snapshot.documents.map { document ->
                    val username = document.getString("User")
                    val comment = document.getString("Review")
                    Comment(username.toString(), comment.toString())
                }
                // Show or hide the empty view depending on the comments list
                if (comments.isEmpty()) {
                    viewBinding.empty.visibility = View.VISIBLE
                } else {
                    viewBinding.empty.visibility = View.GONE
                }

                adapter.updateData(comments)
            } else {
                //For Checking purposes
                Log.d(TAG, "No comments")
            }
        }


        // Firebase Firestore code to get book data from "Books" collection
        db.collection("Books")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val bookTitle = document.data["Title"] as String
                        if (bookTitle == title) {
                            // Set book information to corresponding UI elements
                            viewBinding.booktitletv.text = bookTitle
                            viewBinding.authortv.text = document.data["Author"] as CharSequence?
                            viewBinding.publishdatetv.text =
                                document.data["Date Published"] as CharSequence?
                            viewBinding.ISBNtv.text = document.data["ISBN"] as CharSequence?
                            viewBinding.descriptiontv.text = document.data["Plot"] as CharSequence?
                            // Load book image with Picasso
                            val imageUri = Uri.parse(document.data["Book Img"] as String?)
                            Picasso.get().load(imageUri).placeholder(R.drawable.hob_logo)
                                .into(viewBinding.bookimg)

                            // Log book data for debugging
                            //For checking purposes
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


        // When add button is pressed, launch AddBookReview activity if user hasn't reviewed the book yet
        viewBinding.addbtnFab.setOnClickListener {
            val db = FirebaseFirestore.getInstance()
            val title = viewBinding.booktitletv.text.toString()
            val user = this.intent.getStringExtra(UNAME).toString()

            // Check if the user has already left a review for this book
            db.collection("UserReviews")
                .whereEqualTo("Book Title", title)
                .whereEqualTo("User", user)
                .get()
                .addOnSuccessListener { documents ->
                    if (!documents.isEmpty) {
                        Toast.makeText(this@BookInfoActivity, "You have already reviewed this book", Toast.LENGTH_SHORT).show()
                    } else {
                        // If the user hasn't left a review, launch the AddBookReview activity
                        val intent = Intent(this, AddBookReview::class.java)
                        intent.putExtra(AddBookReview.BOOK_TITLE_KEY, title)
                        intent.putExtra(AddBookReview.AUTHOR_KEY, author)
                        intent.putExtra(AddBookReview.DESCRIPTION_KEY, description)
                        intent.putExtra(AddBookReview.IMG_KEY, image)
                        intent.putExtra(AddBookReview.UNAME, user)
                        startActivity(intent)
                    }
                }
                .addOnFailureListener { exception ->
                    Log.e(TAG, "Error checking if user has reviewed book: $exception")
                }
        }

        // Set up navigation drawer
        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        // pass the Open and Close toggle for the drawer layout listener to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Set up navigation drawer item clicks
        val navigationView = findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item clicks here
            when (menuItem.itemId) {
                // Go to MainActivity
                R.id.nav_home -> {
                    val home = Intent(this, MainActivity::class.java)
                    home.putExtra(MainActivity.UNAME, this.intent.getStringExtra(UNAME).toString())
                    startActivity(home)
                    finishAffinity()
                }
                // Go to MyLibraryActivity
                R.id.nav_books-> {
                    val lib = Intent(this, MyLibraryActivity::class.java)
                    lib.putExtra(MyLibraryActivity.UNAME, this.intent.getStringExtra(UNAME).toString())
                    startActivity(lib)
                    finishAffinity()
                }
                // Go to MyProfileActivity
                R.id.nav_profile->{
                    val profile = Intent(this, MyProfileActivity::class.java)
                    profile.putExtra(MyProfileActivity.UNAME, this.intent.getStringExtra(UNAME).toString())
                    startActivity(profile)
                    finishAffinity()
                }
                // Go to StartPage
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

    // This function is called when a menu item in the action bar is clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

}
