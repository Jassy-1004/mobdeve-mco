package com.mobdeve.s13.Group17.MCO2

import android.content.ClipData.Item
import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mobdeve.s13.Group17.MCO2.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    // data
    private val bookList: ArrayList<Books> = DataHelper.initializeData()
    private lateinit var logout: ImageButton
    // RecyclerView reference
    private lateinit var recyclerView: RecyclerView
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewBinding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        val dropdown = findViewById<Spinner>(R.id.filter)
        val items = arrayOf("Most Rated", "Latest Books", "Oldest Books")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        val homepage : Intent = Intent(this, MainActivity::class.java);
        val logout : Intent = Intent(this, StartPage::class.java);



        dropdown.adapter = adapter

        // Initialize the RecyclerView
        this.recyclerView = viewBinding.recyclerView

        // Set the Adapter.
        this.recyclerView.adapter = MyAdapter(this.bookList)

        // Set the LayoutManager.
        this.recyclerView.layoutManager = LinearLayoutManager(this)

        bottomNavigationView= findViewById(R.id.bottomNavigationView)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_home->startActivity(homepage)
                R.id.nav_logout->startActivity(logout)


            }
            true
        }


    }

 
}