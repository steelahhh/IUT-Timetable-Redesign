package com.alefimenko.iuttimetable.feature.schedule.schedulepage

import com.alefimenko.iuttimetable.R
import com.alefimenko.iuttimetable.util.requireActivity
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.support.RouterPagerAdapter

/*
 * Created by Alexander Efimenko on 2019-04-18.
 */

class SchedulePagerAdapter(
    private val host: Controller
) : RouterPagerAdapter(host) {
    override fun configureRouter(router: Router, position: Int) {
        router.setRoot(
            RouterTransaction.with(
                SchedulePageController(position)
            )
        )
    }

    override fun getCount() = 6

    override fun getPageTitle(position: Int): CharSequence? {
        return host.requireActivity().resources.getStringArray(R.array.short_days)[position]
    }
}
