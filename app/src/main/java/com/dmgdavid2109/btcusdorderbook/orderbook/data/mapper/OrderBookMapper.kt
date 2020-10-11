package com.dmgdavid2109.btcusdorderbook.orderbook.data.mapper

import com.dmgdavid2109.btcusdorderbook.common.data.Mapper
import com.dmgdavid2109.btcusdorderbook.orderbook.data.model.OrderBookEntry
import com.dmgdavid2109.btcusdorderbook.orderbook.data.model.OrderBook
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.model.FormattedOrderBook
import javax.inject.Inject
import kotlin.math.absoluteValue

class OrderBookMapper @Inject constructor() : Mapper<OrderBook, FormattedOrderBook> {

    companion object {
        const val FORMAT = "%.5f\n"
        const val FORMAT_DOLLAR = "$%.1f\n"
    }

    override fun map(input: OrderBook): FormattedOrderBook {
        val bids = fromOrderBookMapToString(input.bids)
        val asks = fromOrderBookMapToString(input.asks)

        return FormattedOrderBook(
            bidsAmount = bids.amount,
            bidsPrice = bids.price,
            asksAmount = asks.amount,
            asksPrice = asks.price
        )
    }

    data class OrderBookString(val amount: String, val price: String)

    private fun fromOrderBookMapToString(map: HashMap<Double, OrderBookEntry>): OrderBookString {
        val list = map.toSortedMap().values.toList().reversed()
        val amount = StringBuilder()
        val price = StringBuilder()

        list.forEach {
            amount.append(String.format(FORMAT, it.amount.absoluteValue))
            price.append(String.format(FORMAT_DOLLAR, it.price))
        }

        return OrderBookString(amount = amount.toString(), price = price.toString())
    }
}
