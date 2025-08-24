package com.surelabsid.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.surelabsid.core.data.source.local.entity.PokemonEntity
import com.surelabsid.core.data.source.local.entity.PokemonFavEntity
import com.surelabsid.core.data.source.network.model.PokeDetail
import com.surelabsid.core.utils.helpers.toJson
import com.surelabsid.core.utils.helpers.toJsonStatisticsData
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon ORDER BY _id ASC LIMIT :limit OFFSET :offset ")
    suspend fun findAllPokemon(offset: Int, limit: Int): List<PokemonEntity>

    @Query("SELECT * FROM pokemon WHERE name LIKE :name")
    fun findOnePokemon(name: String): Flow<PokemonEntity?>

    @Query("SELECT * FROM pokemon WHERE id = :id")
    suspend fun findOnePokemonById(id: Int): PokemonEntity?

    @Query("UPDATE pokemon SET height = :height, weight = :weight, imageUrl = :imageUrl, abilities = :abilities, stats = :stats, types = :types WHERE id = :id")
    suspend fun updatePokemonById(
        id: Int,
        height: Int,
        weight: Int,
        imageUrl: String,
        abilities: String,
        stats: String,
        types: String
    )

    @Transaction
    suspend fun updateIfConnectedAndGet(
        detail: PokeDetail?,
        id: Int,
        isConnected: Boolean
    ): PokemonEntity? {
        if (isConnected && detail != null) {
            updatePokemonById(
                id = id,
                height = detail.height.toInt(),
                weight = detail.weight.toInt(),
                imageUrl = detail.images.toJson(),
                abilities = detail.abilities.toJson(),
                stats = detail.statistics.toJsonStatisticsData(),
                types = detail.types.toJson()
            )
        }
        return findOnePokemonById(id)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemon(pokemon: List<PokemonEntity>)

    @Insert(entity = PokemonFavEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonToFav(pokemon: PokemonFavEntity)

}