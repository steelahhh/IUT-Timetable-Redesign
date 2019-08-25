package com.alefimenko.iuttimetable.presentation.di.modules

import com.alefimenko.iuttimetable.data.local.schedule.GroupsDao
import com.alefimenko.iuttimetable.presentation.root.RootInteractor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import ru.terrakok.cicerone.Cicerone

/*
 * Created by Alexander Efimenko on 2019-04-05.
 */

val rootModule = module {
    single {
        RootInteractor(get(), get(named(GroupsDao.TAG)), get())
    }

    val cicerone = Cicerone.create()
    single { cicerone.navigatorHolder }
    single { cicerone.router }
}
