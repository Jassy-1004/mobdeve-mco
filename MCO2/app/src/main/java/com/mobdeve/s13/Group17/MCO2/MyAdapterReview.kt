package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutMylibraryBinding

class MyAdapterReview(private val data: ArrayList<BookReview>, private val myActivityResultLauncher: ActivityResultLauncher<Intent>): RecyclerView.Adapter<MyViewHolderReview>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderReview {
        // Create a LayoutInflater using the parent's context
        val inflater = LayoutInflater.from(parent.context)

        // Inflate a new View given the item_layout2.xml item view we created.
        val view: ItemLayoutMylibraryBinding = ItemLayoutMylibraryBinding.inflate(inflater, parent,false)

        // Return a new instance of our MyViewHolder passing the View object we created
        val myViewHolder = MyViewHolderReview(view)

        myViewHolder.itemView.setOnClickListener{
            val intent : Intent = Intent(myViewHolder.itemView.context, BookReviewActivity::class.java)



            this.myActivityResultLauncher.launch(intent)
        }

        return MyViewHolderReview(view)
    }

    override fun onBindViewHolder(holder: MyViewHolderReview, position: Int) {
        holder.bindData(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
}