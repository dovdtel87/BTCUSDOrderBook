package com.dmgdavid2109.btcusdorderbook.orderbook.ui

import android.os.Bundle
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dmgdavid2109.btcusdorderbook.R
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.model.FormattedOrderBook
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.model.Ticker
import com.dmgdavid2109.btcusdorderbook.utils.createFactoryWithNavController
import com.dmgdavid2109.btcusdorderbook.utils.toFactory
import com.dmgdavid2109.btcusdorderbook.utils.toLiveData
import io.mockk.every
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OrderBookFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var fragmentScenario: FragmentScenario<OrderBookFragment>

    @Test
    fun itDisplaysOrderBookAndTicker() {

        //Given
        val expectedOrderBook = FormattedOrderBook(
            bidsAmount = "0.50000\n0.60000\n",
            bidsPrice = "\$2234.0\n\$1234.0\n",
            asksAmount = "0.20000\n0.30000\n",
            asksPrice = "\$5632.0\n\$5532.0\n"
        )
        val orderBookViewState = OrderBookViewState(
            isLoading = false,
            errorMessage = null,
            ticker = Ticker(256.00, 211.0, 270.0, 110000.0),
            orderBook = expectedOrderBook
        )
        val viewModel = mockk<OrderBookViewModel>(relaxed = true) {
            every { viewState } returns orderBookViewState.toLiveData()
        }

        // When
        startFragment(viewModel)

        // Then
        onView(withId(R.id.ticker)).check(ViewAssertions.matches(ViewMatchers.withText("Ticker")))
        onView(withId(R.id.lastPrice)).check(ViewAssertions.matches(ViewMatchers.withText("$ 256.00")))
        onView(withId(R.id.volume)).check(ViewAssertions.matches(ViewMatchers.withText("Vol: 110000.00000")))
        onView(withId(R.id.low)).check(ViewAssertions.matches(ViewMatchers.withText("Low: $211.00")))
        onView(withId(R.id.high)).check(ViewAssertions.matches(ViewMatchers.withText("High: $270.00")))

        onView(withId(R.id.bidsAmount)).check(ViewAssertions.matches(ViewMatchers.withText("0.50000\n0.60000\n")))
        onView(withId(R.id.bidsPrice)).check(ViewAssertions.matches(ViewMatchers.withText("\$2234.0\n\$1234.0\n")))
        onView(withId(R.id.asksAmount)).check(ViewAssertions.matches(ViewMatchers.withText("0.20000\n0.30000\n")))
        onView(withId(R.id.asksPrice)).check(ViewAssertions.matches(ViewMatchers.withText("\$5632.0\n\$5532.0\n")))
    }

    @Test
    fun itDisplaysLoadingView() {
        // Given
        val orderBookViewState = OrderBookViewState(
            isLoading = true,
            errorMessage = null
        )
        val viewModel = mockk<OrderBookViewModel>(relaxed = true) {
            every { viewState } returns orderBookViewState.toLiveData()
        }

        // When
        startFragment(viewModel)

        // Then
        onView(withId(R.id.progress_bar)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun itDisplaysErrorView() {
        // Given
        val orderBookViewState = OrderBookViewState(
            isLoading = false,
            errorMessage = null
        )
        val viewModel = mockk<OrderBookViewModel>(relaxed = true) {
            every { viewState } returns orderBookViewState.toLiveData()
        }

        // When
        startFragment(viewModel)

        // Then
        onView(withId(R.id.error_description)).check(ViewAssertions.matches(ViewMatchers.withText(R.string.generic_error)))
    }

    private fun startFragment(viewModel: OrderBookViewModel) {
        fragmentScenario = launchFragmentInContainer(
            Bundle(),
            themeResId = R.style.AppTheme,
            factory = createFactoryWithNavController {
                OrderBookFragment(viewModel.toFactory())
            }
        )
    }
}
