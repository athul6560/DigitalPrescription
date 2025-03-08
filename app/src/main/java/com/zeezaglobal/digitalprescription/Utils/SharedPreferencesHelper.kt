package com.zeezaglobal.digitalprescription.Utils

import android.content.Context

class SharedPreferencesHelper(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("APP_PREFS", Context.MODE_PRIVATE)

    fun getToken(): String {
        return sharedPreferences.getString("jwt_token", "") ?: ""
    }

    fun getUserId(): Long {
        return sharedPreferences.getInt("user_id", -1).takeIf { it != -1 }?.toLong() ?: 0L
    }
}
