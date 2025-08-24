package com.surelabsid.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.surelabsid.core.data.source.local.entity.PokemonEntity
import com.surelabsid.core.data.source.local.entity.PokemonFavEntity

@Database(entities = [PokemonEntity::class, PokemonFavEntity::class], version = 2)
abstract class PokemonDatabase : RoomDatabase() {
    abstract fun appDao(): PokemonDao
}