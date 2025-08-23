package com.surelabsid.core.domain.usecase

import com.surelabsid.core.data.common.Resources
import com.surelabsid.core.data.source.network.model.PokemonItem
import com.surelabsid.core.data.source.network.response.pokedetail.PokeDetailResponse
import com.surelabsid.core.data.source.network.services.PokeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokeInteractor(private val pokeService: PokeService) : PokeUseCase {
    override fun getPokeList(offset: Int, limit: Int): Flow<Resources<List<PokemonItem?>?>> {
        return flow {
            emit(Resources.Loading())
            try {
                val response = pokeService.getPokeList(offset, limit)
                val toPokemonItem = response.results?.map {
                    it?.toPokemonItem()
                }
                emit(Resources.Success(toPokemonItem))
            } catch (e: Exception) {
                emit(Resources.Error(e.message.toString()))
            }
        }
    }

    override fun getPokeDetail(id: String): Flow<Resources<PokeDetailResponse>> {
        return flow {
            emit(Resources.Loading())
            try {
                val response = pokeService.getPokeDetail(id)
                emit(Resources.Success(response))
            } catch (e: Exception) {
                emit(Resources.Error(e.message.toString()))
            }
        }
    }
}