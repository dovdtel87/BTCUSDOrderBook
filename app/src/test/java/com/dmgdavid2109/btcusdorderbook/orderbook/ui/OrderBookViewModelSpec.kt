package com.dmgdavid2109.btcusdorderbook.orderbook.ui

import com.dmgdavid2109.btcusdorderbook.helpers.getValueTest
import com.dmgdavid2109.btcusdorderbook.helpers.mock
import com.dmgdavid2109.btcusdorderbook.helpers.withInstantTaskExecutor
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.model.FormattedOrderBook
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.model.Ticker
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.usecase.*
import com.tinder.scarlet.WebSocket
import io.mockk.every
import io.reactivex.Flowable
import io.reactivex.Observable
import junit.framework.TestCase.assertEquals
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class OrderBookViewModelSpec : Spek({
    withInstantTaskExecutor()

    val getOrderBookUseCase: GetOrderBookUseCase by mock<GetOrderBookUseCase>()
    val getTickerUseCase: GetTickerUseCase by mock<GetTickerUseCase>()
    val connectWebSocketUseCase: ConnectWebSocketUseCase by mock<ConnectWebSocketUseCase>()
    val handleMessagesUseCase: HandleMessagesUseCase by mock<HandleMessagesUseCase>()
    val subscribeChannelsUseCase: SubscribeChannelsUseCase by mock<SubscribeChannelsUseCase>()

    val viewModel: OrderBookViewModel by memoized {
        OrderBookViewModel(
            getOrderBookUseCase,
            getTickerUseCase,
            connectWebSocketUseCase,
            subscribeChannelsUseCase,
            handleMessagesUseCase
        )
    }

    val orderBook = FormattedOrderBook("100","200","300","400")
    val orderBookEmpty = FormattedOrderBook("","","","")
    val ticker = Ticker()

    describe("observeBook"){
        context("when it retrieves an order book") {
            val expectedViewState =
                OrderBookViewState(
                    false,
                    null,
                    ticker,
                    orderBook
                )
            beforeEachTest {
                every { connectWebSocketUseCase.invoke() } returns Flowable.just(WebSocket.Event.OnConnectionOpened(Any()))
                every { getOrderBookUseCase.invoke() } returns Observable.just(orderBook)
            }
            it("updates the view state with the book") {
                viewModel.observeBook()
                assertEquals(expectedViewState, viewModel.viewState.getValueTest())
            }
        }
    }

    describe("observeTicker"){
        context("when it retrieves a ticker") {
            val expectedViewState =
                OrderBookViewState(
                    true,
                    null,
                    ticker,
                    orderBookEmpty
                )
            beforeEachTest {
                every { connectWebSocketUseCase.invoke() } returns Flowable.just(WebSocket.Event.OnConnectionOpened(Any()))
                every { getTickerUseCase.invoke() } returns Observable.just(ticker)
            }
            it("updates the view state with the ticker") {
                viewModel.observeTicker()
                assertEquals(expectedViewState, viewModel.viewState.getValueTest())
            }
        }
    }
})
