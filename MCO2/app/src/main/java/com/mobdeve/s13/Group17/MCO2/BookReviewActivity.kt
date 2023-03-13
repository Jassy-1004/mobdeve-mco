package com.mobdeve.s13.Group17.MCO2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMyreviewBinding

class BookReviewActivity : AppCompatActivity() {

    companion object{
        const val BOOK_TITLE_KEY = "BOOK_TITLE_KEY"
        const val AUTHOR_KEY = "AUTHOR_KEY"
        const val BOOK_DESCRIPTION_KEY = "BOOK_DESCRIPTION_KEY"
        const val RATING_KEY = "RATING_KEY"
        const val REVIEW_KEY = "REVIEW_KEY"
        const val POSITION_KEY = "POSITION_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityMyreviewBinding = ActivityMyreviewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.booktitletv.text = intent.getStringExtra(BookReviewActivity.BOOK_TITLE_KEY)
        viewBinding.authortv.text = intent.getStringExtra(BookReviewActivity.AUTHOR_KEY)
        viewBinding.descriptiontv.text = intent.getStringExtra(BookReviewActivity.BOOK_DESCRIPTION_KEY)
        viewBinding.myRating.rating = intent.getIntExtra(BookReviewActivity.RATING_KEY,0).toFloat()
        viewBinding.myReviewtv.text = intent.getStringExtra(BookReviewActivity.REVIEW_KEY)

        val position = intent.getIntExtra(BookReviewActivity.POSITION_KEY, 0)

    }
}