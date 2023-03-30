package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.Group17.MCO2.databinding.DialogDeletionBinding


class DeleteBookReviewActivity : AppCompatActivity() {
    private lateinit var yes: Button
    private lateinit var no: Button

    companion object{
        const val BOOK_TITLE_KEY = "BOOK_TITLE_KEY"
        const val UNAME="USERNAME"
    }

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         val viewBinding: DialogDeletionBinding = DialogDeletionBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

         //clicking the yes button would start activity for MyLibraryActivity
         yes = viewBinding.yesBtn
         yes.setOnClickListener {
             //add delete from database here

             val intent: Intent = Intent(this, MyLibraryActivity::class.java);
             intent.putExtra(MyLibraryActivity.UNAME, this.intent.getStringExtra(UNAME).toString())
             startActivity(intent)
             finishAffinity()
         }

         //clicking the no button would start activity for BookReviewActivity
         no = viewBinding.noBtn
         no.setOnClickListener{
             finish()
         }
    }

}