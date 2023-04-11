package com.mobdeve.s13.Group17.MCO2

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutMylibraryBinding

class MyViewHolderReview (private val viewBinding: ItemLayoutMylibraryBinding): RecyclerView.ViewHolder(viewBinding.root){
    // method which accepts a character object and set views' info accordingly
    fun bindData(review: BookReview){
        this.viewBinding.BookTitle.text= review.Title
        this.viewBinding.reviewTv.text = review.comment
        this.viewBinding.dateTv.text = review.DatePublished
        this.viewBinding.myRating.rating = review.Rating

    }
}