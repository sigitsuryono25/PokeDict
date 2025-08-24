package com.surelabsid.core.utils.helpers

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.surelabsid.core.data.source.network.model.StatisticsData

@TypeConverter
fun List<String?>.toJson(): String = Gson().toJson(this)

@TypeConverter
fun List<StatisticsData?>.toJsonStatisticsData(): String = Gson().toJson(this)

@TypeConverter
fun String.toList(): List<String> {
    if (this.isEmpty()) return emptyList()
    val type = object : TypeToken<List<String>>() {}.type
    return Gson().fromJson(this, type)
}

fun String.toStatisticsData(): List<StatisticsData> {
    if (this.isEmpty()) return emptyList()
    val type = object : TypeToken<List<StatisticsData>>() {}.type
    return Gson().fromJson(this, type)
}
