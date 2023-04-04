package com.mobdeve.s13.Group17.MCO2

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutBinding
import com.squareup.picasso.Picasso


class MyAdapter(private val data: MutableList<Books>, private var filteredList: List<Books> , private val myActivityResultLauncher: ActivityResultLauncher<Intent>, val uname: String): RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            // Create a LayoutInflater using the parent's context
            val inflater = LayoutInflater.from(parent.context)

            // Inflate a new View given the item_layout2.xml item view we created.
            val view: ItemLayoutBinding = ItemLayoutBinding.inflate(inflater, parent, false)

            // Return a new instance of our MyViewHolder passing the View object we created
          //  val myViewHolder = MyViewHolder(view)

            return MyViewHolder(view)
        }

        fun filterList(filteredList: List<Books>) {
        this.filteredList = filteredList
        notifyDataSetChanged()
     }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

            val book: Books = filteredList[position]


            holder.bindData(book)




            // clicking the itemView would start the activity for BookInfoActivity
            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, BookInfoActivity::class.java)

                intent.putExtra(BookInfoActivity.BOOK_TITLE_KEY, book.Title)
                intent.putExtra(BookInfoActivity.AUTHOR_KEY, book.Author)
                intent.putExtra(BookInfoActivity.PUBLICATION_DATE_KEY, book.DatePublished)
                intent.putExtra(BookInfoActivity.ISBN_KEY, book.ISBN)
                intent.putExtra(BookInfoActivity.DESCRIPTION_KEY, book.Plot)
                intent.putExtra(BookInfoActivity.POSITION_KEY, position)
                intent.putExtra(BookInfoActivity.RATING_KEY, book.Rating)
                intent.putExtra(BookInfoActivity.IMG_KEY, book.BookImg)
                intent.putExtra(BookInfoActivity.UNAME, uname)

                myActivityResultLauncher.launch(intent)
            }

        }

        override fun getItemCount(): Int {
            //return data.size
            return filteredList.size
        }
}