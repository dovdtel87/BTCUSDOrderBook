package com.dmgdavid2109.btcusdorderbook.orderbook.data.model

data class OrderBook(
    val asks: HashMap<Double, OrderBookEntry> = hashMapOf(),
    val bids: HashMap<Double, OrderBookEntry> = hashMapOf()) {

    companion object {
        fun createFrom(orderBooksEntries: List<OrderBookEntry>): OrderBook {
            val asks = hashMapOf<Double, OrderBookEntry>()
            val bids = hashMapOf<Double, OrderBookEntry>()

            orderBooksEntries.filter { it.count > 0 }.forEach {
                when {
                    it.amount > 0 -> bids[it.price] = it
                    else -> asks[it.price] = it
                }
            }

            return OrderBook(asks, bids)
        }
    }

    fun updateEntry(entry: OrderBookEntry): OrderBook {
        return if (entry.amount < 0) {
            OrderBook(asks = updateMap(entry, asks), bids = bids)
        } else {
            OrderBook(asks = asks, bids = updateMap(entry, bids))
        }
    }

    private fun updateMap(entry: OrderBookEntry, side: HashMap<Double, OrderBookEntry>): HashMap<Double, OrderBookEntry> {
        val sideMap = side.toMutableMap()
        val shouldDelete = entry.count == 0

        sideMap.remove(entry.price)
        if (!shouldDelete) {
            sideMap[entry.price] = entry.copy()
        }

        return sideMap as HashMap<Double, OrderBookEntry>
    }
}
