package com.mobdeve.s13.Group17.MCO2

import android.content.ContentValues
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
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.*
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMylibraryBinding
import com.squareup.picasso.Picasso

class MyLibraryActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MyLibraryActivity"
        const val UNAME = "Username"
        const val comment = "Review"
    }


    //data
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private var bookList = ArrayList<BookReview>()



    //private val reviewList: ArrayList<BookReview> = DataHelper.initializedData()

    private lateinit var recyclerViewLibrary: RecyclerView

    private lateinit var myAdapter: MyAdapterReview

    private lateinit var dbf: FirebaseFirestore

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

        val emptyView = viewBinding.empty

        val uname = this.intent.getStringExtra(UNAME).toString()

        // setup recycler view
        this.recyclerViewLibrary = viewBinding.recyclerViewLibrary


        // Set adapter to RecyclerView
        //recyclerViewLibrary.adapter =  MyAdapter(bookList, bookList, bookReviewResultLauncher, uname)

        myAdapter = MyAdapterReview(bookList, bookReviewResultLauncher, uname)

        // Set the Adapter.
        this.recyclerViewLibrary.adapter = myAdapter

        //this.recyclerViewLibrary.adapter = adapter

        this.recyclerViewLibrary.layoutManager = LinearLayoutManager(this)


        dbf= FirebaseFirestore.getInstance()

        dbf.collection("UserReviews")
            .whereEqualTo("User", uname)
            .addSnapshotListener { reviews, error ->
                if (error != null) {
                    Log.e("Firestore", "Error getting reviews: ", error)
                    return@addSnapshotListener
                }

                val bookTitles = mutableListOf<String>()

                // Extract the book titles from the reviews
                for (review in reviews!!) {
                    val bookTitle = review.getString("Book Title")
                    if (bookTitle != null) {
                        bookTitles.add(bookTitle)
                    }
                }

                if (bookTitles.isNotEmpty()) {
                    // Query the Books collection using the book titles
                    dbf.collection("Books")
                        .whereIn("Title", bookTitles)
                        .get()
                        .addOnSuccessListener { books ->
                            // Display the book information
                            val bookList = mutableListOf<BookReview>()
                            for (book in books) {
                                Log.d("Book", "Title: ${book.getString("Title")}")
                                Log.d("Book", "Author: ${book.getString("Author")}")

                                val title = book.getString("Title")
                                val comment = ""
                                val imageUri = Uri.parse(book.getString("Book Img"))
                                Picasso.get().load(imageUri).placeholder(R.drawable.hob_logo)

                                if (title != null && imageUri != null) {
                                    bookList.add(BookReview(title, comment, imageUri))
                                }
                            }

                            myAdapter.updateData(bookList)
                        }
                        .addOnFailureListener { exception ->
                            Log.e("Firestore", "Error getting books: ", exception)
                        }
                } else {
                    if (myAdapter.itemCount == 0) {
                        recyclerViewLibrary.visibility = View.GONE
                        emptyView.visibility = View.VISIBLE
                    } else {
                        recyclerViewLibrary.visibility = View.VISIBLE
                        emptyView.visibility = View.GONE
                    }
                    myAdapter.updateData(emptyList())
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

