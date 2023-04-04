package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutCommentBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutMylibraryBinding

class MyAdapterComment (private val data: ArrayList<Comment>, val title:  String): RecyclerView.Adapter<MyViewHolderComment>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderComment {
        // Create a LayoutInflater using the parent's context
        val inflater = LayoutInflater.from(parent.context)

        // Inflate a new View given the item_layout2.xml item view we created.
        val view: ItemLayoutCommentBinding = ItemLayoutCommentBinding.inflate(inflater, parent,false)

        // Return a new instance of our MyViewHolder passing the View object we created
        val myViewHolder = MyViewHolderComment(view)

        return MyViewHolderComment(view)
    }

    override fun onBindViewHolder(holder: MyViewHolderComment, position: Int) {
        holder.bindData(data[position])

    }

    override fun getItemCount(): Int {
        return data.size
    }
}