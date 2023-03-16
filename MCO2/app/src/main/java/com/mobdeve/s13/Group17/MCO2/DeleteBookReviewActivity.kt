package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.Group17.MCO2.databinding.DialogDeletionBinding


class DeleteBookReviewActivity : AppCompatActivity() {
    private lateinit var yes: Button
    private lateinit var no: Button

     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         val viewBinding: DialogDeletionBinding = DialogDeletionBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
         yes = viewBinding.yesBtn
         yes.setOnClickListener {
             val intent: Intent = Intent(this, MyLibraryActivity::class.java);
             startActivity(intent)
             finishAffinity()
         }
         no = viewBinding.noBtn
         no.setOnClickListener{
             val intent: Intent = Intent (this, BookReviewActivity::class.java)
             startActivity(intent)
             finishAffinity()
         }
    }

}