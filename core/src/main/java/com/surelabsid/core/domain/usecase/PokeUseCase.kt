package com.surelabsid.core.domain.usecase

import com.surelabsid.core.data.common.Resources
import com.surelabsid.core.data.source.network.model.PokemonItem
import com.surelabsid.core.data.source.network.response.pokedetail.PokeDetailResponse
import com.surelabsid.core.data.source.network.response.pokelist.PokeListResponse
import kotlinx.coroutines.flow.Flow

interface PokeUseCase {
    fun getPokeList(offset: Int, limit: Int): Flow<Resources<List<PokemonItem?>?>>
    fun getPokeDetail(id: String): Flow<Resources<PokeDetailResponse>>
}