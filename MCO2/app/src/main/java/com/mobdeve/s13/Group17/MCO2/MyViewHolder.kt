package com.mobdeve.s13.Group17.MCO2

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

  //  private val bookid: TextView = itemView.findViewById(R.id.ISBN)
    private val title: TextView = itemView.findViewById(R.id.BookTitle)
    private val author: TextView = itemView.findViewById(R.id.author)
    private val bookImg: ImageView = itemView.findViewById(R.id.imgBook)
    private val description: TextView = itemView.findViewById(R.id.description)
    private val rating: RatingBar =itemView.findViewById((R.id.myRatingBar))
    private val date: TextView = itemView.findViewById(R.id.Date)


    //  Method that accepts a Character object and sets our views' info accordingly.
    fun bindData(books: Books) {
        bookImg.setImageResource(books.bookImage)
     //   bookid.text= books.book_isbn.toString()
        title.text=books.bookName
        author.text=books.bookAuthor
        description.text=books.bookPlot
        rating.rating =books.bookRating
        date.text=books.bookDate

    }


    }