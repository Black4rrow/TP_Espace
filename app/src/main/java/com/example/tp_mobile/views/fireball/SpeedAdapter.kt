package com.example.tp_mobile.views.fireball

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mobile.R
import com.example.tp_mobile.model.Fireball

class SpeedAdapter(private val itemList: List<Fireball>) : RecyclerView.Adapter<SpeedAdapter.SpeedViewHolder>() {

    inner class SpeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.speed_text)
        val imageView: ImageView = itemView.findViewById(R.id.speed_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.last_one_fireball_item_layout, parent, false)
        return SpeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpeedViewHolder, position: Int) {
        val fireball = itemList[position]
        holder.textView.text = "${fireball?.vel ?: "?"} J"
        holder.imageView.setImageResource(R.drawable.speed_icon)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
