package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutMylibraryBinding

class MyAdapterReview(private val data: ArrayList<BookReview>, private val myActivityResultLauncher: ActivityResultLauncher<Intent>, val uname: String): RecyclerView.Adapter<MyViewHolderReview>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderReview {
        // Create a LayoutInflater using the parent's context
        val inflater = LayoutInflater.from(parent.context)

        // Inflate a new View given the item_layout2.xml item view we created.
        val view: ItemLayoutMylibraryBinding = ItemLayoutMylibraryBinding.inflate(inflater, parent,false)

        // Return a new instance of our MyViewHolder passing the View object we created
        val myViewHolder = MyViewHolderReview(view)

        return myViewHolder
    }


    override fun onBindViewHolder(holder: MyViewHolderReview, position: Int) {
        val bookReview = data[position]

        holder.bindData(bookReview,uname)

        //clicking itemView would start activity for BookReviewActivity
        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, BookReviewActivity::class.java)
            intent.putExtra(BookReviewActivity.BOOK_TITLE_KEY, bookReview.Title)
            intent.putExtra(BookReviewActivity.AUTHOR_KEY, bookReview.Author)
            intent.putExtra(BookReviewActivity.BOOK_DESCRIPTION_KEY,bookReview.Plot)
            intent.putExtra(BookReviewActivity.RATING_KEY, bookReview.Rating)
            intent.putExtra(BookReviewActivity.REVIEW_KEY, bookReview.comment)
            intent.putExtra(BookReviewActivity.IMAGE_KEY, bookReview.BookImg)
            intent.putExtra(BookReviewActivity.POSITION_KEY, position)
            intent.putExtra(BookReviewActivity.UNAME, uname)



            myActivityResultLauncher.launch(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}