package com.zeezaglobal.digitalprescription.SharedPreference

import android.content.Context
import android.content.SharedPreferences

object UserId {
    private const val PREF_NAME = "auth_prefs"
    private const val TOKEN_KEY = "user_id"

    private lateinit var prefs: SharedPreferences

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setId(token: Int) {
        prefs.edit().putInt(TOKEN_KEY, token).apply()
    }

    fun getId(): Int {
        return prefs.getInt(TOKEN_KEY, -1)
    }

    fun clearId() {
        prefs.edit().remove(TOKEN_KEY).apply()
    }
}