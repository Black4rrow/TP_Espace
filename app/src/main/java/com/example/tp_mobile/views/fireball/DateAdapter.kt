package com.example.tp_mobile.views.fireball

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mobile.R
import com.example.tp_mobile.model.Fireball

class DateAdapter(
    private val onClick: (Fireball) -> Unit,
    private var itemList: List<Fireball>
) : RecyclerView.Adapter<DateAdapter.DateViewHolder>() {

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val speedTextView: TextView = itemView.findViewById(R.id.top_date_speed_text)
        val powerTextView: TextView = itemView.findViewById(R.id.top_date_power_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.last_one_fireball_item_layout, parent, false)
        return DateViewHolder(view)
    }

    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val fireball = itemList[position]
        holder.speedTextView.text = "${(fireball?.vel ?: "")} km/s"
        holder.powerTextView.text = "${fireball?.energy ?: "?"} J"
        holder.itemView.setOnClickListener {
            onClick(fireball)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
    fun updateData(newFireballs: List<Fireball>) {
        itemList = newFireballs
        notifyDataSetChanged()
    }
}
