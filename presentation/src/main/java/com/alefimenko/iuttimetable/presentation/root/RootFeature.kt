package com.alefimenko.iuttimetable.presentation.root

import androidx.appcompat.app.AppCompatDelegate
import com.alefimenko.iuttimetable.common.extension.ioMainSchedulers
import com.alefimenko.iuttimetable.data.local.Preferences
import com.alefimenko.iuttimetable.data.local.schedule.GroupsDao
import com.alefimenko.iuttimetable.presentation.BuildConfig
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign

/*
 * Created by Alexander Efimenko on 2019-04-05.
 */

class RootFeature(
    private val sharedPreferences: Preferences,
    private val groupsDao: GroupsDao
) {

    private val cd = CompositeDisposable()

    fun setRootScreen() {
        val currentVersion = BuildConfig.VERSION_CODE

        cd += groupsDao.groups
            .ioMainSchedulers()
            .subscribe({
                // val hasNoGroup =
                //     groups.isEmpty() || sharedPreferences.currentGroup == ITEM_DOESNT_EXIST
                // val root = when {
                //     hasNoGroup -> Screens.PickInstituteScreen()
                //     else -> Screens.ScheduleScreen()
                // }
                // navigator.setRoot(root)
            }, {
                it.printStackTrace()
            })

        sharedPreferences.versionCode = currentVersion
    }

    fun updateTheme() {
        if (sharedPreferences.isNightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    fun onDestroy() {
        cd.clear()
    }
}
