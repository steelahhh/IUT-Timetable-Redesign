@file:SuppressWarnings("LongParameterList", "LongMethod")

package com.alefimenko.iuttimetable.pick_institute.builder

import com.alefimenko.iuttimetable.data.DataModule
import com.alefimenko.iuttimetable.data.local.Constants
import com.alefimenko.iuttimetable.pick_institute.PickInstitute.Input
import com.alefimenko.iuttimetable.pick_institute.PickInstitute.Output
import com.alefimenko.iuttimetable.pick_institute.PickInstituteInteractor
import com.alefimenko.iuttimetable.pick_institute.PickInstituteNode
import com.alefimenko.iuttimetable.pick_institute.PickInstituteRouter
import com.alefimenko.iuttimetable.pick_institute.PickInstituteViewImpl
import com.alefimenko.iuttimetable.pick_institute.feature.PickInstituteFeature
import com.badoo.mvicore.android.AndroidTimeCapsule
import com.badoo.ribs.core.builder.BuildParams
import dagger.Provides
import io.reactivex.ObservableSource
import io.reactivex.functions.Consumer
import javax.inject.Named

@dagger.Module(includes = [DataModule::class])
internal object PickInstituteModule {

    @PickInstituteScope
    @Provides
    @Named(PickInstituteFeature.CAPSULE_KEY)
    @JvmStatic
    internal fun timeCapsule(
        buildParams: BuildParams<Nothing?>
    ) = AndroidTimeCapsule(buildParams.savedInstanceState)

    @PickInstituteScope
    @Provides
    @JvmStatic
    internal fun router(
        component: PickInstituteComponent,
        buildParams: BuildParams<Nothing?>
    ): PickInstituteRouter = PickInstituteRouter(
        buildParams = buildParams,
        transitionHandler = null
    )

    @PickInstituteScope
    @Provides
    @JvmStatic
    internal fun interactor(
        buildParams: BuildParams<Nothing?>,
        router: PickInstituteRouter,
        input: ObservableSource<Input>,
        output: Consumer<Output>,
        @Named(PickInstituteFeature.CAPSULE_KEY)
        timeCapsule: AndroidTimeCapsule,
        feature: PickInstituteFeature
    ): PickInstituteInteractor = PickInstituteInteractor(
        buildParams = buildParams,
        router = router,
        input = input,
        output = output,
        feature = feature,
        timeCapsule = timeCapsule
    )

    @PickInstituteScope
    @Provides
    @JvmStatic
    internal fun node(
        @Named(Constants.PICK_GROUP_ROOT)
        isRoot: Boolean,
        buildParams: BuildParams<Nothing?>,
        router: PickInstituteRouter,
        interactor: PickInstituteInteractor,
        input: ObservableSource<Input>,
        output: Consumer<Output>,
        feature: PickInstituteFeature
    ): PickInstituteNode = PickInstituteNode(
        buildParams = buildParams,
        viewFactory = PickInstituteViewImpl.Factory().invoke(isRoot),
        router = router,
        interactor = interactor,
        input = input,
        output = output,
        feature = feature
    )
}
