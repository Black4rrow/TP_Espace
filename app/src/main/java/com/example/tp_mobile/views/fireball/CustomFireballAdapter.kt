package com.example.tp_mobile.views.fireball

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp_mobile.R
import com.example.tp_mobile.model.Fireball
import com.example.tp_mobile.model.OnFireballFavoriteListener
import com.example.tp_mobile.utils.SortStyle
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CustomFireballAdapter(
    private val onCLick: (Fireball) -> Unit,
    private val data: MutableList<Fireball>,
    private val listener: OnFireballFavoriteListener
) :
    RecyclerView.Adapter<CustomFireballAdapter.FireballViewHolder>() {


    // ViewHolder pour contenir les vues des éléments de la liste
    class FireballViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.date)
        val speedTextView: TextView = view.findViewById(R.id.speed_text)
        val powerTextView: TextView = view.findViewById(R.id.power_text)
        val favButton: ImageButton = view.findViewById(R.id.fav_button)
    }

    // Crée les vues pour chaque élément de la liste
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FireballViewHolder {
        // Inflate le layout pour chaque élément
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fireball_item_layout, parent, false)
        return FireballViewHolder(view)
    }

    override fun onBindViewHolder(holder: FireballViewHolder, position: Int) {
        var isFavorite = false;
        val fireball = data[position]

        holder.dateTextView.text = formatDate(fireball.date)
        holder.dateTextView.paintFlags =
            holder.dateTextView.paintFlags or android.graphics.Paint.UNDERLINE_TEXT_FLAG

        holder.speedTextView.text = "${fireball?.vel ?: "?"} km/s"
        holder.powerTextView.text = "${fireball?.energy ?: "?"} J"

        if (fireball.isFavorite!!) {
            holder.favButton.setImageResource(R.drawable.baseline_favorite_24)
            holder.favButton.setColorFilter(R.color.red)
        } else {
            holder.favButton.setImageResource(R.drawable.baseline_favorite_border_24)
            holder.favButton.setColorFilter(R.color.black)
        }

        holder.favButton.setOnClickListener {
            listener.onFavoriteClicked(fireball, holder, position)
        }

        holder.itemView.setOnClickListener {
            onCLick.invoke(fireball)
        }
    }

    override fun getItemCount(): Int = data.size

    // Méthode pour formater la date de type ZonedDateTime en dd/MM/yyyy
    private fun formatDate(dateString: String?): String {
        val zonedDateTime = ZonedDateTime.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return zonedDateTime.format(formatter)
    }

    fun addData(newData: MutableList<Fireball>) {
        data.addAll(newData)
        notifyDataSetChanged()
    }

    fun replaceAllData(newData: MutableList<Fireball>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    fun sortData(sortStyle: SortStyle) {
        when (sortStyle) {
            SortStyle.NONE -> {

            }

            SortStyle.DATE_ASC -> {
                val sortedData = data.sortedBy { it.date }
                replaceAllData(sortedData.toMutableList())
            }

            SortStyle.DATE_DESC -> {
                val sortedData = data.sortedByDescending { it.date }
                replaceAllData(sortedData.toMutableList())
            }

            SortStyle.SPEED_ASC -> {
                val sortedData = data.sortedBy { it.vel }
                replaceAllData(sortedData.toMutableList())
            }

            SortStyle.SPEED_DESC -> {
                val sortedData = data.sortedByDescending { it.vel }
                replaceAllData(sortedData.toMutableList())
            }

            SortStyle.RADIATED_ENERGY_ASC -> {
                val sortedData = data.sortedBy { it.energy }
                replaceAllData(sortedData.toMutableList())
            }

            SortStyle.RADIATED_ENERGY_DESC -> {
                val sortedData = data.sortedByDescending { it.energy }
                replaceAllData(sortedData.toMutableList())
            }
        }

        notifyDataSetChanged()
    }

    fun getData(): MutableList<Fireball> {
        return data
    }

    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }
}