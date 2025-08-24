package com.surelabsid.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.surelabsid.core.data.source.network.model.PokeDetail
import com.surelabsid.core.utils.helpers.toList
import com.surelabsid.core.utils.helpers.toStatisticsData
import java.util.Date

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true) val _id: Int = 0,
    val id: String = "",
    val pokeId: String = "",
    val name: String = "",
    val imageUrl: String = "",
    val cover: String = "",
    val types: String = "",
    val height: Float = 0F,
    val weight: Float = 0F,
    val abilities: String = "",
    val stats: String = "",
    val createdAt: Long = Date().time
) {
    fun toPokemonDetail(): PokeDetail {
        return PokeDetail(
            pokeId = id.toString(),
            pokeName = name,
            images = imageUrl.toList(),
            types = types.toList(),
            height = height,
            weight = weight,
            abilities = abilities.toList(),
            statistics = stats.toStatisticsData()
        )
    }
}