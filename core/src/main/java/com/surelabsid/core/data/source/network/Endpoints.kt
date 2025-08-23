package com.surelabsid.core.data.source.network

object Endpoints {
    const val BASE_API_V2 = "api/v2"
    const val LIMIT = "limit"
    const val OFFSET = "offset"
    const val PATH_ID = "id"

    const val POKE_LIST = "${BASE_API_V2}/pokemon"
    const val POKE_DETAIL = "${BASE_API_V2}/pokemon/{$PATH_ID}"
}