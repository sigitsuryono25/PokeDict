package com.surelabsid.core.domain.usecase

import com.surelabsid.core.data.common.Resources
import com.surelabsid.core.data.source.local.LocalDataSource
import com.surelabsid.core.data.source.local.entity.PokemonEntity
import com.surelabsid.core.data.source.network.model.GeneralMessage
import com.surelabsid.core.data.source.network.model.PokeDetail
import kotlinx.coroutines.flow.Flow

class PokeInteractor(private val localDataSource: LocalDataSource) : PokeUseCase {
    override fun getPokeList(offset: Int, limit: Int): Flow<Resources<List<PokemonEntity>>> {
        return localDataSource.getPokeList(offset, limit)
    }

    override fun getPokeDetail(id: String): Flow<Resources<PokeDetail?>> {
        return localDataSource.getPokeDetail(id)
    }

    override fun getPokemonFavorite(): Flow<Resources<List<PokemonEntity>>> {
        return localDataSource.getPokemonFavorite()
    }

    override fun setToFavorite(id: String): Flow<Resources<GeneralMessage>> {
        return localDataSource.setToFavorite(id)
    }

    override fun checkIsFavorite(id: String): Flow<Resources<Boolean>> {
        return localDataSource.checkIsFavorite(id)
    }

}