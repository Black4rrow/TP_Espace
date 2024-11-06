package com.example.tp_mobile.model.domain.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDB(

	val name:String,
	val email: String,
	val password: String,
	val tintin: String? = null
//	val cards: List<MagicCard>
) {
	@PrimaryKey(autoGenerate = true)
	var idUser: Int? =null
}