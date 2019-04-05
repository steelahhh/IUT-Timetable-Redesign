package com.alefimenko.iuttimetable.core.base

import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.view.View
import com.alefimenko.iuttimetable.util.createBinder
import com.bluelinelabs.conductor.archlifecycle.LifecycleController
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer
import io.reactivex.subjects.PublishSubject

/*
 * Created by Alexander Efimenko on 2019-02-04.
 */

abstract class BaseController<Event, ViewModel>(
    private val events: PublishSubject<Event> = PublishSubject.create()
) : LifecycleController(),
    Consumer<ViewModel>,
    ObservableSource<Event> by events,
    ComponentCallbacks {

    val bind = createBinder()

    fun dispatch(event: Event) {
        events.onNext(event)
    }

    fun requireView() = view ?: error("")

    fun requireContext() = requireView().context

    final override fun accept(viewmodel: ViewModel) {
        if (isAttached) {
            acceptViewmodel(viewmodel)
        }
    }

    abstract fun acceptViewmodel(viewmodel: ViewModel)

    override fun onAttach(view: View) {
        (activity as BaseActivity).updateNavigationColor()
        super.onAttach(view)
    }

    override fun onDestroyView(view: View) {
        bind.resetViews()
        super.onDestroyView(view)
    }

    override fun onLowMemory() = Unit

    override fun onConfigurationChanged(newConfig: Configuration?) = Unit
}