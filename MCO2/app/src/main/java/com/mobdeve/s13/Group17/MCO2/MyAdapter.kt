package com.mobdeve.s13.Group17.MCO2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


    class MyAdapter(private val data: ArrayList<Books>): RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            // Create a LayoutInflater using the parent's context
            val inflater = LayoutInflater.from(parent.context)
            // Inflate a new View given the item_layout2.xml item view we created.
            val view = inflater.inflate(R.layout.item_layout, parent, false)
            // Return a new instance of our MyViewHolder passing the View object we created
            return MyViewHolder(view)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bindData(data.get(position))
        }

        override fun getItemCount(): Int {
            return data.size
        }
}