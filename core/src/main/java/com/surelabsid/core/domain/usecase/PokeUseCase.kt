package com.surelabsid.core.domain.usecase

import com.surelabsid.core.data.common.Resources
import com.surelabsid.core.data.source.local.entity.PokemonEntity
import com.surelabsid.core.data.source.network.model.GeneralMessage
import com.surelabsid.core.data.source.network.model.PokeDetail
import kotlinx.coroutines.flow.Flow

interface PokeUseCase {
    fun getPokeList(offset: Int, limit: Int): Flow<Resources<List<PokemonEntity>>>
    fun getPokeDetail(id: String): Flow<Resources<PokeDetail?>>
    fun getPokemonFavorite(): Flow<Resources<List<PokemonEntity>>>
    fun setToFavorite(id: String): Flow<Resources<GeneralMessage>>
    fun checkIsFavorite(id: String): Flow<Resources<Boolean>>
}