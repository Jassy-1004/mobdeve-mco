package com.mobdeve.s13.Group17.MCO2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityBookinfoBinding

class BookInfoActivity : AppCompatActivity() {
    private lateinit var backbtn: ImageButton
    companion object{
        const val BOOK_TITLE_KEY = "BOOK_TITLE_KEY"
        const val AUTHOR_KEY = "AUTHOR_KEY"
        const val PUBLICATION_DATE_KEY = "PUBLICATION_DATE_KEY"
        const val ISBN_KEY = "ISBN_KEY"
        const val DESCRIPTION_KEY = "DESCRIPTION_KEY"
        const val POSITION_KEY = "POSITION_KEY"
        const val RATING_KEY = "RATING_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityBookinfoBinding = ActivityBookinfoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.booktitletv.text = intent.getStringExtra(BookInfoActivity.BOOK_TITLE_KEY)
        viewBinding.authortv.text = intent.getStringExtra(BookInfoActivity.AUTHOR_KEY)
        viewBinding.publishdatetv.text = intent.getStringExtra(BookInfoActivity.PUBLICATION_DATE_KEY)
        viewBinding.ISBNtv.text = intent.getStringExtra(BookInfoActivity.ISBN_KEY)
        viewBinding.descriptiontv.text = intent.getStringExtra(BookInfoActivity.DESCRIPTION_KEY)
        viewBinding.myRatingBar.rating = intent.getIntExtra(BookInfoActivity.RATING_KEY, 0).toFloat()

        val position = intent.getIntExtra(BookInfoActivity.POSITION_KEY, 0)

        viewBinding.booktitletv.setOnClickListener(View.OnClickListener {
            val intent : Intent = Intent()

            intent.putExtra(BookInfoActivity.POSITION_KEY, position)

            setResult(Activity.RESULT_OK, intent)

            finish()
        })

        backbtn= viewBinding.backbutton
        backbtn.setOnClickListener {
            finish()
            
        }
    }
}