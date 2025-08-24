package com.surelabsid.pokeinfo.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.surelabsid.core.data.common.Resources
import com.surelabsid.core.data.source.local.entity.PokemonEntity
import com.surelabsid.core.data.source.network.model.PokeDetail
import com.surelabsid.core.data.source.network.model.PokemonItem
import com.surelabsid.core.domain.usecase.PokeUseCase
import com.surelabsid.core.utils.base.BaseViewModel
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(private val useCase: PokeUseCase) : BaseViewModel() {
    private var _pokeList = MutableLiveData<MutableList<PokemonEntity?>>()
    val pokeList get() = _pokeList
    private var _pokeDetail = MutableLiveData<PokeDetail?>()
    val pokeDetail get() = _pokeDetail

    fun getPokeList(offset: Int, limit: Int) {
        viewModelScope.launch {
            useCase.getPokeList(offset, limit).collect { data ->
                when (data) {
                    is Resources.Loading -> {}
                    is Resources.Success -> {
                        _pokeList.postValue(data.data?.toMutableList())
                    }

                    is Resources.Error -> {
                        _error.postValue(data.message)
                    }
                }
            }
        }
    }

    fun getPokeDetail(id: String) {
        viewModelScope.launch {
            useCase.getPokeDetail(id).collect { data ->
                when (data) {
                    is Resources.Loading -> {}
                    is Resources.Success -> {
                        _pokeDetail.postValue(data.data)
                    }

                    is Resources.Error -> {
                        Timber.e("error ${data.message}")
                        _error.postValue(data.message)
                    }
                }
            }
        }
    }

}