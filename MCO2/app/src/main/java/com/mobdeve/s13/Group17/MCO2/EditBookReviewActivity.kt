package com.mobdeve.s13.Group17.MCO2

import android.app.Activity
import android.content.ContentValues
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
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityAddoreditreviewBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMyreviewBinding
import com.squareup.picasso.Picasso
import java.util.function.BinaryOperator

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

    // variable to hold an instance of Firebase Firestore
    private lateinit var dbf : FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        //inflate layout for this activity
        val viewBinding: ActivityAddoreditreviewBinding = ActivityAddoreditreviewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        // put intent information to variable
        val title = this.intent.getStringExtra(BOOK_TITLE_KEY).toString()
        val author = this.intent.getStringExtra(AUTHOR_KEY).toString()
        val description = this.intent.getStringExtra(BOOK_DESCRIPTION_KEY).toString()
        var rating = this.intent.getFloatExtra(RATING_KEY, 0F).toFloat()
        var review = this.intent.getStringExtra(REVIEW_KEY).toString()
        val image = this.intent.getIntExtra(IMG_KEY, R.drawable.hob_logo)
        val user = intent.getStringExtra(UNAME).toString()
        Log.d(ContentValues.TAG, user)


        // set values to views
        viewBinding.booktitletv.text = intent.getStringExtra(EditBookReviewActivity.BOOK_TITLE_KEY)
        viewBinding.authortv.text = intent.getStringExtra(EditBookReviewActivity.AUTHOR_KEY)
        viewBinding.descriptiontv.text = intent.getStringExtra(EditBookReviewActivity.BOOK_DESCRIPTION_KEY)
        viewBinding.bookimg.setImageResource(intent.getIntExtra(EditBookReviewActivity.IMG_KEY, R.drawable.hob_logo))
        viewBinding.myRatingBar.rating = intent.getFloatExtra(RATING_KEY, 0F).toFloat()
        viewBinding.commentEt.setText(intent.getStringExtra(REVIEW_KEY))

        //initialize Firebase Firestore instance
        dbf = FirebaseFirestore.getInstance()

        // retrieve book data from Firestore collection
        dbf.collection("Books")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val bookTitle = document.data["Title"] as String
                        if (bookTitle == viewBinding.booktitletv.text) {
                            val imageUri = Uri.parse(document.data["Book Img"] as String?)
                            Picasso.get().load(imageUri).placeholder(R.drawable.hob_logo).into(viewBinding.bookimg);

                            viewBinding.descriptiontv.text = document.data["Plot"] as CharSequence?
                            Log.e("TAG", imageUri.toString())
                            Log.e("TAG","${document.data["Rating"]}")
                            break
                        }
                    }
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }

        // retrieve user review from Firestore collection
        dbf.collection("UserReviews").whereEqualTo("User", this.intent.getStringExtra(BookInfoActivity.UNAME).toString()).whereEqualTo("Book Title",title)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val reviewText = document.getString("Review")
                        if (reviewText != null) {
                            viewBinding.commentEt.setText(reviewText)
                        }

                        val rating = document.getDouble("Rating")
                        if (rating != null) {
                            viewBinding.myRatingBar.rating = rating.toFloat()
                        }
                    }
                    Log.w(ContentValues.TAG, "Found.")
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }


        viewBinding.savebtn.setOnClickListener() {
            if (viewBinding.commentEt.text.toString().isNotEmpty()) {

                var id: String = ""
                val comment = viewBinding.commentEt.text.toString()
                val rating = viewBinding.myRatingBar.rating.toFloat()

                //Update UserReviews information
                //Used in the edit user review feature
                dbf.collection("UserReviews").whereEqualTo("User", user)
                    .whereEqualTo("Book Title", title).get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            for (document in task.result) {
                                id = document.id
                                Log.w(ContentValues.TAG, "id: $id")
                            }

                            dbf.runTransaction { transaction ->
                                val snapshot =
                                    transaction.get(dbf.collection("UserReviews").document(id))
                                transaction.update(
                                    dbf.collection("UserReviews").document(id),
                                    "Rating",
                                    rating,
                                    "Review",
                                    comment
                                )
                                // Success
                                null
                            }.addOnSuccessListener {
                                // start BookReviewActivity and finish this activity
                                val intent: Intent = Intent(this, BookReviewActivity::class.java)

                                intent.putExtra(BookReviewActivity.BOOK_TITLE_KEY, title)
                                intent.putExtra(BookReviewActivity.BOOK_DESCRIPTION_KEY, description)
                                intent.putExtra(BookReviewActivity.RATING_KEY, rating)
                                intent.putExtra(BookReviewActivity.REVIEW_KEY, review)
                                intent.putExtra(BookReviewActivity.IMAGE_KEY, image)
                                intent.putExtra(BookReviewActivity.AUTHOR_KEY, author)
                                intent.putExtra(
                                    BookReviewActivity.UNAME,
                                    this.intent.getStringExtra(UNAME).toString()
                                )
                               startActivity(intent)

                                Log.d(ContentValues.TAG, "Transaction success!")
                                finish()
                            }
                        } else {
                            Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                        }
                    }
            }
            else{
                // show a Toast message to remind the user to enter a review
                Toast.makeText(this,
                    "Please enter your review",
                    Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // handle click events for the discard button
        viewBinding.discardbtn.setOnClickListener(View.OnClickListener{
            val intent: Intent = Intent(this, BookReviewActivity::class.java)

            intent.putExtra(BookReviewActivity.BOOK_TITLE_KEY, title)
            intent.putExtra(BookReviewActivity.BOOK_DESCRIPTION_KEY, description)
            intent.putExtra(BookReviewActivity.RATING_KEY, rating)
            intent.putExtra(BookReviewActivity.REVIEW_KEY, review)
            intent.putExtra(BookReviewActivity.IMAGE_KEY, image)
            intent.putExtra(BookReviewActivity.AUTHOR_KEY, author)
            intent.putExtra(BookReviewActivity.UNAME, this.intent.getStringExtra(UNAME).toString())
            startActivity(intent)
            finish()
        })
    }
}