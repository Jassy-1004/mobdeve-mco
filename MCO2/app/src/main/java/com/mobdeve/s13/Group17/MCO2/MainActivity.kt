package com.mobdeve.s13.Group17.MCO2

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.mobdeve.s13.Group17.MCO2.StartPage.Companion.getIsLoggedIn
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
        const val UNAME = "Username"
    }

    // variables for RecyclerView and DrawerLayout
    private var bookList = ArrayList<Books>()
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter


    // ActivityResultLauncher for getting book information
    private val bookInfoResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){result : ActivityResult->
        if(result.resultCode == RESULT_OK){

        }

    }

    // Firestore database instance
    private lateinit var dbf : FirebaseFirestore
    // boolean variable to keep track of user login status
    var isUserLoggedIn = getIsLoggedIn()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get the username passed from the previous activity
        val username = this.intent.getStringExtra(UNAME).toString()
        Log.d(TAG, "DocumentSnapshot data: ${this.intent.getStringExtra(UNAME).toString()}")

        // Set the view using view binding
        val viewBinding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Set up the spinner for sorting options
        val dropdown = findViewById<Spinner>(R.id.filter)
        val items = arrayOf("Filter", "A-Z", "Most Rated", "Least Rated")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        dropdown.adapter = adapter
        dropdown.setSelection(0)

        // Set up the listener for the spinner
        dropdown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                when (position) {
                    1 -> sortAlphabetically(bookList)
                    2 -> sortByRatingDescending(bookList)
                    3 -> sortByLeastRated(bookList)
                }
                myAdapter.notifyDataSetChanged()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle nothing selected event
            }
        }

        // Set up the RecyclerView and its adapter
        // Initialize the RecyclerView
        this.recyclerView = viewBinding.recyclerView
        bookList = arrayListOf()
        myAdapter= MyAdapter(bookList, bookList,bookInfoResultLauncher, username)
        this.recyclerView.adapter = myAdapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the listener for Firestore database changes
        EventChangeListener()



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
                    home.putExtra(MainActivity.UNAME, this.intent.getStringExtra(UNAME).toString())
                    startActivity(home)
                    finishAffinity()
                }

                // Go to the MyLibraryActivity
                R.id.nav_books-> {
                    // Do something for menu item 2
                    val lib = Intent(this, MyLibraryActivity::class.java)
                    lib.putExtra(MyLibraryActivity.UNAME, this.intent.getStringExtra(UNAME).toString())
                    startActivity(lib)
                    finishAffinity()
                }

                // Go to the MyProfileActivity
                R.id.nav_profile->{
                    val profile = Intent(this, MyProfileActivity::class.java)
                    profile.putExtra(MyProfileActivity.UNAME, this.intent.getStringExtra(UNAME).toString())
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

    override fun onResume() {
        super.onResume()

        // Get the username passed from the previous activity
        val username = this.intent.getStringExtra(UNAME).toString()
        Log.d(TAG, "DocumentSnapshot data: ${this.intent.getStringExtra(UNAME).toString()}")

        // Set the view using view binding
        val viewBinding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // Set up the RecyclerView and its adapter
        // Initialize the RecyclerView
        this.recyclerView = viewBinding.recyclerView
        bookList = arrayListOf()
        myAdapter= MyAdapter(bookList, bookList,bookInfoResultLauncher, username)
        this.recyclerView.adapter = myAdapter
        this.recyclerView.layoutManager = LinearLayoutManager(this)

        // Set up the listener for Firestore database changes
        EventChangeListener()
    }

    // EventChangeListener function used to listen to changes in the Firestore collection "Books" and populate a RecyclerView accordingly
    private fun EventChangeListener() {

        // Progress dialog to be shown while fetching data
        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Fetching Data, please wait")
        progressDialog.show()


        // Get the Firestore instance and listen for changes in the "Books" collection
        dbf = FirebaseFirestore.getInstance()
        dbf.collection("Books").
        addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                // If an error occurred, log it and return
                if (error != null) {
                    Log.e("Firestore Error", error.message.toString())
                    return
                }

                // Iterate over each document change in the query snapshot
                for (dc: DocumentChange in value?.documentChanges!!) {
                    // If the document change type is "added", add the corresponding book to the book list
                    if (dc.type == DocumentChange.Type.ADDED) {
                        bookList.add(dc.document.toObject(Books::class.java))
                    }
                }
                // Dismiss the progress dialog and notify the adapter that the data set has changed
                progressDialog.dismiss();
                myAdapter.notifyDataSetChanged()
            }
        })


        // Get the search EditText and listen for text changes to filter the RecyclerView items
        val searchEditText = findViewById<EditText>(R.id.search)
        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                search(s.toString())
            }
        })



    }
    // Function to sort the book list by rating in descending order
    private fun sortByRatingDescending(list: ArrayList<Books>) {
        list.sortByDescending { it.Rating }
        bookList = list
    }

    // Function to sort the book list by rating in ascending order
    private fun sortByLeastRated(list: ArrayList<Books>) {

        list.sortBy { it.Rating }
        bookList = list
    }

    // Function to sort the book list alphabetically by title
    private fun sortAlphabetically(list: ArrayList<Books>) {
        list.sortBy { it.Title }
        bookList = list
    }

    // Function to filter the book list based on a search query and update the RecyclerView
    private fun search(query: String) {
        val filteredList = if (query.isEmpty()) {
            // If the query is empty, return the original list
            bookList
        } else {
            // Otherwise, filter the list based on the search query
            bookList.filter { book ->
                book.Title.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT)) ||
                        book.Author.toLowerCase(Locale.ROOT).contains(query.toLowerCase(Locale.ROOT))
            }
        }
        myAdapter.filterList(filteredList)
        myAdapter.notifyDataSetChanged()
    }

    // Function to handle the "options item selected" event
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    // Function to save the name of the previous activity to shared preferences when the activity is paused
    override fun onPause() {
        super.onPause()
        val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putString("previous_activity", this.javaClass.name)
        editor.apply()
    }

    // Function to handle the "back" button press by calling, so it wouldn't go back to th login page
    override fun onBackPressed() {
        finishAffinity()
    }


}
