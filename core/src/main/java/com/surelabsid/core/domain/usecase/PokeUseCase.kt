package com.surelabsid.core.domain.usecase

import com.surelabsid.core.data.common.Resources
import com.surelabsid.core.data.source.local.entity.PokemonEntity
import com.surelabsid.core.data.source.network.model.PokeDetail
import com.surelabsid.core.data.source.network.model.PokemonItem
import com.surelabsid.core.data.source.network.response.pokedetail.PokeDetailResponse
import com.surelabsid.core.data.source.network.response.pokelist.PokeListResponse
import kotlinx.coroutines.flow.Flow

interface PokeUseCase {
    fun getPokeList(offset: Int, limit: Int): Flow<Resources<List<PokemonEntity>>>
    fun getPokeDetail(id: String): Flow<Resources<PokeDetail?>>
}