package com.surelabsid.pokeinfo.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.surelabsid.core.data.common.Resources
import com.surelabsid.core.data.source.local.entity.PokemonEntity
import com.surelabsid.core.domain.usecase.PokeUseCase
import com.surelabsid.core.utils.base.BaseViewModel
import kotlinx.coroutines.launch

class FavoriteViewModel(private val pokeUseCase: PokeUseCase) : BaseViewModel() {
    private var _pokemonFav = MutableLiveData<List<PokemonEntity>>()
    val pokemonFav get() = _pokemonFav

    fun getPokemonFavorite() {
        viewModelScope.launch {
            pokeUseCase.getPokemonFavorite().collect {
                when (it) {
                    is Resources.Loading -> {
                        _loading.postValue(true)
                    }

                    is Resources.Success -> {
                        _loading.postValue(false)
                        _pokemonFav.postValue(it.data ?: emptyList())
                    }

                    is Resources.Error -> {
                        _loading.postValue(false)
                        _error.postValue(it.message)
                    }
                }
            }
        }
    }
}