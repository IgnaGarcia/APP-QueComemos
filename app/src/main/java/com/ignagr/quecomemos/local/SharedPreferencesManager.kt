package com.ignagr.quecomemos.local

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import com.ignagr.quecomemos.entities.Filter
import com.ignagr.quecomemos.entities.Selection

class SharedPreferencesManager(
    val context: Context) {
    protected val PREFERENCES_NAME = "quecomemos_preferences"

    private val LAST_UPDATE = "last_update_preference"
    private val SELECTION = "last_selection_preference"
    private val LAST_FILTER = "last_filter_preference"

    private val gson = Gson()

    private fun getPreferences(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    /** SELECTION **/
    fun saveSelection(selection: Selection) {
        getPreferences(context)
            .edit()
            .putString(SELECTION, gson.toJson(selection))
            .apply()

        setLastUpdate()
    }

    fun getSelection(): Selection? {
        return gson.fromJson(getPreferences(context)
            .getString(SELECTION, ""), Selection::class.java)
    }

    /** LAST UPDATE **/
    private fun setLastUpdate() {
        getPreferences(context)
            .edit()
            .putString(LAST_UPDATE, gson.toJson(System.currentTimeMillis()))
            .apply()
    }

    fun getLastUpdate(): Long? {
        return gson.fromJson(getPreferences(context)
            .getString(LAST_UPDATE, ""), Long::class.java)
    }

    /** FILTER **/
    fun setLastFilter(filter: Filter) {
        getPreferences(context)
            .edit()
            .putString(LAST_FILTER, gson.toJson(filter))
            .apply()
    }

    fun getLastFilter(): Filter? {
        return gson.fromJson(getPreferences(context)
            .getString(LAST_FILTER, ""), Filter::class.java)
    }
}