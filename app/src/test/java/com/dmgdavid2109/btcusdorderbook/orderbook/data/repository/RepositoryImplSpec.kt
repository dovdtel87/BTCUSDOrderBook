package com.dmgdavid2109.btcusdorderbook.orderbook.data.repository

import com.dmgdavid2109.btcusdorderbook.helpers.mock
import com.dmgdavid2109.btcusdorderbook.orderbook.data.api.BitfinexWebSocketApi
import com.dmgdavid2109.btcusdorderbook.orderbook.data.model.OrderBook
import com.dmgdavid2109.btcusdorderbook.orderbook.data.model.Subscribe
import io.mockk.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class RepositoryImplSpec : Spek({

    val api: BitfinexWebSocketApi by mock<BitfinexWebSocketApi>()

    val repository: BTCUSDRepositoryImpl by memoized {
        BTCUSDRepositoryImpl(api)
    }

    val ticker = listOf<Double>(1.0,2.0,3.0,4.0,5.0)
    val orderBook = OrderBook(hashMapOf(), hashMapOf())

    describe("notifyTicker") {
        it("updates ticker processor") {
            val tickerProcessorObserver = repository.tickerProcessor.test()
            repository.notifyTicker(ticker)
            tickerProcessorObserver.assertValue(ticker)
        }
    }

    describe("notifyOrderBook") {
        it("updates order book processor") {
            val orderBookProcessorObserver = repository.orderBookProcessor.test()
            repository.notifyOrderBook(orderBook)
            orderBookProcessorObserver.assertValue(orderBook)
        }
    }

    describe("observeOrderBook") {
        it("return order book") {
            val observableProcessor = repository.observeOrderBook().test()
            repository.notifyOrderBook(orderBook)
            observableProcessor.assertValue(orderBook)
        }
    }

    describe("observeTicker") {
        it("return ticker") {
            val observableProcessor = repository.observeTicker().test()
            repository.notifyTicker(ticker)
            observableProcessor.assertValue(ticker)
        }
    }

    describe("subscribeChannels") {
        it("subscribe to both channels") {
            repository.subscribeChannels()
            verify { api.sendSubscribe(Subscribe()) }
        }
    }
})
