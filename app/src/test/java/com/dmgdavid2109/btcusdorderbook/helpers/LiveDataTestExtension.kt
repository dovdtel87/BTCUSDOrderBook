package com.dmgdavid2109.btcusdorderbook.helpers

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

fun <T> LiveData<T>.getValueTest(): T? {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getValueTest.removeObserver(this)
        }
    }
    this.observeForever(observer)
    latch.await(50, TimeUnit.MILLISECONDS)
    return data
}
