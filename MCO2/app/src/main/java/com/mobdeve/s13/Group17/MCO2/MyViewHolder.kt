package com.mobdeve.s13.Group17.MCO2

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutBinding
import com.squareup.picasso.Picasso


class MyViewHolder(private val viewBinding: ItemLayoutBinding): RecyclerView.ViewHolder(viewBinding.root) {
    //  Method that accepts a Character object and sets our views' info accordingly.
    private lateinit var db : FirebaseFirestore
    fun bindData(books: Books) {
        db = FirebaseFirestore.getInstance();

        db.collection("Books")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        val bookTitle = document.data["Title"] as String
                        if (bookTitle == viewBinding.BookTitle.text) {
                            val imageUri = Uri.parse(document.data["Book Img"] as String?)
                            Picasso.get().load(imageUri).placeholder(R.drawable.hob_logo).into(viewBinding.imgBook);

                            Log.e("TAG", imageUri.toString())
                            Log.e("TAG","${document.data["Rating"]}")
                            break
                        }
                    }
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }

        this.viewBinding.ISBN.text = books.ISBN
        if (books.BookImg.isNotEmpty()) {
            Picasso.get().load(books.BookImg)
                .placeholder(R.drawable.hob_logo)
                .into(viewBinding.imgBook) // load the book image into the ImageView
        } else {
            Picasso.get().load(R.drawable.hob_logo)
                .into(viewBinding.imgBook) // load a placeholder image into the ImageView
        }
        this.viewBinding.BookTitle.text = books.Title
        this.viewBinding.author.text = books.Author
        this.viewBinding.description.text = books.Plot
        this.viewBinding.myRating.rating = books.Rating.toFloat()
        this.viewBinding.Date.text = books.DatePublished
    }
}