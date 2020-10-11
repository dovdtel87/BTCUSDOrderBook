package com.dmgdavid2109.btcusdorderbook.orderbook.domain.model

data class FormattedOrderBook(
    val bidsAmount: String = "",
    val bidsPrice: String = "",
    val asksAmount: String = "",
    val asksPrice: String = ""
)
