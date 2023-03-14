package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMyreviewBinding

class BookReviewActivity: AppCompatActivity() {
    companion object{
        const val BOOK_TITLE_KEY = "BOOK_TITLE_KEY"
        const val AUTHOR_KEY = "AUTHOR_KEY"
        const val BOOK_DESCRIPTION_KEY = "BOOK_DESCRIPTION_KEY"
        const val RATING_KEY = "RATING_KEY"
        const val REVIEW_KEY = "REVIEW_KEY"
        const val POSITION_KEY = "POSITION_KEY"
    }

    private lateinit var editBtn: Button

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityMyreviewBinding = ActivityMyreviewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        editBtn = viewBinding.editbtn
        editBtn.setOnClickListener{
            val edit: Intent = Intent(this, EditBookReviewActivity::class.java)
            startActivity(edit)
            finish()
        }

        viewBinding.deletebtn.setOnClickListener{

        }
    }
}
