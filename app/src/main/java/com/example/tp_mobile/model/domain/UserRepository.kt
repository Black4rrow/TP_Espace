package com.example.tp_mobile.model.domain


import android.content.SharedPreferences
import com.example.tp_mobile.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object UserRepository {
// peut etre a rajouter firebase
private const val KEY_USERS_LIST = "key_users_list"
private val gson = Gson()

    fun getUsersInSharedPreferences(sharedPreferences: SharedPreferences): List<User> {
        val usersJson = sharedPreferences.getString(KEY_USERS_LIST, null)

        return if (usersJson != null) {

            val type = object : TypeToken<List<User>>() {}.type
            gson.fromJson(usersJson, type)
        } else {
            emptyList()
        }
    }

    private fun saveUsersInSharedPreferences(users: List<User>, sharedPreferences: SharedPreferences) {
        val editor = sharedPreferences.edit()

        val usersJson = gson.toJson(users)

        editor.putString(KEY_USERS_LIST, usersJson)
        editor.apply()
    }
    fun addUserToSharedPreferences(user: User, sharedPreferences: SharedPreferences) {
        val users = getUsersInSharedPreferences(sharedPreferences).toMutableList()
        users.add(user)
        saveUsersInSharedPreferences(users, sharedPreferences)
    }

}