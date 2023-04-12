package com.mobdeve.s13.Group17.MCO2

import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityAddoreditreviewBinding
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class AddBookReview : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var Review: EditText
    private lateinit var Rating:RatingBar

    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var ref: DocumentReference

    companion object{
        const val IMG_KEY="IMG_KEY"
        const val BOOK_TITLE_KEY = "BOOK_TITLE_KEY"
        const val AUTHOR_KEY = "AUTHOR_KEY"
        const val DESCRIPTION_KEY = "DESCRIPTION_KEY"
        const val POSITION_KEY = "POSITION_KEY"
        const val UNAME = "Username"

    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityAddoreditreviewBinding = ActivityAddoreditreviewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        
        //Putting different parts of the page
        viewBinding.myRatingBar.rating = 0F
        Review=viewBinding.commentEt
        Rating=viewBinding.myRatingBar
        viewBinding.authortv.text = intent.getStringExtra(AddBookReview.AUTHOR_KEY)
        viewBinding.booktitletv.text = intent.getStringExtra(AddBookReview.BOOK_TITLE_KEY)
        viewBinding.descriptiontv.text = intent.getStringExtra(AddBookReview.DESCRIPTION_KEY)


        firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("Books")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val bookTitle = document.data["Title"] as String
                        if (bookTitle == viewBinding.booktitletv.text) {
                            val imageUri = Uri.parse(document.data["Book Img"] as String?)
                            Picasso.get().load(imageUri).placeholder(R.drawable.hob_logo).into(viewBinding.bookimg);

                            Log.e("TAG", imageUri.toString())
                            Log.e("TAG","${document.data["Rating"]}")
                            break
                        }
                    }
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }


        //Save button
        viewBinding.savebtn.setOnClickListener(){
            if (Review.text.toString().isEmpty()) {
                Toast.makeText(this@AddBookReview, "Please write something", Toast.LENGTH_SHORT).show()
            }
            else if(Rating.rating.toFloat().equals(0F)){
                Toast.makeText(this@AddBookReview, "Please rate the book", Toast.LENGTH_SHORT).show()
            }
            else {
                val username = this.intent.getStringExtra(UNAME).toString()
                Log.w(TAG, "$username")
                val currentTimestamp = com.google.firebase.Timestamp.now()
                val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
                val currentDate = dateFormat.format(currentTimestamp.toDate())
                firebaseFirestore.collection("UserReviews").get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val reg_entry: MutableMap<String, Any> = HashMap()
                            reg_entry["User"]=username
                            reg_entry["Review"] = Review.text.toString()
                            reg_entry["Rating"] = Rating.rating.toFloat()
                            reg_entry["Book Title"] = viewBinding.booktitletv.text.toString()
                            reg_entry["Date Posted"] = currentDate

                            firebaseFirestore.collection("UserReviews")
                                .add(reg_entry)
                                .addOnSuccessListener { documentReference ->
                                    Toast.makeText(
                                        this@AddBookReview,
                                        "Review successfully added",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    val intent: Intent = Intent()
                                    setResult(Activity.RESULT_OK, intent)
                                    finish()
                                }

                        } else {
                            Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                        }

                    }

            }
        }


        //Discard button will exit the add book review activity
        viewBinding.discardbtn.setOnClickListener(View.OnClickListener{
            finish()
        })




    }


}