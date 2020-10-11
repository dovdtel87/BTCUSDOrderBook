package com.dmgdavid2109.btcusdorderbook.orderbook.data.model

data class OrderBookEntry(
    val price: Double,
    val count: Int,
    val amount: Double
) {

    companion object {
        fun fromList(array: List<Double>): OrderBookEntry {
            return OrderBookEntry(
                    price = array[0],
                    count = array[1].toInt(),
                    amount = array[2]
            )
        }
    }
}
