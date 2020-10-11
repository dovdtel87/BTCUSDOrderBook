package com.dmgdavid2109.btcusdorderbook.orderbook.data.mapper

import com.dmgdavid2109.btcusdorderbook.common.data.Mapper
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.model.Ticker
import javax.inject.Inject

class TickerMapper @Inject constructor() : Mapper<List<Double>, Ticker> {

    override fun map(input: List<Double>): Ticker {
        return Ticker(
            lastPrice = input[6],
            volume = input[7],
            high = input[8],
            low = input[9]
        )
    }
}
