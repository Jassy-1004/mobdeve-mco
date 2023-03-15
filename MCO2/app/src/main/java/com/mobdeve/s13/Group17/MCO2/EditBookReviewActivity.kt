package com.mobdeve.s13.Group17.MCO2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityAddoreditreviewBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMyreviewBinding

class EditBookReviewActivity : AppCompatActivity() {

    companion object{
        const val BOOK_TITLE_KEY = "BOOK_TITLE_KEY"
        const val AUTHOR_KEY = "AUTHOR_KEY"
        const val BOOK_DESCRIPTION_KEY = "BOOK_DESCRIPTION_KEY"
        const val RATING_KEY = "RATING_KEY"
        const val REVIEW_KEY = "REVIEW_KEY"
        const val IMG_KEY = "IMG_KEY"
        const val POSITION_KEY = "POSITION_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityAddoreditreviewBinding = ActivityAddoreditreviewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.booktitletv.text = intent.getStringExtra(EditBookReviewActivity.BOOK_TITLE_KEY)
        viewBinding.authortv.text = intent.getStringExtra(EditBookReviewActivity.AUTHOR_KEY)
        viewBinding.descriptiontv.text = intent.getStringExtra(EditBookReviewActivity.BOOK_DESCRIPTION_KEY)
        viewBinding.bookimg.setImageResource(intent.getIntExtra(EditBookReviewActivity.IMG_KEY, R.drawable.hob_logo))
        viewBinding.myRatingBar.rating = intent.getFloatExtra(BookReviewActivity.RATING_KEY, 0F).toFloat()
        viewBinding.commentEt.setText(intent.getStringExtra(EditBookReviewActivity.REVIEW_KEY))

        viewBinding.savebtn.setOnClickListener(){
            val intent: Intent = Intent()

            setResult(Activity.RESULT_OK, intent)

            finish()
        }

        viewBinding.discardbtn.setOnClickListener(View.OnClickListener{
            finish()
        })
    }
}