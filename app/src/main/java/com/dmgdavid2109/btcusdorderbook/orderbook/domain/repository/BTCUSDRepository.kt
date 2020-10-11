package com.dmgdavid2109.btcusdorderbook.orderbook.domain.repository

import com.dmgdavid2109.btcusdorderbook.orderbook.data.model.OrderBook
import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable
import io.reactivex.Observable

interface BTCUSDRepository {
    fun observeOrderBook(): Observable<OrderBook>
    fun observeTicker(): Observable<List<Double>>
    fun connectWebSocket(): Flowable<WebSocket.Event>
    fun subscribeChannels()
    fun notifyOrderBook(orderBook: OrderBook)
    fun notifyTicker(ticker: List<Double>)
}
