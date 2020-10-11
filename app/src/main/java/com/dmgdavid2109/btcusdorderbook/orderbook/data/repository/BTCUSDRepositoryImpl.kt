package com.dmgdavid2109.btcusdorderbook.orderbook.data.repository

import com.dmgdavid2109.btcusdorderbook.orderbook.data.api.BitfinexWebSocketApi
import com.dmgdavid2109.btcusdorderbook.orderbook.data.model.OrderBook
import com.dmgdavid2109.btcusdorderbook.orderbook.data.model.Subscribe
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.repository.BTCUSDRepository
import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class BTCUSDRepositoryImpl @Inject constructor(
    private val bitfinexWebSocketApi: BitfinexWebSocketApi
) : BTCUSDRepository {

    companion object {
        const val CHANNEL_TICKER = "ticker"
    }

    val orderBookProcessor = PublishSubject.create<OrderBook>()
    val tickerProcessor =  PublishSubject.create<List<Double>>()

    override fun connectWebSocket(): Flowable<WebSocket.Event> {
        return bitfinexWebSocketApi.observeWebSocketEvent()
    }

    override fun subscribeChannels() {
        bitfinexWebSocketApi.sendSubscribe(
            Subscribe(
                channel = CHANNEL_TICKER
            )
        )
        bitfinexWebSocketApi.sendSubscribe(Subscribe())
    }

    override fun notifyOrderBook(orderBook: OrderBook) {
        orderBookProcessor.onNext(orderBook)
    }

    override fun notifyTicker(ticker: List<Double>) {
        tickerProcessor.onNext(ticker)
    }

    override fun observeTicker(): Observable<List<Double>> {
        return tickerProcessor
    }

    override fun observeOrderBook(): Observable<OrderBook> {
        return orderBookProcessor
    }
}
