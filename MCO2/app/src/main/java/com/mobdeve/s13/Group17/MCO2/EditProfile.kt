package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityEditprofileBinding

class EditProfile : AppCompatActivity() {
    private lateinit var done: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityEditprofileBinding =
            ActivityEditprofileBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        done = viewBinding.editComplete
        done.setOnClickListener {
            val intent: Intent = Intent(this, ProfileActivity::class.java);
            startActivity(intent)
            finish()

        }


    }
}
