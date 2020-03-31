package com.alefimenko.iuttimetable.presentation.ribs.schedule

import com.alefimenko.iuttimetable.data.remote.model.Schedule
import com.alefimenko.iuttimetable.presentation.ribs.schedule.ScheduleView.Event
import com.alefimenko.iuttimetable.presentation.ribs.schedule.ScheduleView.ViewModel
import com.badoo.ribs.core.view.RibView
import com.badoo.ribs.core.view.ViewFactory
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer

interface ScheduleView : RibView,
    ObservableSource<Event>,
    Consumer<ViewModel> {

    fun openWeekPickerDialog(weeks: List<String>, selectedWeek: Int)

    sealed class Event {
        data class ChangeClassVisibility(val classIndex: Int, val dayIndex: Int, val weekIndex: Int) : Event()
        data class SwitchToWeek(val weekIdx: Int) : Event()
        object ChangeWeek : Event()
        object OnSettingsClick : Event()
        object Retry : Event()
    }

    data class ViewModel(
        val isLoading: Boolean,
        val isError: Boolean,
        val schedule: Schedule? = null,
        val currentDay: Int,
        val currentWeek: Int,
        val selectedWeek: Int
    )

    interface Factory : ViewFactory<Nothing?, ScheduleView>
}
