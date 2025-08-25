package com.surelabsid.core.utils.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel: ViewModel() {
    protected val _error = MutableLiveData<String>()
    val error get() = _error
    protected val _loading = MutableLiveData<Boolean>()
    val loading get() = _loading

}