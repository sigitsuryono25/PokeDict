package com.surelabsid.core.data.source.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokeDetail(
    var pokeId: String,
    var pokeName: String,
    var height: Float,
    var weight: Float,
    var images: List<String?>,
    var abilities: List<String?>,
    var types: List<String?>,
    var statistics: List<StatisticsData?>
): Parcelable

@Parcelize
data class StatisticsData(
    var title: String,
    var power: Number
): Parcelable
