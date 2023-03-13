package com.mobdeve.s13.Group17.MCO2

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutBinding

class MyViewHolder(private val viewBinding: ItemLayoutBinding): RecyclerView.ViewHolder(viewBinding.root) {
    //  Method that accepts a Character object and sets our views' info accordingly.
    fun bindData(books: Books) {
        this.viewBinding.ISBN.text = books.book_isbn
        this.viewBinding.imgBook.setImageResource(books.bookImage)
        this.viewBinding.BookTitle.text = books.bookName
        this.viewBinding.author.text = books.bookAuthor
        this.viewBinding.description.text = books.bookPlot
        this.viewBinding.myRating.rating = books.bookRating
        this.viewBinding.PublishedDate.text = books.bookDate
    }
}