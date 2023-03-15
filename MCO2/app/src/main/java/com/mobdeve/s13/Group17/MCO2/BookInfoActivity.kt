package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityBookinfoBinding

class BookInfoActivity : AppCompatActivity() {
    private lateinit var backbtn: ImageButton

    companion object{
        const val IMG_KEY="IMG_KEY"
        const val BOOK_TITLE_KEY = "BOOK_TITLE_KEY"
        const val AUTHOR_KEY = "AUTHOR_KEY"
        const val PUBLICATION_DATE_KEY = "PUBLICATION_DATE_KEY"
        const val ISBN_KEY = "ISBN_KEY"
        const val DESCRIPTION_KEY = "DESCRIPTION_KEY"
        const val POSITION_KEY = "POSITION_KEY"
        const val RATING_KEY = "RATING_KEY"
    }

    private val commentList: ArrayList<Comment> = DataHelper.initializeDatum()

    private lateinit var recyclerViewComment: RecyclerView

    private lateinit var adapter: MyAdapterComment
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityBookinfoBinding = ActivityBookinfoBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        this.recyclerViewComment = viewBinding.recyclerView2
        this.adapter = MyAdapterComment(commentList)


        this.recyclerViewComment.adapter = adapter

        this.recyclerViewComment.layoutManager = LinearLayoutManager(this)

        viewBinding.bookimg.setImageResource(
            intent.getIntExtra(
                IMG_KEY,
                R.drawable.hob_logo
            )
        )

        val title = intent.getStringExtra(BookInfoActivity.BOOK_TITLE_KEY)
        val author = intent.getStringExtra(BookInfoActivity.AUTHOR_KEY)
        val description = intent.getStringExtra(BookInfoActivity.DESCRIPTION_KEY)
        val image = intent.getIntExtra(BookInfoActivity.IMG_KEY, R.drawable.hob_logo)

        viewBinding.booktitletv.text = intent.getStringExtra(BOOK_TITLE_KEY)
        viewBinding.authortv.text = intent.getStringExtra(AUTHOR_KEY)
        viewBinding.publishdatetv.text = intent.getStringExtra(PUBLICATION_DATE_KEY)
        viewBinding.ISBNtv.text = intent.getStringExtra(ISBN_KEY)
        viewBinding.descriptiontv.text = intent.getStringExtra(DESCRIPTION_KEY)
        viewBinding.myRatingBar.rating = intent.getFloatExtra(RATING_KEY, 0F).toFloat()
        val position = intent.getIntExtra(POSITION_KEY, 0)

        backbtn = viewBinding.backbutton
        backbtn.setOnClickListener {
            finish()

        }

        viewBinding.addbtnFab.setOnClickListener {
            val intent: Intent = Intent(this, AddBookReview::class.java)

            intent.putExtra(AddBookReview.BOOK_TITLE_KEY, title)
            intent.putExtra(AddBookReview.AUTHOR_KEY, author)
            intent.putExtra(AddBookReview.DESCRIPTION_KEY, description)
            intent.putExtra(AddBookReview.IMG_KEY, image)

            startActivity(intent)
        }
    }
}