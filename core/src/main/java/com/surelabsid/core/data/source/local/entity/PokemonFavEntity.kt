package com.surelabsid.core.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "pokemon_favorites")
data class PokemonFavEntity(
    @PrimaryKey
    val id: Int,
    val createdAt: Long = Date().time
)