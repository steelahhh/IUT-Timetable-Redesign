package com.alefimenko.iuttimetable.data.local.schedule

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alefimenko.iuttimetable.data.local.model.ScheduleEntity

/*
 * Created by Alexander Efimenko on 2018-12-12.
 */

@Database(entities = [(ScheduleEntity::class)], version = 4, exportSchema = false)
abstract class SchedulesDatabase : RoomDatabase() {
    abstract val schedulesDao: SchedulesDao
}