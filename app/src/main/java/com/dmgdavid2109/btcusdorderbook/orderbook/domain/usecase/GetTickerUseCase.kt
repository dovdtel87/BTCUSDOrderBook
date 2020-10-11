package com.dmgdavid2109.btcusdorderbook.orderbook.domain.usecase

import com.dmgdavid2109.btcusdorderbook.common.network.SchedulerProvider
import com.dmgdavid2109.btcusdorderbook.orderbook.data.mapper.TickerMapper
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.model.Ticker
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.repository.BTCUSDRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetTickerUseCase @Inject constructor(
    private val repository: BTCUSDRepository,
    private val tickerMapper: TickerMapper,
    private val schedulerProvider: SchedulerProvider
) {
    fun invoke(): Observable<Ticker> {
        return repository.observeTicker()
            .map(tickerMapper::map)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }
}
