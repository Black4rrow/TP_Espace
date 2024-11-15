package com.example.tp_mobile.views.fireball

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mobile.R
import com.example.tp_mobile.model.Fireball
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class PowerAdapter(private val itemList: List<Fireball>) : RecyclerView.Adapter<PowerAdapter.PowerViewHolder>() {

    inner class PowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.best_power_date_text)

        val speedTextView: TextView = itemView.findViewById(R.id.best_power_speed_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PowerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.best_power_fireball_item_layout, parent, false)
        return PowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PowerViewHolder, position: Int) {
        val fireball = itemList[position]
        holder.dateTextView.text ="Report date : \n${formatDate(fireball?.date ?: "")}"

        holder.speedTextView.text = "${fireball?.vel ?: "?"} km/s"
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun formatDate(dateString: String): String {
        val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

        val zonedDateTime = ZonedDateTime.parse(dateString, inputFormatter)

        return zonedDateTime.format(outputFormatter)
    }
}
