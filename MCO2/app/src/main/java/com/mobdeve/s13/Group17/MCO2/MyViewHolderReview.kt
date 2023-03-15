package com.mobdeve.s13.Group17.MCO2

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutMylibraryBinding

class MyViewHolderReview (private val viewBinding: ItemLayoutMylibraryBinding): RecyclerView.ViewHolder(viewBinding.root){
    fun bindData(review: BookReview){
        this.viewBinding.BookTitle.text= review.book_Title
        this.viewBinding.reviewTv.text = review.book_Review
        this.viewBinding.dateTv.text = review.datePosted
        this.viewBinding.myRating.rating = review.book_Rating
        this.viewBinding.imgBook.setImageResource(review.bookImage)
    }


}