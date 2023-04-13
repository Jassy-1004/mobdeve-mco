package com.mobdeve.s13.Group17.MCO2

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutMylibraryBinding
import com.squareup.picasso.Picasso

class MyViewHolderReview (private val viewBinding: ItemLayoutMylibraryBinding): RecyclerView.ViewHolder(viewBinding.root){
    // method which accepts a character object and set views' info accordingly

    private lateinit var dbf: FirebaseFirestore

    fun bindData(review: BookReview, username: String) {
        dbf = FirebaseFirestore.getInstance()

        // Retrieve the book image for the given book title
        dbf.collection("Books")
            .whereEqualTo("Title", review.Title)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val imageUri = Uri.parse(document.data["Book Img"] as String?)
                        Picasso.get().load(imageUri).placeholder(R.drawable.hob_logo).into(viewBinding.imgBook)

                        Log.e("TAG", imageUri.toString())
                        Log.e("TAG", "${document.data["Rating"]}")
                        break
                    }
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }

        // Retrieve the user review for the given book title
        dbf.collection("UserReviews")
            .whereEqualTo("User", username)
            .whereEqualTo("Book Title", review.Title)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val reviewText = document.getString("Review")
                        if (reviewText != null) {
                            viewBinding.reviewTv.text = reviewText
                        }

                        val rating = document.getDouble("Rating")
                        if (rating != null){
                            viewBinding.myRating.rating = rating.toFloat()
                        }

                        val date = document.getString("Date Posted")
                        if (date != null){
                            viewBinding.dateTv.text = date.toString()
                        }

                        break

                    }
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }

        this.viewBinding.BookTitle.text = review.Title
        this.viewBinding.dateTv.text = review.DatePublished

    }


}