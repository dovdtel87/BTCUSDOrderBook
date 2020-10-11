package com.dmgdavid2109.btcusdorderbook.common.network

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun ui(): Scheduler
    fun io(): Scheduler
}
