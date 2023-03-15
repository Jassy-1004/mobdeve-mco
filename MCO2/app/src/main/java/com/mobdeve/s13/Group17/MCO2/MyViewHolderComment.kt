package com.mobdeve.s13.Group17.MCO2

import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutCommentBinding

class MyViewHolderComment(private val viewBinding: ItemLayoutCommentBinding): RecyclerView.ViewHolder(viewBinding.root) {
    fun bindData(comment: Comment){
        this.viewBinding.usertv.text = comment.username
        this.viewBinding.commenttv.text = comment.comment
    }
}