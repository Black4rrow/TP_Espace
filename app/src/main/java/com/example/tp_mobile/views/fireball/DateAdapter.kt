package com.example.tp_mobile.views.fireball

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mobile.R
import com.example.tp_mobile.model.Fireball

class DateAdapter(private val itemList: List<Fireball>) : RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val speedTextView: TextView = itemView.findViewById(R.id.speed_text)
        val speedImageView: ImageView = itemView.findViewById(R.id.speed_hint)

        val powerTextView: TextView = itemView.findViewById(R.id.power_text)
        val powerImageView: ImageView = itemView.findViewById(R.id.power_hint)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.last_one_fireball_item_layout, parent, false)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val fireball = itemList[position]
        holder.speedTextView.text = "${fireball?.vel ?: "?"} km/s"
        holder.speedImageView.setImageResource(R.drawable.speed_icon)

        holder.powerTextView.text = "${fireball?.impactE ?: "?"} J"
        holder.powerImageView.setImageResource(R.drawable.power_icon)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}
