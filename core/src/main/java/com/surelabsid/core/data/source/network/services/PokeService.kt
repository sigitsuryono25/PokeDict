package com.surelabsid.core.data.source.network.services

import com.surelabsid.core.data.source.network.Endpoints
import com.surelabsid.core.data.source.network.response.pokedetail.PokeDetailResponse
import com.surelabsid.core.data.source.network.response.pokelist.PokeListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeService {

    @GET(Endpoints.POKE_LIST)
    suspend fun getPokeList(
        @Query(Endpoints.OFFSET) offset: Int,
        @Query(Endpoints.LIMIT) limit: Int
    ): PokeListResponse

    @GET(Endpoints.POKE_DETAIL)
    suspend fun getPokeDetail(
        @Path(Endpoints.PATH_ID) id: String
    ): PokeDetailResponse
}