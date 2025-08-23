package com.surelabsid.core.data.source.network.response.pokelist

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.surelabsid.core.data.source.network.model.PokemonItem
import com.surelabsid.core.utils.helpers.getPokeId
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokeListResponse(

    @field:SerializedName("next")
    val next: String? = null,

    @field:SerializedName("previous")
    val previous: String? = null,

    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("results")
    val results: List<ResultsItem?>? = null
) : Parcelable

@Parcelize
data class ResultsItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable {

    fun toPokemonItem(): PokemonItem {
        val id = url?.getPokeId()
        return PokemonItem(
            id = id.orEmpty().padStart(3, '0'),
            url = url.orEmpty(),
            name = name.orEmpty(),
            imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
        )
    }
}
