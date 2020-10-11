package com.dmgdavid2109.btcusdorderbook.common.ui

import androidx.lifecycle.MutableLiveData

class ViewStateLiveData<T>(initialState: T) : MutableLiveData<T>() {

    init {
        value = initialState
    }

    val currentState: T?
        get() = value

    fun updateCurrentState(newStateCreator: T.() -> T) {
        value = value?.newStateCreator()
    }
}
