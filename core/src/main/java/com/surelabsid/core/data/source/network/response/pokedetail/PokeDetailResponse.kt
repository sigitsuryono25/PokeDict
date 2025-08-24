package com.surelabsid.core.data.source.network.response.pokedetail

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.surelabsid.core.data.source.network.model.PokeDetail
import com.surelabsid.core.data.source.network.model.StatisticsData
import com.surelabsid.core.utils.helpers.getAllImageUrls
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokeDetailResponse(

    @field:SerializedName("types")
    val types: List<TypesItem?>? = null,

    @field:SerializedName("weight")
    val weight: Float? = null,

    @field:SerializedName("sprites")
    val sprites: Sprites? = null,

    @field:SerializedName("abilities")
    val abilities: List<AbilitiesItem?>? = null,

    @field:SerializedName("stats")
    val stats: List<StatsItem?>? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("height")
    val height: Float? = null,

    @field:SerializedName("order")
    val order: Int? = null
) : Parcelable {

    fun toPokeDetail(): PokeDetail {
        val newAbilities =
            abilities?.map { it?.ability?.name.toString().replaceFirstChar { it.uppercase() } }
                ?: emptyList()
        val types =
            types?.map { it?.type?.name.toString().replaceFirstChar { it.uppercase() } }?.toList()
                ?: emptyList()
        val stat = stats?.map {
            StatisticsData(
                title = it?.stat?.name.toString(),
                power = it?.baseStat ?: 0
            )
        }?.toList() ?: emptyList()
        val images = sprites?.other?.getAllImageUrls()

        return PokeDetail(
            pokeId = id.toString().padStart(3, '0'),
            pokeName = name.toString(),
            types = types,
            height = height ?: 0F,
            weight = weight ?: 0F,
            abilities = newAbilities,
            images = images ?: emptyList(),
            statistics = stat
        )
    }
}

@Parcelize
data class OmegarubyAlphasapphire(

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_female")
    val frontFemale: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class GenerationVii(

    @field:SerializedName("icons")
    val icons: Icons? = null,

    @field:SerializedName("ultra-sun-ultra-moon")
    val ultraSunUltraMoon: UltraSunUltraMoon? = null
) : Parcelable

@Parcelize
data class MovesItem(

    @field:SerializedName("version_group_details")
    val versionGroupDetails: List<VersionGroupDetailsItem?>? = null,

    @field:SerializedName("move")
    val move: Move? = null
) : Parcelable

@Parcelize
data class Silver(

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_transparent")
    val frontTransparent: String? = null,

    @field:SerializedName("back_shiny")
    val backShiny: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class Move(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable

@Parcelize
data class Showdown(

    @field:SerializedName("back_shiny_female")
    val backShinyFemale: String? = null,

    @field:SerializedName("back_female")
    val backFemale: String? = null,

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_female")
    val frontFemale: String? = null,

    @field:SerializedName("back_shiny")
    val backShiny: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class Species(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable

@Parcelize
data class VersionGroupDetailsItem(

    @field:SerializedName("level_learned_at")
    val levelLearnedAt: Int? = null,

    @field:SerializedName("version_group")
    val versionGroup: VersionGroup? = null,

    @field:SerializedName("move_learn_method")
    val moveLearnMethod: MoveLearnMethod? = null,

    @field:SerializedName("order")
    val order: String? = null
) : Parcelable

@Parcelize
data class Sprites(

    @field:SerializedName("back_shiny_female")
    val backShinyFemale: String? = null,

    @field:SerializedName("back_female")
    val backFemale: String? = null,

    @field:SerializedName("other")
    val other: Other? = null,

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("versions")
    val versions: Versions? = null,

    @field:SerializedName("front_female")
    val frontFemale: String? = null,

    @field:SerializedName("back_shiny")
    val backShiny: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class UltraSunUltraMoon(

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_female")
    val frontFemale: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class HeartgoldSoulsilver(

    @field:SerializedName("back_shiny_female")
    val backShinyFemale: String? = null,

    @field:SerializedName("back_female")
    val backFemale: String? = null,

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_female")
    val frontFemale: String? = null,

    @field:SerializedName("back_shiny")
    val backShiny: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class OfficialArtwork(

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class Generation(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable

@Parcelize
data class GenerationI(

    @field:SerializedName("yellow")
    val yellow: Yellow? = null,

    @field:SerializedName("red-blue")
    val redBlue: RedBlue? = null
) : Parcelable

@Parcelize
data class Stat(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable

@Parcelize
data class GenerationIv(

    @field:SerializedName("platinum")
    val platinum: Platinum? = null,

    @field:SerializedName("diamond-pearl")
    val diamondPearl: DiamondPearl? = null,

    @field:SerializedName("heartgold-soulsilver")
    val heartgoldSoulsilver: HeartgoldSoulsilver? = null
) : Parcelable

@Parcelize
data class Other(

    @field:SerializedName("dream_world")
    val dreamWorld: DreamWorld? = null,

    @field:SerializedName("showdown")
    val showdown: Showdown? = null,

    @field:SerializedName("official-artwork")
    val officialArtwork: OfficialArtwork? = null,

    @field:SerializedName("home")
    val home: Home? = null
) : Parcelable

@Parcelize
data class GenerationIii(

    @field:SerializedName("firered-leafgreen")
    val fireredLeafgreen: FireredLeafgreen? = null,

    @field:SerializedName("ruby-sapphire")
    val rubySapphire: RubySapphire? = null,

    @field:SerializedName("emerald")
    val emerald: Emerald? = null
) : Parcelable

@Parcelize
data class Animated(

    @field:SerializedName("back_shiny_female")
    val backShinyFemale: String? = null,

    @field:SerializedName("back_female")
    val backFemale: String? = null,

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_female")
    val frontFemale: String? = null,

    @field:SerializedName("back_shiny")
    val backShiny: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class Cries(

    @field:SerializedName("legacy")
    val legacy: String? = null,

    @field:SerializedName("latest")
    val latest: String? = null
) : Parcelable

@Parcelize
data class Crystal(

    @field:SerializedName("back_transparent")
    val backTransparent: String? = null,

    @field:SerializedName("back_shiny_transparent")
    val backShinyTransparent: String? = null,

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_transparent")
    val frontTransparent: String? = null,

    @field:SerializedName("front_shiny_transparent")
    val frontShinyTransparent: String? = null,

    @field:SerializedName("back_shiny")
    val backShiny: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class GenerationViii(

    @field:SerializedName("icons")
    val icons: Icons? = null
) : Parcelable

@Parcelize
data class Yellow(

    @field:SerializedName("front_gray")
    val frontGray: String? = null,

    @field:SerializedName("back_transparent")
    val backTransparent: String? = null,

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("back_gray")
    val backGray: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_transparent")
    val frontTransparent: String? = null
) : Parcelable

@Parcelize
data class Platinum(

    @field:SerializedName("back_shiny_female")
    val backShinyFemale: String? = null,

    @field:SerializedName("back_female")
    val backFemale: String? = null,

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_female")
    val frontFemale: String? = null,

    @field:SerializedName("back_shiny")
    val backShiny: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class BlackWhite(

    @field:SerializedName("back_shiny_female")
    val backShinyFemale: String? = null,

    @field:SerializedName("back_female")
    val backFemale: String? = null,

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("animated")
    val animated: Animated? = null,

    @field:SerializedName("front_female")
    val frontFemale: String? = null,

    @field:SerializedName("back_shiny")
    val backShiny: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class FireredLeafgreen(

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("back_shiny")
    val backShiny: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class StatsItem(

    @field:SerializedName("stat")
    val stat: Stat? = null,

    @field:SerializedName("base_stat")
    val baseStat: Int? = null,

    @field:SerializedName("effort")
    val effort: Int? = null
) : Parcelable

@Parcelize
data class VersionGroup(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable

@Parcelize
data class DreamWorld(

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_female")
    val frontFemale: String? = null
) : Parcelable

@Parcelize
data class GenerationV(

    @field:SerializedName("black-white")
    val blackWhite: BlackWhite? = null
) : Parcelable

@Parcelize
data class DiamondPearl(

    @field:SerializedName("back_shiny_female")
    val backShinyFemale: String? = null,

    @field:SerializedName("back_female")
    val backFemale: String? = null,

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_female")
    val frontFemale: String? = null,

    @field:SerializedName("back_shiny")
    val backShiny: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class RedBlue(

    @field:SerializedName("front_gray")
    val frontGray: String? = null,

    @field:SerializedName("back_transparent")
    val backTransparent: String? = null,

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("back_gray")
    val backGray: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_transparent")
    val frontTransparent: String? = null
) : Parcelable

@Parcelize
data class XY(

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_female")
    val frontFemale: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class Version(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable

@Parcelize
data class TypesItem(

    @field:SerializedName("slot")
    val slot: Int? = null,

    @field:SerializedName("type")
    val type: Type? = null
) : Parcelable

@Parcelize
data class MoveLearnMethod(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable

@Parcelize
data class GenerationIi(

    @field:SerializedName("gold")
    val gold: Gold? = null,

    @field:SerializedName("crystal")
    val crystal: Crystal? = null,

    @field:SerializedName("silver")
    val silver: Silver? = null
) : Parcelable

@Parcelize
data class GameIndicesItem(

    @field:SerializedName("game_index")
    val gameIndex: Int? = null,

    @field:SerializedName("version")
    val version: Version? = null
) : Parcelable

@Parcelize
data class Emerald(

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class Gold(

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_transparent")
    val frontTransparent: String? = null,

    @field:SerializedName("back_shiny")
    val backShiny: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class Versions(

    @field:SerializedName("generation-iii")
    val generationIii: GenerationIii? = null,

    @field:SerializedName("generation-ii")
    val generationIi: GenerationIi? = null,

    @field:SerializedName("generation-v")
    val generationV: GenerationV? = null,

    @field:SerializedName("generation-iv")
    val generationIv: GenerationIv? = null,

    @field:SerializedName("generation-vii")
    val generationVii: GenerationVii? = null,

    @field:SerializedName("generation-i")
    val generationI: GenerationI? = null,

    @field:SerializedName("generation-viii")
    val generationViii: GenerationViii? = null,

    @field:SerializedName("generation-vi")
    val generationVi: GenerationVi? = null
) : Parcelable

@Parcelize
data class Home(

    @field:SerializedName("front_shiny_female")
    val frontShinyFemale: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_female")
    val frontFemale: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class Icons(

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("front_female")
    val frontFemale: String? = null
) : Parcelable

@Parcelize
data class Ability(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable

@Parcelize
data class RubySapphire(

    @field:SerializedName("back_default")
    val backDefault: String? = null,

    @field:SerializedName("front_default")
    val frontDefault: String? = null,

    @field:SerializedName("back_shiny")
    val backShiny: String? = null,

    @field:SerializedName("front_shiny")
    val frontShiny: String? = null
) : Parcelable

@Parcelize
data class AbilitiesItem(

    @field:SerializedName("is_hidden")
    val isHidden: Boolean? = null,

    @field:SerializedName("ability")
    val ability: AbilityData? = null,

    @field:SerializedName("slot")
    val slot: Int? = null
) : Parcelable

@Parcelize

data class AbilityData(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null,
) : Parcelable


@Parcelize
data class GenerationVi(

    @field:SerializedName("omegaruby-alphasapphire")
    val omegarubyAlphasapphire: OmegarubyAlphasapphire? = null,

    @field:SerializedName("x-y")
    val xY: XY? = null
) : Parcelable

@Parcelize
data class Type(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable

@Parcelize
data class FormsItem(

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("url")
    val url: String? = null
) : Parcelable
