package com.dmgdavid2109.btcusdorderbook.orderbook.ui

import androidx.lifecycle.LiveData
import com.dmgdavid2109.btcusdorderbook.R
import com.dmgdavid2109.btcusdorderbook.common.ui.BaseViewModel
import com.dmgdavid2109.btcusdorderbook.common.ui.LceViewModelInputs
import com.dmgdavid2109.btcusdorderbook.common.ui.ViewStateLiveData
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.usecase.*
import com.tinder.scarlet.Message
import com.tinder.scarlet.WebSocket
import com.uber.autodispose.autoDispose
import javax.inject.Inject

class OrderBookViewModel @Inject constructor(
    private val getOrderBookUseCase: GetOrderBookUseCase,
    private val getTickerUseCase: GetTickerUseCase,
    private val connectWebSocketUseCase: ConnectWebSocketUseCase,
    private val subscribeChannelsUseCase: SubscribeChannelsUseCase,
    private val handleMessagesUseCase: HandleMessagesUseCase
) : BaseViewModel(), LceViewModelInputs {

    private val _viewState = ViewStateLiveData(OrderBookViewState())
    val viewState: LiveData<OrderBookViewState>
        get() = _viewState

    init {
        connectWebSocket()
    }

    private fun connectWebSocket() {
        connectWebSocketUseCase
            .invoke()
            .doOnSubscribe{
                _viewState.value = _viewState.value?.copy(isLoading = true)
            }
            .autoDispose(this)
            .subscribe { event->
                if (event is WebSocket.Event.OnConnectionOpened<*>) {
                    subscribeChannelsUseCase.invoke()
                }

                if (event is WebSocket.Event.OnMessageReceived && event.message is Message.Text) {
                    handleMessagesUseCase.invoke(event.message as Message.Text)
                }
            }
    }

    fun observeBook() {
        getOrderBookUseCase
            .invoke()
            .autoDispose(this)
            .subscribe { orderBook ->
                _viewState.value = _viewState.value?.copy(isLoading = false, orderBook = orderBook)
            }
    }

    fun observeTicker() {
        getTickerUseCase
            .invoke()
            .autoDispose(this)
            .subscribe { ticker ->
                _viewState.value = _viewState.value?.copy(ticker = ticker)
            }
    }

    override fun retry() {
        connectWebSocket()
        observeBook()
        observeTicker()
    }

    override fun accept(t: Throwable?) {
        super.accept(t)
        _viewState.updateCurrentState {
            copy(
                isLoading = false,
                errorMessage = R.string.generic_error
            )
        }
    }
}
