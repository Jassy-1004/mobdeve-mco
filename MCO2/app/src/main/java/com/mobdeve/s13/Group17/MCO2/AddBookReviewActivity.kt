package com.mobdeve.s13.Group17.MCO2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMyreviewBinding

class AddBookReviewActivity : AppCompatActivity() {

    companion object{
        const val BOOK_TITLE_KEY = "BOOK_TITLE_KEY"
        const val AUTHOR_KEY = "AUTHOR_KEY"
        const val BOOK_DESCRIPTION_KEY = "BOOK_DESCRIPTION_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityMyreviewBinding = ActivityMyreviewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.booktitletv.text = intent.getStringExtra(AddBookReviewActivity.BOOK_TITLE_KEY)
        viewBinding.authortv.text = intent.getStringExtra(AddBookReviewActivity.AUTHOR_KEY)
        viewBinding.descriptiontv.text = intent.getStringExtra(AddBookReviewActivity.BOOK_DESCRIPTION_KEY)

    }
}