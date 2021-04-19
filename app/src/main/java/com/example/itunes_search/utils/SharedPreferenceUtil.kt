package com.example.itunes_search.utils

import android.content.Context
import com.example.itunes_search.model.SearchModel
import com.google.gson.Gson


class SharedPreferenceUtil {
    companion object {
        fun saveData(context: Context, searchModel: SearchModel) {
            val sharedPreferences =
                context.getSharedPreferences("Data", 0)
            val sharedPreferencesEditor = sharedPreferences.edit()
            val gson = Gson()
            val serializedObject = gson.toJson(searchModel)
            sharedPreferencesEditor.putString("SearchTemp", serializedObject)
            sharedPreferencesEditor.commit()
        }

        fun getData(
            context: Context
        ): SearchModel? {
            val sharedPreferences =
                context.getSharedPreferences("Data", 0)
            if (sharedPreferences.contains("SearchTemp")) {
                val gson = Gson()
                return gson.fromJson(sharedPreferences.getString("SearchTemp", ""), SearchModel::class.java)
            }
            return null
        }
    }
}