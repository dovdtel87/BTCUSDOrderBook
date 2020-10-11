package com.dmgdavid2109.btcusdorderbook.orderbook.domain.usecase

import com.dmgdavid2109.btcusdorderbook.helpers.mock
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.repository.BTCUSDRepository
import io.mockk.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class SubscribeChannelsUseCaseSpec : Spek({

    val repository: BTCUSDRepository by mock<BTCUSDRepository>()

    val subscribeChannelsUseCase: SubscribeChannelsUseCase by memoized {
        SubscribeChannelsUseCase(
            repository
        )
    }

    describe("invoke") {
        it("subscribe to the channels") {
            subscribeChannelsUseCase.invoke()
            verify { repository.subscribeChannels() }
        }
    }
})
