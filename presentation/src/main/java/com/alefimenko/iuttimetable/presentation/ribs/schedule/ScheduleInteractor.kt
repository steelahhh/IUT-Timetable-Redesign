package com.alefimenko.iuttimetable.presentation.ribs.schedule

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import com.alefimenko.iuttimetable.presentation.ribs.groups.Groups
import com.alefimenko.iuttimetable.presentation.ribs.schedule.ScheduleRouter.Configuration.Content
import com.alefimenko.iuttimetable.presentation.ribs.schedule.ScheduleRouter.Configuration.Overlay
import com.alefimenko.iuttimetable.presentation.ribs.schedule.feature.ScheduleFeature
import com.alefimenko.iuttimetable.presentation.ribs.schedule.feature.ScheduleFeature.News
import com.alefimenko.iuttimetable.presentation.ribs.schedule.mapper.InputToWish
import com.alefimenko.iuttimetable.presentation.ribs.schedule.mapper.NewsToOutput
import com.alefimenko.iuttimetable.presentation.ribs.schedule.mapper.StateToViewModel
import com.alefimenko.iuttimetable.presentation.ribs.schedule.mapper.ViewEventToWish
import com.alefimenko.iuttimetable.presentation.ribs.settings.Settings
import com.badoo.mvicore.android.AndroidTimeCapsule
import com.badoo.mvicore.android.lifecycle.createDestroy
import com.badoo.mvicore.android.lifecycle.startStop
import com.badoo.mvicore.binder.named
import com.badoo.mvicore.binder.using
import com.badoo.ribs.core.Interactor
import io.reactivex.Observable.wrap
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer

internal class ScheduleInteractor(
    savedInstanceState: Bundle?,
    private val timeCapsule: AndroidTimeCapsule,
    private val router: ScheduleRouter,
    private val input: ObservableSource<Schedule.Input>,
    private val output: Consumer<Schedule.Output>,
    private val feature: ScheduleFeature
) : Interactor<ScheduleView>(
    savedInstanceState = savedInstanceState,
    disposables = feature
) {
    val groupsOutputConsumer: Consumer<Groups.Output> = Consumer { event ->
        when (event) {
            Groups.Output.Dismiss -> router.popOverlay()
            is Groups.Output.AddNewGroup -> {
                output.accept(Schedule.Output.OpenPickGroup(event.isRoot))
                router.popOverlay()
            }
            is Groups.Output.UpdateGroup -> output.accept(Schedule.Output.GroupUpdated)
        }
    }

    val settingsOutputConsumer = Consumer<Settings.Output> { output ->
        when (output) {
            Settings.Output.GoBack -> router.popBackStack()
        }
    }

    private val scheduleNewsConsumer = Consumer<News> { news ->
        when (news) {
            News.OpenSettings -> router.push(Content.Settings)
            News.OpenGroupPicker -> router.pushOverlay(Overlay.GroupsPicker)
        }
    }

    override fun onAttach(ribLifecycle: Lifecycle, savedInstanceState: Bundle?) {
        ribLifecycle.createDestroy {
            bind(feature.news to scheduleNewsConsumer)
            bind(feature.news to output using NewsToOutput named "OutputTransformer")
            bind(input to feature using InputToWish)
        }
    }

    override fun onViewCreated(view: ScheduleView, viewLifecycle: Lifecycle) {
        viewLifecycle.startStop {
            bind(feature.news to Consumer<News> { news ->
                when (news) {
                    is News.RouteToWeekPicker -> view.openWeekPickerDialog(news.list, news.selectedWeek)
                }
            })
            bind(wrap(feature).distinctUntilChanged() to view using StateToViewModel)
            bind(view to feature using ViewEventToWish)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        timeCapsule.saveState(outState)
    }
}
