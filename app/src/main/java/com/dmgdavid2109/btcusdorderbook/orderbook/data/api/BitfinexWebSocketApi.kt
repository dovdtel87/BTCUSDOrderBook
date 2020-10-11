package com.dmgdavid2109.btcusdorderbook.orderbook.data.api

import com.dmgdavid2109.btcusdorderbook.orderbook.data.model.Subscribe
import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable

interface BitfinexWebSocketApi {
    @Receive
    fun observeWebSocketEvent(): Flowable<WebSocket.Event>
    @Send
    fun sendSubscribe(subscribe: Subscribe)
}
