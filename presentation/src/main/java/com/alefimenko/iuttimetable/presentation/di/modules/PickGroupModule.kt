package com.alefimenko.iuttimetable.presentation.di.modules

import com.alefimenko.iuttimetable.presentation.di.Scopes.PICK_GROUP
import com.alefimenko.iuttimetable.presentation.pickgroup.PickGroupRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

/*
 * Created by Alexander Efimenko on 2019-02-16.
 */

val pickGroupModule = module {
    scope(named(PICK_GROUP)) {
        scoped {
            PickGroupRepository(get(), get())
        }
    }
}
