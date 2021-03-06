package com.alefimenko.iuttimetable.pick_institute.mapper

import com.alefimenko.iuttimetable.pick_institute.PickInstituteView.Event
import com.alefimenko.iuttimetable.pick_institute.feature.PickInstituteFeature.Wish

internal object ViewEventToWish : (Event) -> Wish? {

    override fun invoke(event: Event): Wish? = when (event) {
        is Event.Retry -> Wish.FetchInstitutes
        is Event.FormPicked -> Wish.FormPicked(event.form)
        is Event.InstituteClicked -> Wish.InstitutePicked(event.institute)
        is Event.NextClicked -> Wish.NavigateToPickGroup
        else -> null
    }
}
