package com.dmgdavid2109.btcusdorderbook.utils

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import androidx.navigation.*
import io.mockk.every
import io.mockk.mockk

fun createNavControllerMock(currentDestinationId: Int = 1) : NavController = mockk(relaxed = true) {
    every { currentDestination } returns NavDestination("").apply { id = currentDestinationId }
    every { graph } returns NavGraph(MockNavigator()).apply {
        startDestination = 1
        addDestination(NavDestination("").apply { id = 1 })
    }
}

@Navigator.Name("Name")
private class MockNavigator : Navigator<NavGraph>() {
    override fun navigate(destination: NavGraph, args: Bundle?, navOptions: NavOptions?, navigatorExtras: Extras?): NavDestination? {
        return mockk(relaxed = true)
    }

    override fun createDestination(): NavGraph {
        return mockk(relaxed = true)
    }

    override fun popBackStack(): Boolean {
        return false
    }
}

fun <T : Fragment> createFactoryWithNavController (
    newFragment: () -> T
): FragmentFactory {
    return object : FragmentFactory() {
        override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
            return newFragment()
        }
    }
}
