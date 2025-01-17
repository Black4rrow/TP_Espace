package com.example.tp_mobile.views.fireball

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mobile.R
import com.example.tp_mobile.model.Fireball
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class PowerAdapter(
    private val onClick: (Fireball) -> Unit,
    private var itemList: List<Fireball>
) : RecyclerView.Adapter<PowerAdapter.PowerViewHolder>() {

    inner class PowerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.best_power_date_text)
        val speedTextView: TextView = itemView.findViewById(R.id.best_power_speed_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PowerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.best_power_fireball_item_layout, parent, false)
        return PowerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PowerViewHolder, position: Int) {
        val fireball = itemList[position]
        holder.dateTextView.text = "${formatDate(fireball?.date ?: "")}"
        holder.speedTextView.text = "${fireball?.vel ?: "?"} km/s"
        holder.itemView.setOnClickListener {
            onClick(fireball)
        }
    }

    override fun getItemCount(): Int = itemList.size

    private fun formatDate(dateString: String): String {
        return try {
            val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            val outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
            val zonedDateTime = ZonedDateTime.parse(dateString, inputFormatter)
            zonedDateTime.format(outputFormatter)
        } catch (e: Exception) {
            "Invalid Date"
        }
    }

    fun updateData(newFireballs: List<Fireball>) {
        itemList = newFireballs
        notifyDataSetChanged()
    }
}
