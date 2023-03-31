package com.mobdeve.s13.Group17.MCO2

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityAddoreditreviewBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMyreviewBinding
import java.util.function.BinaryOperator

//MCO3 Group 17- Chua, Shi, Tan
class EditBookReviewActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    companion object{
        const val BOOK_TITLE_KEY = "BOOK_TITLE_KEY"
        const val AUTHOR_KEY = "AUTHOR_KEY"
        const val BOOK_DESCRIPTION_KEY = "BOOK_DESCRIPTION_KEY"
        const val RATING_KEY = "RATING_KEY"
        const val REVIEW_KEY = "REVIEW_KEY"
        const val IMG_KEY = "IMG_KEY"
        const val POSITION_KEY = "POSITION_KEY"
        const val UNAME="USERNAME"
    }

    val db = FirebaseFirestore.getInstance();

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityAddoreditreviewBinding = ActivityAddoreditreviewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // putting data to views
        viewBinding.booktitletv.text = intent.getStringExtra(EditBookReviewActivity.BOOK_TITLE_KEY)
        viewBinding.authortv.text = intent.getStringExtra(EditBookReviewActivity.AUTHOR_KEY)
        viewBinding.descriptiontv.text = intent.getStringExtra(EditBookReviewActivity.BOOK_DESCRIPTION_KEY)
        viewBinding.bookimg.setImageResource(intent.getIntExtra(EditBookReviewActivity.IMG_KEY, R.drawable.hob_logo))
        viewBinding.myRatingBar.rating = intent.getFloatExtra(BookReviewActivity.RATING_KEY, 0F).toFloat()
        viewBinding.commentEt.setText(intent.getStringExtra(REVIEW_KEY))

        viewBinding.savebtn.setOnClickListener(){
            val intent: Intent = Intent()

            var id: String = ""
            val comment = viewBinding.commentEt.text.toString()
            val rating = viewBinding.myRatingBar.rating.toFloat()

            //add database update here
            db.collection("UserReviews").whereEqualTo("User", intent.getStringExtra(UNAME)).whereEqualTo("Book Title", intent.getStringExtra(
                BOOK_TITLE_KEY)).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            id = document.id
                            Log.w(ContentValues.TAG, "id: $id")
                        }

                        db.runTransaction { transaction ->
                            val snapshot = transaction.get(db.collection("UserReviews").document(id))
                            transaction.update(db.collection("UserReviews").document(id), "Rating", rating,"Review", comment)
                            // Success
                            null
                        }.addOnSuccessListener {
                            Log.d(ContentValues.TAG, "Transaction success!")
                            intent.putExtra(BookReviewActivity.UNAME, this.intent.getStringExtra(UNAME).toString())
                            startActivity(intent)
                            finishAffinity()
                        }
                    } else {
                        Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                    }
                }

            setResult(Activity.RESULT_OK, intent)

            finish()
        }

        viewBinding.discardbtn.setOnClickListener(View.OnClickListener{
            finish()
        })
        /*// drawer layout instance to toggle the menu icon to open
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
        } else super.onOptionsItemSelected(item)*/
    }
}