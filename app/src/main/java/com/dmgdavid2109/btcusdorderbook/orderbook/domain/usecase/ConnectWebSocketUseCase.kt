package com.dmgdavid2109.btcusdorderbook.orderbook.domain.usecase

import com.dmgdavid2109.btcusdorderbook.common.network.SchedulerProvider

import com.dmgdavid2109.btcusdorderbook.orderbook.domain.repository.BTCUSDRepository
import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable
import javax.inject.Inject

class ConnectWebSocketUseCase @Inject constructor(
    private val repository: BTCUSDRepository,
    private val schedulerProvider: SchedulerProvider
) {
    fun invoke(): Flowable<WebSocket.Event> {
        return repository.connectWebSocket()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }
}
