package com.example.myapplication.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R

class SliderAdapter ( val slideItems : MutableList<SlideItem>, val viewPager2: ViewPager2) : RecyclerView.Adapter<SliderViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.slider_item, parent, false)
        return SliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bind(slideItems.get(position))
        if(position == slideItems.size -1){
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int = slideItems.size

    private val runnable = Runnable {
        slideItems.addAll(slideItems)
        notifyDataSetChanged()
    }
}

class SliderViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    fun bind(slideItem : SlideItem){
        val img : ImageView = view.findViewById(R.id.img)
        img.setImageResource(slideItem.image)
    }

}