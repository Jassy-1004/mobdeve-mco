package com.mobdeve.s13.Group17.MCO2

import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMyreviewBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityProfileBinding
import com.squareup.picasso.Picasso

class MyViewHolder(private val viewBinding: ItemLayoutBinding): RecyclerView.ViewHolder(viewBinding.root) {
    //  Method that accepts a Character object and sets our views' info accordingly.
    fun bindData(books: Books) {


        this.viewBinding.ISBN.text = books.ISBN
        Picasso.get().load(books.BookImg).into(this.viewBinding.imgBook)
        this.viewBinding.BookTitle.text = books.Title
        this.viewBinding.author.text = books.Author
        this.viewBinding.description.text = books.Plot
        this.viewBinding.myRating.rating = books.Rating.toFloat()
        this.viewBinding.Date.text = books.DatePublished
    }
}