package com.dmgdavid2109.btcusdorderbook.orderbook.domain.usecase

import com.dmgdavid2109.btcusdorderbook.common.network.SchedulerProvider
import com.dmgdavid2109.btcusdorderbook.orderbook.data.mapper.OrderBookMapper
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.model.FormattedOrderBook

import com.dmgdavid2109.btcusdorderbook.orderbook.domain.repository.BTCUSDRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetOrderBookUseCase @Inject constructor(
    private val repository: BTCUSDRepository,
    private val orderBookMapper: OrderBookMapper,
    private val schedulerProvider: SchedulerProvider
) {
    fun invoke(): Observable<FormattedOrderBook> {
        return repository.observeOrderBook()
            .map(orderBookMapper::map)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }
}
