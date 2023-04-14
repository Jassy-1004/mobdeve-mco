package com.mobdeve.s13.Group17.MCO2

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.Group17.MCO2.databinding.DialogDeletionBinding

/*
MCO4
GROUP 17
CHUA, JASMIN
SHI, KAYE
TAN, HAILY
*/

class DeleteBookReviewActivity : AppCompatActivity() {
    private lateinit var yes: Button
    private lateinit var no: Button

    // initialize database instance
    private val db = FirebaseFirestore.getInstance();

    companion object{
        const val BOOK_TITLE_KEY = "BOOK_TITLE_KEY"
        const val UNAME="USERNAME"
    }
    
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

         // set content view using view binding
         val viewBinding: DialogDeletionBinding = DialogDeletionBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

         //clicking the yes button would start activity for MyLibraryActivity
         yes = viewBinding.yesBtn
         yes.setOnClickListener {
             var id: String = ""

             // find the document id to delete
             db.collection("UserReviews").whereEqualTo("User", intent.getStringExtra(
                 EditBookReviewActivity.UNAME)).whereEqualTo("Book Title", intent.getStringExtra(
                 EditBookReviewActivity.BOOK_TITLE_KEY)).get()
                 .addOnCompleteListener { task ->
                     if (task.isSuccessful) {
                         for (document in task.result) {
                             id = document.id
                             Log.w(TAG, "id: $id")

                             // delete document from database
                             db.collection("UserReviews").document(id).delete()
                                 .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
                                 .addOnFailureListener { Log.w(TAG, "Error deleting document") }
                         }
                     }
                 }

             // go back to MyLibraryActivity
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