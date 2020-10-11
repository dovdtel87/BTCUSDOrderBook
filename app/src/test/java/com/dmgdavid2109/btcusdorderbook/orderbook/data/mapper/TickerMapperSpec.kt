package com.dmgdavid2109.btcusdorderbook.orderbook.data.mapper

import com.dmgdavid2109.btcusdorderbook.orderbook.domain.model.Ticker
import junit.framework.TestCase.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class TickerMapperSpec : Spek({

    val mapper: TickerMapper by memoized {
        TickerMapper()
    }

    val list = listOf(
        116.32, 5.2234, 232.24, 4.2345, -3.65, 0.0, 345.53, 7623.32458795,321.12, 123.01
    )
    val expectedTicker = Ticker(
        lastPrice = 345.53,
        volume = 7623.32458795,
        high = 321.12,
        low = 123.01
    )

    describe("map") {
        it("then maps to ticker") {
            val result = mapper.map(list)
            assertEquals(expectedTicker, result)
        }
    }
})
