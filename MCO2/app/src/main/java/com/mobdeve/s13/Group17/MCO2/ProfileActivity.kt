package com.mobdeve.s13.Group17.MCO2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityBookinfoBinding
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    companion object{
        const val FIRSTNAME_TITLE_KEY = "FIRSTNAME_TITLE_KEY"
        const val LASTNAME_KEY = "LASTNAME_KEY"
        const val USERNAME_KEY = "USERNAME_KEY"
        const val GENDER_KEY = "GENDER_KEY"
        const val BIRTHDAY_KEY = "BIRTHDAY_KEY"
        const val BIO_KEY = "BIO_KEY"
        const val POSITION_KEY = "POSITION_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.profileName.text = intent.getStringExtra(ProfileActivity.FIRSTNAME_TITLE_KEY)
        viewBinding.profileName.text = intent.getStringExtra(ProfileActivity.LASTNAME_KEY)
        viewBinding.profileUsername.text = intent.getStringExtra(ProfileActivity.USERNAME_KEY)
        viewBinding.profileGender.text = intent.getStringExtra(ProfileActivity.GENDER_KEY)
        viewBinding.profileBirthdate.text = intent.getStringExtra(ProfileActivity.BIRTHDAY_KEY)
        viewBinding.profileBio.text = intent.getStringExtra(ProfileActivity.BIO_KEY)
        val position = intent.getIntExtra(BookInfoActivity.POSITION_KEY, 0)

        viewBinding.profileName.setOnClickListener(View.OnClickListener {
            val intent : Intent = Intent()

            intent.putExtra(ProfileActivity.POSITION_KEY, position)

            setResult(Activity.RESULT_OK, intent)

            finish()
        })
    }
}