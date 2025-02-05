package com.esraa.aroundegypt.data.cache

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    @TypeConverter
    fun fromList(value: List<RecommendedExperienceCache>): String {
        val gson = Gson()
        val type = object : TypeToken<List<RecommendedExperienceCache>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toList(value: String): List<RecommendedExperienceCache> {
        val gson = Gson()
        val type = object : TypeToken<List<RecommendedExperienceCache>>() {}.type
        return gson.fromJson(value, type)
    }
}