package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat.startActivity
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

        myViewHolder.itemView.setOnClickListener {
            val intent = Intent(myViewHolder.itemView.context, BookReviewActivity::class.java)

            myActivityResultLauncher.launch(intent)
        }

        return MyViewHolderReview(view)
    }






    override fun onBindViewHolder(holder: MyViewHolderReview, position: Int) {
        val bookReview = data[position]
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, BookReviewActivity::class.java)

            intent.putExtra(BookReviewActivity.BOOK_TITLE_KEY, bookReview.book_Title)
            intent.putExtra(BookReviewActivity.AUTHOR_KEY, bookReview.book_Author)
            intent.putExtra(BookReviewActivity.BOOK_DESCRIPTION_KEY,bookReview.book_Author)
            intent.putExtra(BookReviewActivity.RATING_KEY, bookReview.book_Rating)
            intent.putExtra(BookReviewActivity.REVIEW_KEY, bookReview.book_Review)
            intent.putExtra(BookReviewActivity.POSITION_KEY, position)

            myActivityResultLauncher.launch(intent)
        }


        holder.bindData(bookReview)
    }

    override fun getItemCount(): Int {
        return data.size
    }
}