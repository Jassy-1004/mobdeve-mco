package com.mobdeve.s13.Group17.MCO2

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.Group17.MCO2.databinding.ItemLayoutBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMyreviewBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityProfileBinding

class MyViewHolder(private val viewBinding: ItemLayoutBinding): RecyclerView.ViewHolder(viewBinding.root) {
    //  Method that accepts a Character object and sets our views' info accordingly.
    fun bindData(books: Books) {
        this.viewBinding.ISBN.text = books.book_isbn
        this.viewBinding.imgBook.setImageResource(books.bookImage)
        this.viewBinding.BookTitle.text = books.bookName
        this.viewBinding.author.text = books.bookAuthor
        this.viewBinding.description.text = books.bookPlot
        this.viewBinding.myRating.rating = books.bookRating
        this.viewBinding.Date.text = books.bookDate
    }

   /* fun bindsData(bookReview: BookReview){
        this.viewBindings.booktitletv.text = bookReview.book_Title
        this.viewBindings.authortv.text = bookReview.book_Author
        this.viewBindings.descriptiontv.text = bookReview.book_Description
        this.viewBindings.myRating.rating = bookReview.book_Rating
        this.viewBindings.myReviewtv.text = bookReview.book_Review
    }

    fun bindDatas(profile: Profile){
        this.viewsBinding.profileName.text = profile.firstName
        this.viewsBinding.profileName.text = profile.lastName
        this.viewsBinding.profileUsername.text = profile.username
        this.viewsBinding.profileGender.text = profile.gender
        this.viewsBinding.profileBirthdate.text = profile.birthday
        this.viewsBinding.profileBio.text = profile.bio
    }*/

}