package com.dmgdavid2109.btcusdorderbook.orderbook.domain.usecase

import android.util.Log
import com.dmgdavid2109.btcusdorderbook.orderbook.data.model.OrderBookEntry
import com.dmgdavid2109.btcusdorderbook.orderbook.data.model.OrderBook
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.repository.BTCUSDRepository
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.tinder.scarlet.Message
import javax.inject.Inject

class HandleMessagesUseCase @Inject constructor(
    private val repository: BTCUSDRepository,
    private val gson: Gson
) {

    lateinit var orderBook :OrderBook
    companion object{
        const val TAG = "HandleMessage"
        const val CHANNEL_TICKER = "ticker"
        const val CHANNEL_BOOK = "book"
        const val SUBSCRIBED = "subscribed"
        const val UNSUBSCRIBED = "unsubscribed"
        const val FIELD_EVENT = "event"
        const val CHANNEL = "channel"
        const val CHANNEL_ID = "chanId"
    }

    fun invoke(message: Message.Text) {
        try {
            val json = gson.fromJson<JsonElement>(message.value, JsonElement::class.java)

            if (json.isJsonArray) {
                val array = json.asJsonArray
                val type = array[1]
                if (!type.isAHeartBeating()) {
                    array.handleChannel()
                }
            }

            if (json.isJsonObject) {
                handleEventMessages(json)
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error parsing message")
        }
    }

    val channels = mutableMapOf<String, Int>().apply {
        put(CHANNEL_TICKER, -1)
        put(CHANNEL_BOOK,  -1)
    }

    private fun JsonElement.isAHeartBeating() =
        with(this) {
            isJsonPrimitive && asJsonPrimitive.isString && asString == "hb"
        }

    private fun JsonArray.handleChannel() {
        if (this.size() > 0) {
            val channelId = this[0].asInt
            if (channels[CHANNEL_TICKER] == channelId) {
                this.handleTickerChannel()
            }

            if (channels[CHANNEL_BOOK] == channelId) {
                this.handleOrderBookChannel()
            }
        }
    }

    private fun JsonArray.handleOrderBookChannel() {
        when(this[1].isJsonArray) {
            true ->  createOrderBook(this)
            false -> updateOrderBook(this)
        }
    }

    private fun createOrderBook(array: JsonArray) {
        val orderBookLevels = mutableListOf<OrderBookEntry>()

        array[1].asJsonArray
            .map { it.asJsonArray }
            .forEach { jsonArray ->
                orderBookLevels.add(
                    OrderBookEntry.fromList(
                        jsonArray.map { it.asDouble }
                    )
                )
        }

        orderBook = OrderBook.createFrom(orderBookLevels)
        repository.notifyOrderBook( OrderBook.createFrom(orderBookLevels))
    }

    private fun updateOrderBook(array: JsonArray) {
        val list = array
            .map { it.asDouble }
            .toMutableList()
            .apply {
                removeAt(0)
            }.toList()

        val entry = OrderBookEntry.fromList(list)

        orderBook = orderBook.updateEntry(entry)
        repository.notifyOrderBook(orderBook)
    }

    private fun handleEventMessages(json: JsonElement) {
        val response = json.asJsonObject
        val event = response.getAsJsonPrimitive(FIELD_EVENT).asString

        event?.let {
            when (event) {
                SUBSCRIBED -> handleSubscribedEvent(response)
                UNSUBSCRIBED -> handleUnSubscribedEvent(response)
            }
        }
    }

    private fun handleSubscribedEvent(response: JsonObject) {
        val channel = response.getAsJsonPrimitive(CHANNEL).asString
        val chanId = response.getAsJsonPrimitive(CHANNEL_ID).asInt
        if (channels.containsKey(channel)) {
            channels[channel] = chanId
        }
    }

    private fun handleUnSubscribedEvent(response: JsonObject) {
        val channel = response.getAsJsonPrimitive(CHANNEL).asString
        val chanId = response.getAsJsonPrimitive(CHANNEL_ID).asInt
        if (channels.containsValue(chanId)) {
            channels[channel] = -1
        }
    }

    private fun JsonArray.handleTickerChannel() {
        val list = this.map { it.asDouble }.toMutableList()
            .apply { removeAt(0) }.toList()
        repository.notifyTicker(list)
    }
}
