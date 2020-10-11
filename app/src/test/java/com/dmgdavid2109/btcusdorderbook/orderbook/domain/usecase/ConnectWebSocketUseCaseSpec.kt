package com.dmgdavid2109.btcusdorderbook.orderbook.domain.usecase

import com.dmgdavid2109.btcusdorderbook.common.network.TestSchedulerProvider
import com.dmgdavid2109.btcusdorderbook.helpers.mock
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.repository.BTCUSDRepository
import com.tinder.scarlet.Message
import com.tinder.scarlet.WebSocket
import io.mockk.every
import io.reactivex.Flowable
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class ConnectWebSocketUseCaseSpec : Spek({

    val repository: BTCUSDRepository by mock<BTCUSDRepository>()
    val webSocket = WebSocket.Event.OnMessageReceived(Message.Text("Test Message"))

    val connectWebSocketUseCase: ConnectWebSocketUseCase by memoized {
        ConnectWebSocketUseCase(
            repository,
            TestSchedulerProvider()
        )
    }

    describe("invoke") {
        beforeEachTest {
            every { repository.connectWebSocket() } returns Flowable.just(webSocket)
        }
        it("establish the connexion") {
            val testObserver = connectWebSocketUseCase.invoke().test()
            testObserver.assertValue(webSocket)
        }
    }
})
