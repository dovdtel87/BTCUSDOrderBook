package com.dmgdavid2109.btcusdorderbook.di

import androidx.fragment.app.Fragment
//import androidx.navigation.fragment.NavHostFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ApplicationComponent::class)
object NavHostFragmentBindingModule {

   /* @Provides
    @IntoMap
    @FragmentKey(NavHostFragment::class)
    fun provideNavHostFragment(): Fragment {
        return NavHostFragment()
    }*/
}
