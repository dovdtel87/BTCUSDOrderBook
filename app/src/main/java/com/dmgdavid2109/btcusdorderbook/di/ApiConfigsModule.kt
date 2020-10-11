package com.dmgdavid2109.btcusdorderbook.di

import com.dmgdavid2109.btcusdorderbook.BuildConfig
import com.dmgdavid2109.btcusdorderbook.common.network.AppSchedulerProvider
import com.dmgdavid2109.btcusdorderbook.common.network.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApiConfigsModule {

    @Provides
    @ApiConfig
    fun providesApiUrl(): String {
        return BuildConfig.API_URL
    }

    @Provides
    @Singleton
    fun providesSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}
