package com.surelabsid.core.data.source.local

import android.app.Application
import com.surelabsid.core.data.common.CommonPrefs
import com.surelabsid.core.data.common.Resources
import com.surelabsid.core.data.source.local.entity.PokemonEntity
import com.surelabsid.core.data.source.local.entity.PokemonFavEntity
import com.surelabsid.core.data.source.local.room.PokemonDao
import com.surelabsid.core.data.source.network.model.GeneralMessage
import com.surelabsid.core.data.source.network.model.PokeDetail
import com.surelabsid.core.data.source.network.services.PokeService
import com.surelabsid.core.domain.usecase.PokeUseCase
import com.surelabsid.core.utils.helpers.NetworkHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import java.util.Date

class LocalDataSource(
    private val service: PokeService,
    private val pokeDao: PokemonDao,
    private val application: Application
) :
    PokeUseCase {
    override fun getPokeList(
        offset: Int,
        limit: Int
    ): Flow<Resources<List<PokemonEntity>>> {
        return flow {
            emit(Resources.Loading())
            try {
                val remote = if (NetworkHelper.isNetworkAvailable(application)) {
                    val entity = service.getPokeList(offset, limit).results?.map {
                        it?.toPokeEntity() ?: PokemonEntity()
                    } ?: emptyList()
                    //insert to database
                    pokeDao.insertPokemon(entity)

                    //update to last sync
                    CommonPrefs.lastSync = Date().toString()

                    //return poke entity
                    entity
                } else pokeDao.findAllPokemon(offset, limit)

                emit(Resources.Success(remote))
            } catch (e: Exception) {
                e.printStackTrace()
                val findAll = pokeDao.findAllPokemon(offset, limit)
                emit(Resources.Success(findAll))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getPokeDetail(id: String): Flow<Resources<PokeDetail?>> {
        return flow {
            emit(Resources.Loading())
            try {
                val isConnected = NetworkHelper.isNetworkAvailable(application)
                val detailFromServer = if (isConnected) service.getPokeDetail(id)
                    .toPokeDetail() else null
                val pokeDetail =
                    pokeDao.updateIfConnectedAndGet(detailFromServer, id.toInt(), isConnected)
                        ?.toPokemonDetail()

                Timber.e("pokeDetail: $pokeDetail")
                emit(Resources.Success(pokeDetail))
            } catch (e: Exception) {
                e.printStackTrace()
                val detailPokemon = pokeDao.findOnePokemonById(id.toInt())
                emit(Resources.Success(detailPokemon?.toPokemonDetail()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getPokemonFavorite(): Flow<Resources<List<PokemonEntity>>> {
        return flow {
            emit(Resources.Loading())
            try {
                val findAll = pokeDao.findAllPokemonFavorite()
                emit(Resources.Success(findAll))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resources.Error(e.message.toString()))
            }
        }
    }

    override fun setToFavorite(id: String): Flow<Resources<GeneralMessage>> {
        return flow {
            emit(Resources.Loading())
            try {
                val findOneEntity = pokeDao.findOnePokemonFav(id.toInt())
                if (findOneEntity == null) {
                    val favEntity = PokemonFavEntity(id.toInt())
                    pokeDao.insertPokemonToFav(favEntity)
                    emit(
                        Resources.Success(
                            GeneralMessage(
                                "Berhasil menambahkan ke favorit.",
                                true
                            )
                        )
                    )
                } else {
                    pokeDao.removeFromFavorite(id.toInt())
                    emit(
                        Resources.Success(
                            GeneralMessage(
                                "Berhasil menghapus dari favorit.",
                                false
                            )
                        )
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resources.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun checkIsFavorite(id: String): Flow<Resources<Boolean>> {
        return flow {
            emit(Resources.Loading())
            try {
                val favEntity = pokeDao.findOnePokemonFav(id.toInt())
                emit(Resources.Success(favEntity != null))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resources.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

}