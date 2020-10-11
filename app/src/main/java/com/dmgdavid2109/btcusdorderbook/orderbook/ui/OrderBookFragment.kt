package com.dmgdavid2109.btcusdorderbook.orderbook.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.dmgdavid2109.btcusdorderbook.R
import com.dmgdavid2109.btcusdorderbook.common.ui.setViewModelInputs
import com.dmgdavid2109.btcusdorderbook.common.ui.setViewState
import com.dmgdavid2109.btcusdorderbook.common.ui.viewBinding
import com.dmgdavid2109.btcusdorderbook.databinding.OrderBookFragmentBinding
import com.dmgdavid2109.btcusdorderbook.di.ViewModelFactory
import javax.inject.Inject

class OrderBookFragment @Inject constructor(
    private val viewModelFactory: ViewModelFactory<OrderBookViewModel>
) : Fragment((R.layout.order_book_fragment)) {

    private val binding by viewBinding(OrderBookFragmentBinding::bind)

    private val orderBookViewModel: OrderBookViewModel by viewModels {
        viewModelFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        binding.loadingView.setViewModelInputs(orderBookViewModel)
        orderBookViewModel.observeTicker()
        orderBookViewModel.observeBook()

        orderBookViewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            with(viewState) {
                binding.loadingView.setViewState(viewState)
                with(orderBook) {
                    binding.asksPrice.text = asksPrice
                    binding.bidsPrice.text = bidsPrice
                    binding.asksAmount.text = asksAmount
                    binding.bidsAmount.text = bidsAmount
                }
                with(ticker) {
                    binding.lastPrice.text =  String.format(getString(R.string.price), lastPrice)
                    binding.volume.text = String.format(getString(R.string.volume), volume)
                    binding.high.text = String.format(getString(R.string.high), high)
                    binding.low.text = String.format(getString(R.string.low), low)
                }
            }
        })
    }
}
