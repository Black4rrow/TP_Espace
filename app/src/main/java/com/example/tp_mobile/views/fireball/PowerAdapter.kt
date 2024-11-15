package com.example.tp_mobile.views.fireball

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mobile.R
import com.example.tp_mobile.model.Fireball

class PowerAdapter(private val itemList: List<Fireball>) : RecyclerView.Adapter<PowerAdapter.PowerViewHolder>() {

    inner class PowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.power_text)
        val imageView: ImageView = itemView.findViewById(R.id.power_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PowerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.best_power_fireball_item_layout, parent, false)
        return PowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PowerViewHolder, position: Int) {
        val fireball = itemList[position]
        holder.textView.text = "${fireball?.impactE ?: "?"} J"
        holder.imageView.setImageResource(R.drawable.power_icon)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
