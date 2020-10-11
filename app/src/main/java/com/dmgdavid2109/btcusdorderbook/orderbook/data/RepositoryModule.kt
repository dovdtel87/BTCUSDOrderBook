package com.dmgdavid2109.btcusdorderbook.orderbook.data

import com.dmgdavid2109.btcusdorderbook.di.ApiConfig
import com.dmgdavid2109.btcusdorderbook.orderbook.data.api.BitfinexWebSocketApi
import com.dmgdavid2109.btcusdorderbook.orderbook.domain.repository.BTCUSDRepository
import com.dmgdavid2109.btcusdorderbook.orderbook.data.repository.BTCUSDRepositoryImpl
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.messageadapter.gson.GsonMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import okhttp3.OkHttpClient

@Module
@InstallIn(ActivityComponent::class)
abstract class RepositoryModule {

    @Binds
    @ActivityScoped
    internal abstract fun BTCUSDRepository(repository: BTCUSDRepositoryImpl): BTCUSDRepository

    companion object {
        @Provides
        @ActivityScoped
        fun provideBitfinexService(client: OkHttpClient, lifecycle: Lifecycle, @ApiConfig url: String): BitfinexWebSocketApi {
            val scarlet = Scarlet.Builder()
                .webSocketFactory(client.newWebSocketFactory(url))
                .lifecycle(lifecycle)
                .addMessageAdapterFactory(GsonMessageAdapter.Factory())
                .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
                .build()
            return scarlet.create(BitfinexWebSocketApi::class.java)
        }
    }
}
