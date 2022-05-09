package com.codefrnd.mvvmapp.data.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

private const val KEY_SAVED_AT = "key_Saved_at"

class PreferenceProvider(
    context: Context
) {

    // PREVENT FROM MEMORY LEAKS
    private val appContext = context.applicationContext

    private val prefernce: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    fun saveLastSavedAt(savedAt: String) {
        prefernce.edit().putString(KEY_SAVED_AT, savedAt).apply()
    }

    fun getLastSavedAt(): String? = prefernce.getString(KEY_SAVED_AT, null)

}