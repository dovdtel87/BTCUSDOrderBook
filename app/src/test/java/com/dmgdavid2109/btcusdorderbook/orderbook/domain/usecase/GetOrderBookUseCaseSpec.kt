package com.dmgdavid2109.btcusdorderbook.orderbook.domain.usecase

import com.dmgdavid2109.btcusdorderbook.common.network.TestSchedulerProvider
import com.dmgdavid2109.btcusdorderbook.helpers.mock
import com.dmgdavid2109.btcusdorderbook.orderbook.data.mapper.OrderBookMapper
import com.dmgdavid2109.btcusdorderbook.orderbook.data.model.OrderBookEntry
import com.dmgdavid2109.btcusdorderbook.orderbook.data.model.OrderBook
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.model.FormattedOrderBook
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.repository.BTCUSDRepository
import io.mockk.every
import io.reactivex.Observable
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class GetOrderBookUseCaseSpec : Spek({

    val repository: BTCUSDRepository by mock<BTCUSDRepository>()
    val orderBookMapper: OrderBookMapper by mock<OrderBookMapper>()

    val bids = hashMapOf<Double, OrderBookEntry>().apply {
        put(1234.0, OrderBookEntry(1234.0, 200, 0.6))
        put(2234.0, OrderBookEntry(2234.0, 200, 0.5))
    }

    val asks = hashMapOf<Double, OrderBookEntry>().apply {
        put(5532.0, OrderBookEntry(5532.0, 100, -0.3))
        put(5632.0, OrderBookEntry(45632.0, 100, -0.2))
    }

    val orderBookTransactionsRegister = OrderBook(bids = bids, asks = asks)
    val orderBook = FormattedOrderBook(
        bidsAmount = "0.50\n0.60\n",
        bidsPrice = "2234.00\n1234.00\n",
        asksAmount = "0.20\n0.30\n",
        asksPrice = "5632.00\n5532.00\n")

    val getOrderBookUseCase: GetOrderBookUseCase by memoized {
        GetOrderBookUseCase(
            repository,
            orderBookMapper,
            TestSchedulerProvider()
        )
    }

    describe("invoke") {
        beforeEachTest {
            every { repository.observeOrderBook() } returns Observable.just(orderBookTransactionsRegister)
            every { orderBookMapper.map(any()) } returns orderBook
        }
        it("returns an order book") {
            val testObserver = getOrderBookUseCase.invoke().test()
            testObserver.assertValue(orderBook)
        }
    }
})
