package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutBinding


class MyAdapter(private val data: ArrayList<Books>, private val myActivityResultLauncher: ActivityResultLauncher<Intent>): RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            // Create a LayoutInflater using the parent's context
            val inflater = LayoutInflater.from(parent.context)
            // Inflate a new View given the item_layout2.xml item view we created.
            val view: ItemLayoutBinding = ItemLayoutBinding.inflate(inflater, parent, false)

            // Return a new instance of our MyViewHolder passing the View object we created
            val myViewHolder = MyViewHolder(view)

            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val book = data[position]

            holder.bindData(book)

            // clicking the itemView would start the activity for BookInfoActivity
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, BookInfoActivity::class.java)

                intent.putExtra(BookInfoActivity.BOOK_TITLE_KEY, book.bookName)
                intent.putExtra(BookInfoActivity.AUTHOR_KEY, book.bookAuthor)
                intent.putExtra(BookInfoActivity.PUBLICATION_DATE_KEY, book.bookDate)
                intent.putExtra(BookInfoActivity.ISBN_KEY, book.book_isbn)
                intent.putExtra(BookInfoActivity.DESCRIPTION_KEY, book.bookPlot)
                intent.putExtra(BookInfoActivity.POSITION_KEY, position)
                intent.putExtra(BookInfoActivity.RATING_KEY, book.bookRating)
                intent.putExtra(BookInfoActivity.IMG_KEY, book.bookImage)

                myActivityResultLauncher.launch(intent)
            }

        }

        override fun getItemCount(): Int {
            return data.size
        }
}