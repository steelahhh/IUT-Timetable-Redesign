package com.alefimenko.iuttimetable.di

import androidx.room.Room
import com.alefimenko.iuttimetable.schedule.SchedulesDatabase
import com.google.gson.GsonBuilder
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

/*
 * Created by Alexander Efimenko on 2019-04-24.
 */

val localDataModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            SchedulesDatabase::class.java, "Schedules"
        ).fallbackToDestructiveMigration().build()
    }

    single { get<SchedulesDatabase>().schedulesDao }

//    single<ScheduleParser>()

    single { GsonBuilder().enableComplexMapKeySerialization().create() }
}
