package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.core.view.drawToBitmap
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMyreviewBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityProfileBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutBinding


class MyAdapter(private val data: ArrayList<Books>, private val myActivityResultLauncher: ActivityResultLauncher<Intent>): RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            // Create a LayoutInflater using the parent's context
            val inflater = LayoutInflater.from(parent.context)
            // Inflate a new View given the item_layout2.xml item view we created.
            val view: ItemLayoutBinding = ItemLayoutBinding.inflate(inflater, parent, false)
            val views: ActivityMyreviewBinding =
                ActivityMyreviewBinding.inflate(inflater, parent, false)
            val vw: ActivityProfileBinding = ActivityProfileBinding.inflate(inflater, parent, false)
            // Return a new instance of our MyViewHolder passing the View object we created
            val myViewHolder = MyViewHolder(view)

            myViewHolder.itemView.setOnClickListener {
                val intent: Intent =
                    Intent(myViewHolder.itemView.context, BookInfoActivity::class.java)

                intent.putExtra(BookInfoActivity.BOOK_TITLE_KEY, view.BookTitle.text.toString())
                intent.putExtra(BookInfoActivity.AUTHOR_KEY, view.author.text.toString())
                intent.putExtra(BookInfoActivity.PUBLICATION_DATE_KEY, view.Date.text.toString())
                intent.putExtra(BookInfoActivity.ISBN_KEY, view.ISBN.text.toString())
                intent.putExtra(BookInfoActivity.DESCRIPTION_KEY, view.description.text.toString())
                intent.putExtra(BookInfoActivity.POSITION_KEY, myViewHolder.adapterPosition)
                intent.putExtra(BookInfoActivity.RATING_KEY, view.myRating.rating.toFloat())

                myActivityResultLauncher.launch(intent)
            }

          /*  myViewHolder.itemView.setOnClickListener{
                val intent : Intent = Intent(myViewHolder.itemView.context, BookReviewActivity::class.java)

                intent.putExtra(BookReviewActivity.BOOK_TITLE_KEY, views.booktitletv.text.toString())
                intent.putExtra(BookReviewActivity.AUTHOR_KEY, views.authortv.text.toString())
                intent.putExtra(BookReviewActivity.BOOK_DESCRIPTION_KEY, views.descriptiontv.text.toString())
                intent.putExtra(BookReviewActivity.RATING_KEY, views.myRating.rating.toFloat())
                intent.putExtra(BookReviewActivity.REVIEW_KEY, views.myReviewtv.text.toString())
                intent.putExtra(BookReviewActivity.POSITION_KEY,myViewHolder.adapterPosition)

                myActivityResultLauncher.launch(intent)
            }*/

           /* myViewHolder.itemView.setOnClickListener{
                val intent : Intent = Intent(myViewHolder.itemView.context, ProfileActivity::class.java)

                intent.putExtra(ProfileActivity.FIRSTNAME_TITLE_KEY, vw.profileName.text.toString())
                intent.putExtra(ProfileActivity.LASTNAME_KEY, vw.profileName.text.toString())
                intent.putExtra(ProfileActivity.USERNAME_KEY, vw.profileUsername.text.toString())
                intent.putExtra(ProfileActivity.GENDER_KEY, vw.profileGender.text.toString())
                intent.putExtra(ProfileActivity.BIRTHDAY_KEY, vw.profileBirthdate.text.toString())
                intent.putExtra(ProfileActivity.BIO_KEY, vw.profileBio.text.toString())
                intent.putExtra(BookReviewActivity.POSITION_KEY,myViewHolder.adapterPosition)

                myActivityResultLauncher.launch(intent)
            }*/

            return MyViewHolder(view)
        }


        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bindData(data[position])
        }

        override fun getItemCount(): Int {
            return data.size
        }
}