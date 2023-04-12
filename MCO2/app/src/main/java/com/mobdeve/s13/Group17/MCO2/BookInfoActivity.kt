package com.mobdeve.s13.Group17.MCO2

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityBookinfoBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutCommentBinding
import com.squareup.picasso.Picasso


class BookInfoActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

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

    private lateinit var  commentList: ArrayList<Comment>

    private lateinit var recyclerViewComment: RecyclerView

    private lateinit var adapter: MyAdapterComment

    private lateinit var db : FirebaseFirestore




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityBookinfoBinding = ActivityBookinfoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)


        commentList = arrayListOf()

        // set up recycler view
        this.recyclerViewComment = viewBinding.recyclerView2
        this.adapter = MyAdapterComment(commentList, BOOK_TITLE_KEY)
        this.recyclerViewComment.adapter = adapter
        this.recyclerViewComment.layoutManager = LinearLayoutManager(this)

        db = FirebaseFirestore.getInstance();


        // putting intent to variable

        val emptyView = viewBinding.empty
        val title = intent.getStringExtra(BOOK_TITLE_KEY)
        val author = intent.getStringExtra(AUTHOR_KEY)
        val description = intent.getStringExtra(DESCRIPTION_KEY)
        val image = intent.getStringExtra(IMG_KEY)
        val date = intent.getStringExtra(PUBLICATION_DATE_KEY)
        val isbn = intent.getStringExtra(ISBN_KEY)
        if (image == null || image.isEmpty()) {
            Log.e("Picasso", "Image URL is empty or null!")
        } else {
            Picasso.get().load(image).into(viewBinding.bookimg)
        }

        viewBinding.booktitletv.text = title
        viewBinding.authortv.text = author
        viewBinding.publishdatetv.text = date
        viewBinding.ISBNtv.text = isbn
        viewBinding.descriptiontv.text = description
        viewBinding.myRatingBar.rating = intent.getFloatExtra(RATING_KEY, 0F).toFloat()

         db = FirebaseFirestore.getInstance();
        var i = false

        // Set up the SnapshotListener to listen for changes to the UserReviews collection
        val collectionRef = db.collection("UserReviews")
        val query = collectionRef.whereEqualTo("Book Title", viewBinding.booktitletv.text)

        val listener = query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.isEmpty) {
                val comments = snapshot.documents.map { document ->
                    val username = document.getString("User")
                    val comment = document.getString("Review")
                    Comment(username.toString(), comment.toString())
                }

                if (comments.isEmpty()) {
                    viewBinding.empty.visibility = View.VISIBLE
                } else {
                    viewBinding.empty.visibility = View.GONE
                }

                adapter.updateData(comments)
            } else {
                Log.d(TAG, "No comments")
            }
        }


        db.collection("UserReview").whereEqualTo("User", this.intent.getStringExtra(UNAME).toString()).whereEqualTo("Book Title",title)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    i = true
                    Log.w(TAG, "Found.")
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }

        db.collection("Books")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val bookTitle = document.data["Title"] as String
                        if (bookTitle == title) {
                            viewBinding.booktitletv.text = bookTitle
                            viewBinding.authortv.text = document.data["Author"] as CharSequence?
                            viewBinding.publishdatetv.text =
                                document.data["Date Published"] as CharSequence?
                            viewBinding.ISBNtv.text = document.data["ISBN"] as CharSequence?
                            viewBinding.descriptiontv.text = document.data["Plot"] as CharSequence?
                            val imageUri = Uri.parse(document.data["Book Img"] as String?)
                            Picasso.get().load(imageUri).placeholder(R.drawable.hob_logo)
                                .into(viewBinding.bookimg);


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





        // pressing add button will start activity to AddBookReview
        viewBinding.addbtnFab.setOnClickListener {
            if(true)
            {
                val intent: Intent = Intent(this, AddBookReview::class.java)

                intent.putExtra(AddBookReview.BOOK_TITLE_KEY, title)
                intent.putExtra(AddBookReview.AUTHOR_KEY, author)
                intent.putExtra(AddBookReview.DESCRIPTION_KEY, description)
                intent.putExtra(AddBookReview.IMG_KEY, image)
                intent.putExtra(AddBookReview.UNAME, this.intent.getStringExtra(UNAME).toString())
                startActivity(intent)
            }
            else if (false){
                Toast.makeText(
                    this@BookInfoActivity,
                    "You have already made a review",
                    Toast.LENGTH_SHORT
                ).show()
            }
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
