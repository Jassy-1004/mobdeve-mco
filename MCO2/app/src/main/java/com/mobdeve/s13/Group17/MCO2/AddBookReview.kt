package com.mobdeve.s13.Group17.MCO2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityAddoreditreviewBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMyreviewBinding

class AddBookReview : AppCompatActivity() {

    companion object{
        const val IMG_KEY="IMG_KEY"
        const val BOOK_TITLE_KEY = "BOOK_TITLE_KEY"
        const val AUTHOR_KEY = "AUTHOR_KEY"
        const val DESCRIPTION_KEY = "DESCRIPTION_KEY"
        const val POSITION_KEY = "POSITION_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityAddoreditreviewBinding = ActivityAddoreditreviewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.authortv.text = intent.getStringExtra(AddBookReview.AUTHOR_KEY)
        viewBinding.booktitletv.text = intent.getStringExtra(AddBookReview.BOOK_TITLE_KEY)
        viewBinding.descriptiontv.text = intent.getStringExtra(AddBookReview.DESCRIPTION_KEY)
        viewBinding.bookimg.setImageResource(intent.getIntExtra(AddBookReview.IMG_KEY, R.drawable.hob_logo))

        viewBinding.myRatingBar.rating = 0F

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