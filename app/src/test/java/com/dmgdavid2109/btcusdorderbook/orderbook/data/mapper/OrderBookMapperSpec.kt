package com.dmgdavid2109.btcusdorderbook.orderbook.data.mapper

import com.dmgdavid2109.btcusdorderbook.orderbook.data.model.OrderBookEntry
import com.dmgdavid2109.btcusdorderbook.orderbook.data.model.OrderBook
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.model.FormattedOrderBook
import junit.framework.TestCase.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class OrderBookMapperSpec : Spek({

    val mapper: OrderBookMapper by memoized {
        OrderBookMapper()
    }

    val bids = hashMapOf<Double, OrderBookEntry>().apply {
        put(1234.0, OrderBookEntry(1234.0, 200, 0.6))
        put(2234.0, OrderBookEntry(2234.0, 200, 0.5))
    }

    val asks = hashMapOf<Double, OrderBookEntry>().apply {
        put(5532.0, OrderBookEntry(5532.0, 100, -0.3))
        put(5632.0, OrderBookEntry(5632.0, 100, -0.2))
    }

    val orderBookTransactionsRegister = OrderBook(bids = bids, asks = asks)
    val expectedOrderBook = FormattedOrderBook(
        bidsAmount = "0.50000\n0.60000\n",
        bidsPrice = "\$2234.0\n\$1234.0\n",
        asksAmount = "0.20000\n0.30000\n",
        asksPrice = "\$5632.0\n\$5532.0\n")

    describe("map") {
        it("then maps to order book") {
            val result = mapper.map(orderBookTransactionsRegister)
            assertEquals(expectedOrderBook, result)
        }
    }
})
