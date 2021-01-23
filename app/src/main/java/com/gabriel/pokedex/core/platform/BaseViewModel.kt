package com.gabriel.pokedex.core.platform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    private var _failure: MutableLiveData<String?> = MutableLiveData()
    var failure: LiveData<String?> = _failure

    protected val _pageLoading = MutableLiveData(false)
    var pageLoading : LiveData<Boolean> = _pageLoading

    protected val _loading = MutableLiveData(false)
    var loading : LiveData<Boolean> = _loading

    protected fun handleFailure(failure: String?) {
        _failure.postValue(failure)
    }
}