package com.raxors.photobooth.utils

import android.content.SharedPreferences
import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

object Prefs {

    fun SharedPreferences.Editor.putParcelable(key: String, parcelable: Parcelable): SharedPreferences.Editor {
        val json = Gson().toJson(parcelable)
        return putString(key, json)
    }

    inline fun <reified T : Parcelable?> SharedPreferences.getParcelable(key: String, default: T? = null): T? {
        val json = getString(key, null)
        return try {
            if (json != null)
                Gson().fromJson(json, T::class.java)
            else default
        } catch (_: JsonSyntaxException) {
            default
        }
    }

}