package com.mobdeve.s13.Group17.MCO2

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutBinding
import com.squareup.picasso.Picasso

/*
MCO4
GROUP 17
CHUA, JASMIN
SHI, KAYE
TAN, HAILY
*/


class MyViewHolder(private val viewBinding: ItemLayoutBinding): RecyclerView.ViewHolder(viewBinding.root) {
    // Method which accepts a character object and set views' info accordingly

    // Initialize Firebase Firestore instance.
    private lateinit var db : FirebaseFirestore

    // Method that accepts a Books object and sets our views' information accordingly.
    fun bindData(books: Books) {
        // Get the Firebase Firestore instance.
        db = FirebaseFirestore.getInstance();

        // Get all documents from the 'Books' collection.
        db.collection("Books")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Loop through all documents to find the document that matches our book's title.
                    for (document in task.result) {
                        val bookTitle = document.data["Title"] as String
                        if (bookTitle == viewBinding.BookTitle.text) {
                            // Get the book image Uri and load it into the ImageView using Picasso library.
                            val imageUri = Uri.parse(document.data["Book Img"] as String?)
                            Picasso.get().load(imageUri).placeholder(R.drawable.hob_logo).into(viewBinding.imgBook);

                            // Set the book's publication date.
                            this.viewBinding.Date.text =
                                document.data["Date Published"] as CharSequence?

                            Log.e("TAG", imageUri.toString())
                            break
                        }
                    }
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }

       // Check if BookImg from books is empty, if its empty it will return default picture
        if (books.BookImg.isNotEmpty()) {
            Picasso.get().load(books.BookImg)
                .placeholder(R.drawable.hob_logo)
                .into(viewBinding.imgBook) // load the book image into the ImageView
        } else {
            Picasso.get().load(R.drawable.hob_logo)
                .into(viewBinding.imgBook) // load a placeholder image into the ImageView
        }
        // Set the book's information (except the image, publication date, and rating) into the views.
        this.viewBinding.ISBN.text = books.ISBN
        this.viewBinding.BookTitle.text = books.Title
        this.viewBinding.author.text = books.Author
        this.viewBinding.description.text = books.Plot
        this.viewBinding.myRating.rating = books.Rating.toFloat()

    }
}