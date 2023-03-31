package com.mobdeve.s13.Group17.MCO2

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutCommentBinding

//MCO3 Group 17- Chua, Shi, Tan
class MyViewHolderComment(private val viewBinding: ItemLayoutCommentBinding): RecyclerView.ViewHolder(viewBinding.root) {
    // method which accepts a character object and set views' info accordingly
    fun bindData(comment: Comment){
        this.viewBinding.usertv.text = comment.username
        this.viewBinding.commenttv.text = comment.comment
    }
}