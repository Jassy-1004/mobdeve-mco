package com.mobdeve.s13.Group17.MCO2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMyreviewBinding

class EditBookReviewActivity : AppCompatActivity() {

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
    }
}