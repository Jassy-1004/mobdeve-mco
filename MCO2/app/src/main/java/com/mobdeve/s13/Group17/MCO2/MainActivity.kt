package com.mobdeve.s13.Group17.MCO2

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ArrayAdapter
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
import com.google.firebase.database.*
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.protobuf.Value
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
        const val UNAME = "Username"
    }
    // data
    //private val bookList: ArrayList<Books> = DataHelper.initializeData()
    private var bookList = ArrayList<Books>()
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

   // private val bookReviewList: ArrayList<BookReview> = DataHelper.initializesDatas()

    // RecyclerView reference
    private lateinit var recyclerView: RecyclerView
    private lateinit var myAdapter: MyAdapter


    private val bookInfoResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){result : ActivityResult->
        if(result.resultCode == RESULT_OK){

        }

    }

    private lateinit var dbf : FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val username = this.intent.getStringExtra(UNAME).toString()
        Log.d(TAG, "DocumentSnapshot data: ${this.intent.getStringExtra(UNAME).toString()}")

        val viewBinding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val dropdown = findViewById<Spinner>(R.id.filter)
        val items = arrayOf("Most Rated", "Latest Books", "Oldest Books")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        dropdown.adapter = adapter

        // Initialize the RecyclerView
        this.recyclerView = viewBinding.recyclerView

        bookList = arrayListOf()

        myAdapter= MyAdapter(bookList,bookInfoResultLauncher, username)

            // Set the Adapter.
        this.recyclerView.adapter = myAdapter

        // Set the LayoutManager.
        this.recyclerView.layoutManager = LinearLayoutManager(this)


        EventChangeListener()



        //Add Data in Firestore db

        /*dbf.collection("Books").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val books: List<Map<String, Any>> = listOf(

                        hashMapOf(
                              "Title" to "",
                              "Author" to "",
                              "Rating" to ,
                              "ISBN" to "",
                              "Plot" to "",
                              "Date Published" to "",
                              "Book Img" to ""

                          )

                    )

                    for (book in books) {
                        dbf.collection("Books")
                            .add(book)
                            .addOnSuccessListener { documentReference ->
                                Log.d(TAG, "Book successfully added with ID: ${documentReference.id}")
                            }
                            .addOnFailureListener { e ->
                                Log.w(TAG, "Error adding book", e)
                            }
                    }
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }*/


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

    private fun EventChangeListener() {

        val progressDialog = ProgressDialog(this@MainActivity)
        progressDialog.setMessage("Fetching Data, please wait")
        progressDialog.show()



        // recycler view getting from firebase firestore
        dbf = FirebaseFirestore.getInstance()
        dbf.collection("Books").
        addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                if (error != null) {
                    Log.e("Firestore Error", error.message.toString())
                    return
                }

                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        bookList.add(dc.document.toObject(Books::class.java))
                    }
                }
                progressDialog.dismiss();
                myAdapter.notifyDataSetChanged()
            }
        })


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun onPause() {
        super.onPause()

        var username = this.intent.getStringExtra(Login1.UNAME).toString()
    }
}
