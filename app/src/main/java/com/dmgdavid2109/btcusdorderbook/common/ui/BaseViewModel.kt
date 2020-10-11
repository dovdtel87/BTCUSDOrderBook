package com.dmgdavid2109.btcusdorderbook.common.ui

import androidx.lifecycle.ViewModel
import com.dmgdavid2109.btcusdorderbook.orderbook.ui.OrderBookViewModel
import com.uber.autodispose.lifecycle.CorrespondingEventsFunction
import com.uber.autodispose.lifecycle.LifecycleEndedException
import com.uber.autodispose.lifecycle.LifecycleScopeProvider
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject

open class BaseViewModel : ViewModel(),
    Consumer<Throwable>,
    LifecycleScopeProvider<BaseViewModel.ViewModelEvent> {

    override fun accept(t: Throwable?) {
    }

    enum class ViewModelEvent {
        CREATED, CLEARED
    }

    override fun lifecycle(): Observable<ViewModelEvent> {
        return lifecycleEvents.hide()
    }
    private val lifecycleEvents = BehaviorSubject.createDefault(ViewModelEvent.CREATED)

    override fun onCleared() {
        lifecycleEvents.onNext(ViewModelEvent.CLEARED)
        super.onCleared()
    }

    override fun correspondingEvents(): CorrespondingEventsFunction<ViewModelEvent> {
        return correspondingEvents
    }

    override fun peekLifecycle(): ViewModelEvent? {
        return lifecycleEvents.value
    }

    companion object {

        private val correspondingEvents = CorrespondingEventsFunction<ViewModelEvent> { event ->
            when (event) {
                ViewModelEvent.CREATED -> ViewModelEvent.CLEARED
                else -> throw LifecycleEndedException("Cannot bind to ViewModel lifecycle after onCleared.")
            }
        }
    }
}
