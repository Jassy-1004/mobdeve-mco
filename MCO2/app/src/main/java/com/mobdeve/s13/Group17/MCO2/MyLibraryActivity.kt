package com.mobdeve.s13.Group17.MCO2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMylibraryBinding

class MyLibraryActivity : AppCompatActivity() {
    //data
    private val reviewList: ArrayList<BookReview> = DataHelper.initializedData()

    private lateinit var recyclerViewLibrary: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var adapter: MyAdapterReview

    private val bookReviewResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){result: ActivityResult ->
        if (result.resultCode == RESULT_OK){

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewBinding: ActivityMylibraryBinding = ActivityMylibraryBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val homepage : Intent = Intent(this, MainActivity::class.java);
        val logout : Intent = Intent(this, StartPage::class.java);
        val library:Intent= Intent (this,MyLibraryActivity::class.java);
        val profile:Intent= Intent (this,ProfileActivity::class.java);

        this.recyclerViewLibrary = viewBinding.recyclerViewLibrary
        this.adapter = MyAdapterReview(reviewList, bookReviewResultLauncher)
        this.recyclerViewLibrary.adapter = adapter

        this.recyclerViewLibrary.layoutManager = LinearLayoutManager(this)

        bottomNavigationView= viewBinding.bottomNavigationView

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home-> {
                    startActivity(homepage)
                    finish()
                }
                R.id.nav_logout->{
                    startActivity(logout)
                    finish()
                }
                R.id.nav_books-> {
                    startActivity(library)
                    finish()
                }
                R.id.nav_profile-> {
                    startActivity(profile)
                    finish()
                }


            }
            true
        }


    }

}
