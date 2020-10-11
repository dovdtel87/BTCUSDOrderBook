package com.dmgdavid2109.btcusdorderbook.orderbook.domain.usecase

import com.dmgdavid2109.btcusdorderbook.common.network.TestSchedulerProvider
import com.dmgdavid2109.btcusdorderbook.helpers.mock
import com.dmgdavid2109.btcusdorderbook.orderbook.data.mapper.TickerMapper
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.model.Ticker
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.repository.BTCUSDRepository
import io.mockk.every
import io.reactivex.Observable
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class GetTickerUseCaseSpec : Spek({

    val repository: BTCUSDRepository by mock<BTCUSDRepository>()
    val tickerMapper: TickerMapper by mock<TickerMapper>()

    val ticker = Ticker(
        lastPrice = 345.53,
        volume = 7623.32458795,
        high = 321.12,
        low = 123.01
    )

    val getTickerUseCase: GetTickerUseCase by memoized {
        GetTickerUseCase(
            repository,
            tickerMapper,
            TestSchedulerProvider()
        )
    }

    describe("invoke") {
        beforeEachTest {
            every { repository.observeTicker() } returns Observable.just(listOf())
            every { tickerMapper.map(any()) } returns ticker
        }
        it("returns an ticker") {
            val testObserver = getTickerUseCase.invoke().test()
            testObserver.assertValue(ticker)
        }
    }
})
