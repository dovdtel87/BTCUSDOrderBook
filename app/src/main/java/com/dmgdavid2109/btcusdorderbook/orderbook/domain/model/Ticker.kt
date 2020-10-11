package com.dmgdavid2109.btcusdorderbook.orderbook.domain.model

data class Ticker(
    val lastPrice: Double = 0.0,
    val low: Double = 0.0,
    val high: Double = 0.0,
    val volume: Double = 0.0
)
