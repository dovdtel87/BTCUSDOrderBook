package com.dmgdavid2109.btcusdorderbook.orderbook.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Subscribe(
    @Json(name = "event") val event: String = "subscribe",
    @Json(name = "channel") val channel: String = "book",
    @Json(name = "pair") val pair: String = "BTCUSD",
    @Json(name = "prec") val prec: String = "P0"
)
