package com.dmgdavid2109.btcusdorderbook.orderbook.ui

import androidx.fragment.app.Fragment
import com.dmgdavid2109.btcusdorderbook.di.FragmentKey
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityComponent::class)
abstract class OrderBookFragmentBindingModule {

    @Binds
    @IntoMap
    @FragmentKey(OrderBookFragment::class)
    abstract fun bindOrderBookFragment(mainFragment: OrderBookFragment): Fragment
}
