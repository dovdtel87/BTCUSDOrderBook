package com.dmgdavid2109.btcusdorderbook.common.ui

import com.dmgdavid2109.btcusdorderbook.common.ui.customviews.LoadingErrorView

fun LoadingErrorView.setViewModelInputs(viewModelInputs: LceViewModelInputs) {
    setRetryListener(viewModelInputs::retry)
}

fun LoadingErrorView.setViewState(viewState: LceViewState) {
    when {
        viewState.isLoading -> status(LoadingErrorView.VisibilityState.LOADING)
        viewState.errorMessage != null -> status(LoadingErrorView.VisibilityState.OFFLINE)
        else -> status(LoadingErrorView.VisibilityState.GONE)
    }
}
