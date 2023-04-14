package com.mobdeve.s13.Group17.MCO2


import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutCommentBinding

/*
MCO4
GROUP 17
CHUA, JASMIN
SHI, KAYE
TAN, HAILY
*/

class MyViewHolderComment(private val viewBinding: ItemLayoutCommentBinding): RecyclerView.ViewHolder(viewBinding.root) {
    // method which accepts a character object and set views' info accordingly

    fun bindData(comment: Comment) {
        // Set the comment information into the views.
         this.viewBinding.usertv.text = comment.username
         this.viewBinding.commenttv.text = comment.comment
    }
}