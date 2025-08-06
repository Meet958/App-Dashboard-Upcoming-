package com.example.utils

import android.content.Context
import android.content.SharedPreferences

object TokenManager {
    private const val PREF_NAME = "meditrack_prefs"
    private const val TOKEN_KEY = "ed3243d1e0a326eccf5b8ab908cf44c65fb092495c2f7fa0e00da694f7033d06bb34da0aa1e950782a25466f214ef0aaf65d312f24cacfffc5717d30e6f0c941"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveToken(context: Context, token: String) {
        val editor = getPrefs(context).edit()
        editor.putString(TOKEN_KEY, token)
        editor.apply()
    }

    fun getToken(context: Context): String? {
        return getPrefs(context).getString(TOKEN_KEY, null)
    }

    fun clearToken(context: Context) {
        val editor = getPrefs(context).edit()
        editor.remove(TOKEN_KEY)
        editor.apply()
    }
}