@file:SuppressWarnings("LongParameterList", "LongMethod")

package com.alefimenko.iuttimetable.schedule.builder

import com.alefimenko.iuttimetable.data.DataModule
import com.alefimenko.iuttimetable.data.date.DateInteractor
import com.alefimenko.iuttimetable.data.date.DateInteractorImpl
import com.alefimenko.iuttimetable.data.local.Preferences
import com.alefimenko.iuttimetable.groups.Groups
import com.alefimenko.iuttimetable.groups.builder.GroupsBuilder
import com.alefimenko.iuttimetable.schedule.Schedule
import com.alefimenko.iuttimetable.schedule.Schedule.Input
import com.alefimenko.iuttimetable.schedule.Schedule.Output
import com.alefimenko.iuttimetable.schedule.ScheduleInteractor
import com.alefimenko.iuttimetable.schedule.ScheduleNode
import com.alefimenko.iuttimetable.schedule.ScheduleRouter
import com.alefimenko.iuttimetable.schedule.feature.ScheduleFeature
import com.alefimenko.iuttimetable.settings.Settings
import com.alefimenko.iuttimetable.settings.builder.SettingsBuilder
import com.badoo.mvicore.android.AndroidTimeCapsule
import com.badoo.ribs.core.builder.BuildParams
import com.badoo.ribs.core.routing.transition.handler.CrossFader
import com.badoo.ribs.core.routing.transition.handler.Slider
import com.badoo.ribs.core.routing.transition.handler.TransitionHandler
import dagger.Provides
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer
import javax.inject.Named

@dagger.Module(includes = [DataModule::class])
internal object ScheduleModule {

    @ScheduleScope
    @Provides
    @Named(ScheduleFeature.CAPSULE_KEY)
    @JvmStatic
    internal fun timeCapsule(
        buildParams: BuildParams<Nothing?>
    ) = AndroidTimeCapsule(buildParams.savedInstanceState)

    @ScheduleScope
    @Provides
    @JvmStatic
    fun provideDateInteractor(preferences: Preferences): DateInteractor = DateInteractorImpl(preferences)

    @ScheduleScope
    @Provides
    @JvmStatic
    internal fun router(
        component: ScheduleComponent,
        buildParams: BuildParams<Nothing?>
    ): ScheduleRouter {
        val settings = SettingsBuilder(component)
        val groups = GroupsBuilder(component)
        return ScheduleRouter(
            settingsBuilder = settings,
            groupsBuilder = groups,
            buildParams = buildParams,
            transitionHandler = TransitionHandler.multiple(
                CrossFader(condition = {
                    it.configuration == ScheduleRouter.Configuration.Overlay.GroupsPicker
                }),
                Slider(condition = {
                    it.configuration == ScheduleRouter.Configuration.Content.Settings
                })
            )
        )
    }

    @ScheduleScope
    @Provides
    @JvmStatic
    internal fun interactor(
        buildParams: BuildParams<Nothing?>,
        router: ScheduleRouter,
        @Named(ScheduleFeature.CAPSULE_KEY)
        timeCapsule: AndroidTimeCapsule,
        input: ObservableSource<Input>,
        output: Consumer<Output>,
        feature: ScheduleFeature
    ): ScheduleInteractor = ScheduleInteractor(
        buildParams = buildParams,
        router = router,
        input = input,
        output = output,
        timeCapsule = timeCapsule,
        feature = feature
    )

    @ScheduleScope
    @Provides
    @JvmStatic
    internal fun node(
        buildParams: BuildParams<Nothing?>,
        preferences: Preferences,
        customisation: Schedule.Customisation,
        router: ScheduleRouter,
        interactor: ScheduleInteractor,
        input: ObservableSource<Input>,
        output: Consumer<Output>,
        feature: ScheduleFeature
    ): ScheduleNode = ScheduleNode(
        buildParams = buildParams,
        viewFactory = customisation.viewFactory(preferences.isTabsEnabled),
        router = router,
        interactor = interactor,
        input = input,
        output = output,
        feature = feature
    )

    @ScheduleScope
    @Provides
    @JvmStatic
    fun settingsOutput(
        scheduleInteractor: ScheduleInteractor
    ): Consumer<Settings.Output> = scheduleInteractor.settingsOutputConsumer

    @ScheduleScope
    @Provides
    @JvmStatic
    fun groupsInput(): ObservableSource<Groups.Input> = Observable.empty()

    @ScheduleScope
    @Provides
    @JvmStatic
    fun groupsOutput(
        scheduleInteractor: ScheduleInteractor
    ): Consumer<Groups.Output> = scheduleInteractor.groupsOutputConsumer
}
