package com.mobdeve.s13.Group17.MCO2


import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityEditprofileBinding

/*
MCO4
GROUP 17
CHUA, JASMIN
SHI, KAYE
TAN, HAILY
*/

class EditProfile : AppCompatActivity() {
    private lateinit var done: Button
    private lateinit var discard: Button

    companion object {
        const val USERNAME_KEY = "USERNAME_KEY"
        const val NAME_KEY = "NAME_KEY"
        const val BIO_KEY = "BIO_KEY"
        private const val UNAME = "Username"
    }


    val db = FirebaseFirestore.getInstance();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityEditprofileBinding =
            ActivityEditprofileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val username = sharedPrefs.getString("username", null).toString()

        Log.d(TAG, "DocumentSnapshot data: $username")

        db.collection("UserInfo").whereEqualTo("Username", username)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result) {
                        viewBinding.profileName.setText(document.data["Name"].toString())
                        viewBinding.profileBio.setText(document.data["Bio"].toString())
                        Log.d(TAG, document.id + " => " + document.data)
                    }
                } else {
                    Log.w(ContentValues.TAG, "Error getting documents.", task.exception)
                }
            }


        // clicking the done button would start activity to MyProfileActivity
        done = viewBinding.editComplete
        done.setOnClickListener {
            val intent: Intent = Intent(this, MyProfileActivity::class.java)

            var id: String = ""
            val bio = viewBinding.profileBio.text.toString()
            val name = viewBinding.profileName.text.toString()

            db.collection("UserInfo").whereEqualTo("Username", username).get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        for (document in task.result) {
                            id = document.id
                            Log.w(TAG, "id: $id")
                        }

                        db.runTransaction { transaction ->
                            val snapshot = transaction.get(db.collection("UserInfo").document(id))
                            transaction.update(
                                db.collection("UserInfo").document(id),
                                "Name",
                                name,
                                "Bio",
                                bio
                            )
                            // Success
                            null
                        }.addOnSuccessListener {
                            Log.d(TAG, "Transaction success!")
                            intent.putExtra(
                                EditProfile.UNAME,
                                this.intent.getStringExtra(MyProfileActivity.UNAME).toString()
                            )
                            startActivity(intent)
                            finishAffinity()
                        }
                    } else {
                        Log.w(TAG, "Error getting documents.", task.exception)
                    }
                }
        }
        // clicking the discard button would finish the current activity
        discard = viewBinding.discardBtn
        discard.setOnClickListener {
            finish()
        }

    }

}