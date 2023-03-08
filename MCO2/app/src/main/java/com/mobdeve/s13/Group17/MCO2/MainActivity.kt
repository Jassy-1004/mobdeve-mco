package com.mobdeve.s13.Group17.MCO2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    // data
    private val characterList: ArrayList<Books> = DataHelper.initializeData()
    // RecyclerView reference
    private lateinit var recyclerView: RecyclerView
    lateinit var drawerLayout: DrawerLayout
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dropdown = findViewById<Spinner>(R.id.filter)
        val items = arrayOf("Most Rated", "Latest Books", "Oldest Books")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        dropdown.adapter = adapter

        // Initialize the RecyclerView
        this.recyclerView = findViewById(R.id.recyclerView)

        // Set the Adapter.
        this.recyclerView.adapter = MyAdapter(this.characterList)

        // Set the LayoutManager.
        this.recyclerView.layoutManager = LinearLayoutManager(this)

        drawerLayout = findViewById(R.id.my_drawer_layout)
        actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close)

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // to make the Navigation drawer icon always appear on the action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }
}