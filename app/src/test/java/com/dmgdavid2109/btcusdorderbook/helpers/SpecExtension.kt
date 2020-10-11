package com.dmgdavid2109.btcusdorderbook.helpers

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import io.mockk.mockk
import org.spekframework.spek2.dsl.LifecycleAware
import org.spekframework.spek2.lifecycle.CachingMode
import org.spekframework.spek2.lifecycle.MemoizedValue

inline fun <reified T: Any> LifecycleAware.mock(
    mode: CachingMode = CachingMode.TEST,
    noinline  block: T.() -> Unit = {}
): MemoizedValue<T> {
    return memoized(mode) {
        mockk<T>(relaxed = true, block = block)
    }
}

fun LifecycleAware.withInstantTaskExecutor() {
    beforeEachTest {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }
        })
    }

    afterEachTest { ArchTaskExecutor.getInstance().setDelegate(null) }
}
