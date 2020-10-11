package com.dmgdavid2109.btcusdorderbook.orderbook.domain.usecase

import com.dmgdavid2109.btcusdorderbook.orderbook.domain.repository.BTCUSDRepository
import javax.inject.Inject

class SubscribeChannelsUseCase @Inject constructor(
    private val repository: BTCUSDRepository
) {
    fun invoke() {
        repository.subscribeChannels()
    }
}
