package com.example.tp_mobile.views.fireball

import android.app.LauncherActivity
import android.content.Context
import android.widget.BaseAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.example.Fireball
import com.example.tp_mobile.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.ZonedDateTime

class CustomFireballAdapter(private val onCLick:(Fireball) -> Unit, private val data: List<Fireball>) :
    RecyclerView.Adapter<CustomFireballAdapter.FireballViewHolder>() {

    // ViewHolder pour contenir les vues des éléments de la liste
    class FireballViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dateTextView: TextView = view.findViewById(R.id.date)
        val speedTextView: TextView = view.findViewById(R.id.speed_text)
        val powerTextView: TextView = view.findViewById(R.id.power_text)
    }

    // Crée les vues pour chaque élément de la liste
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FireballViewHolder {
        // Inflate le layout pour chaque élément
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fireball_item_layout, parent, false)
        return FireballViewHolder(view)
    }

    // Remplace le contenu de chaque vue avec les données de l'élément correspondant
    override fun onBindViewHolder(holder: FireballViewHolder, position: Int) {
        val fireball = data[position]

        // Remplit les TextView avec les données de l'objet Fireball
        holder.dateTextView.text = formatDate(fireball.date)
        holder.speedTextView.text = "${fireball.vel} km/s"
        holder.powerTextView.text = "${fireball.energy} J"

        holder.itemView.setOnClickListener {
            onCLick.invoke(fireball)
        }
    }

    // Retourne le nombre d'éléments dans la liste
    override fun getItemCount(): Int = data.size

    // Méthode pour formater la date de type ZonedDateTime en dd/MM/yyyy
    private fun formatDate(dateString: String?): String {
        val zonedDateTime = ZonedDateTime.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return zonedDateTime.format(formatter)
    }
}