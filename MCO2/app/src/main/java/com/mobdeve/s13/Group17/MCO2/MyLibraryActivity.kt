package com.mobdeve.s13.Group17.MCO2

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.core.view.isGone
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.*
import com.mobdeve.s13.Group17.MCO2.StartPage.Companion.getIsLoggedIn
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMylibraryBinding
import com.squareup.picasso.Picasso

class MyLibraryActivity : AppCompatActivity() {


    // define the uname and comment that will be used to access intents
    companion object {
        private const val TAG = "MyLibraryActivity"
        const val UNAME = "Username"
        const val comment = "Review"
    }


    // declares the variables that are used in the my library activity
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var bookList = ArrayList<BookReview>()

    private lateinit var recyclerViewLibrary: RecyclerView
    private lateinit var myAdapter: MyAdapterReview

    private lateinit var dbf: FirebaseFirestore

    var isUserLoggedIn = getIsLoggedIn()

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

        // retrieve value from a SharedPref
        val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val username = sharedPrefs.getString("username", "")

        // for checker
        Log.d(TAG, "DocumentSnapshot data: ${username}")


        // setup recycler view
        this.recyclerViewLibrary = viewBinding.recyclerViewLibrary


        // Set adapter to RecyclerView
        myAdapter = MyAdapterReview(bookList, bookReviewResultLauncher, username.toString())

        // Set the Adapter.
        this.recyclerViewLibrary.adapter = myAdapter

        this.recyclerViewLibrary.layoutManager = LinearLayoutManager(this)

        // initialize dbf
        dbf= FirebaseFirestore.getInstance()


        dbf.collection("UserReviews")
            .whereEqualTo("User", username)
            .addSnapshotListener { reviews, error ->
                if (error != null) {
                    Log.e("Firestore", "Error getting reviews: ", error)
                    return@addSnapshotListener
                }

                // set of mutable list of string to be modified later depending on the review of the user
                val bookTitles = mutableListOf<String>()

                // extract the book titles from the reviews and add it into bookTitles (mutable list of string)
                for (review in reviews!!) {
                    val bookTitle = review.getString("Book Title")
                    if (bookTitle != null) {
                        bookTitles.add(bookTitle)
                    }
                }

                // progress dialog is shown when fetching data from db and checking if user has reviews
                val progressDialog = ProgressDialog(this@MyLibraryActivity)
                progressDialog.setMessage("Loading....., Please Wait")
                progressDialog.show()

                if (bookTitles.isNotEmpty()) {  // if users have a review

                    // query the Books collection using the book titles
                    dbf.collection("Books")
                        .whereIn("Title", bookTitles)
                        .get()
                        .addOnSuccessListener { books ->
                            // display the book information
                            val bookList = mutableListOf<BookReview>()
                            for (book in books) {

                                // for checking
                                Log.d("Book", "Title: ${book.getString("Title")}")
                                Log.d("Book", "Author: ${book.getString("Author")}")

                                // display the book title , book review, and image of the book
                                val title = book.getString("Title")
                                val comment = ""
                                val imageUri = Uri.parse(book.getString("Book Img"))
                                Picasso.get().load(imageUri).placeholder(R.drawable.hob_logo)

                                if (title != null && imageUri != null) {
                                    bookList.add(BookReview(title, comment, imageUri))
                                }

                                // hide the view and show no reviews view if user has no reviews
                                viewBinding.empty.visibility = View.GONE
                            }
                            // Dismiss the progress dialog
                            progressDialog.dismiss();

                            myAdapter.updateData(bookList)
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Firestore", "Error getting books: ", exception)
                        }
                }
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
                    lib.putExtra(UNAME, this.intent.getStringExtra(UNAME).toString())
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

    // save the name of previous activity in a SharedPref file when current activity is paused
    override fun onPause() {
        super.onPause()
        val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString("previous_activity", this.javaClass.name)
        editor.apply()
    }


    // function is called when action bar inside menu item is clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}

