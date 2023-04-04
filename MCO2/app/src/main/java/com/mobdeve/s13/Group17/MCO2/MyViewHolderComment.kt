package com.mobdeve.s13.Group17.MCO2

import android.content.ContentValues
import android.net.Uri
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutCommentBinding
import com.squareup.picasso.Picasso

class MyViewHolderComment(private val viewBinding: ItemLayoutCommentBinding): RecyclerView.ViewHolder(viewBinding.root) {
    // method which accepts a character object and set views' info accordingly

    fun bindData(comment: Comment) {


         this.viewBinding.usertv.text = comment.username
         this.viewBinding.commenttv.text = comment.comment
    }
}