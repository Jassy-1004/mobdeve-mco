package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import android.widget.Button
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMyreviewBinding

class BookReviewActivity: AppCompatActivity() {
    companion object {
        const val BOOK_TITLE_KEY = "BOOK_TITLE_KEY"
        const val AUTHOR_KEY = "AUTHOR_KEY"
        const val BOOK_DESCRIPTION_KEY = "BOOK_DESCRIPTION_KEY"
        const val RATING_KEY = "RATING_KEY"
        const val REVIEW_KEY = "REVIEW_KEY"
        const val POSITION_KEY = "POSITION_KEY"
        const val IMAGE_KEY="IMAGE_KEY"
        private val reviewList = ArrayList<BookReview>()
    }


    private val reviewList: ArrayList<BookReview> = DataHelper.initializedData()

    private lateinit var editBtn: Button
    private lateinit var deleteBtn: Button

    private val addBookReviewLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_CANCELED) {

        } else if (result.resultCode == RESULT_OK) {
            //add in arraylist
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityMyreviewBinding = ActivityMyreviewBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.booktitletv.text = intent.getStringExtra(BookReviewActivity.BOOK_TITLE_KEY)
        viewBinding.authortv.text = intent.getStringExtra(BookReviewActivity.AUTHOR_KEY)
        viewBinding.descriptiontv.text = intent.getStringExtra(BookReviewActivity.BOOK_DESCRIPTION_KEY)
        viewBinding.myRating.rating = intent.getFloatExtra(BookReviewActivity.RATING_KEY, 0F).toFloat()
        viewBinding.reviewTv.text= intent.getStringExtra(BookReviewActivity.REVIEW_KEY)
        viewBinding.bookImage.setImageResource(intent.getIntExtra(BookReviewActivity.IMAGE_KEY, R.drawable.hob_logo))



        editBtn = viewBinding.editbtn
        editBtn.setOnClickListener {
            val edit: Intent = Intent(this, EditBookReviewActivity::class.java)
            startActivity(edit)
            finish()
        }

        deleteBtn = viewBinding.deletebtn
        viewBinding.deletebtn.setOnClickListener {
            val delete: Intent = Intent(this, DeleteBookReviewActivity::class.java)
            startActivity(delete)
        }
    }
}
