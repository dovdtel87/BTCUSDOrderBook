package com.dmgdavid2109.btcusdorderbook.orderbook.ui

import com.dmgdavid2109.btcusdorderbook.common.ui.LceViewState
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.model.FormattedOrderBook
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.model.Ticker

data class OrderBookViewState(
    override val isLoading: Boolean = true,
    override val errorMessage: Int? = null,
    val ticker: Ticker = Ticker(),
    val orderBook: FormattedOrderBook = FormattedOrderBook()
) : LceViewState
