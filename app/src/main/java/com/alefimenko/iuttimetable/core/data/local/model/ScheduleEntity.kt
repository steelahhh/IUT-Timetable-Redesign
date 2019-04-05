package com.alefimenko.iuttimetable.core.data.local.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alefimenko.iuttimetable.feature.schedule.model.Schedule
import com.google.gson.GsonBuilder
import kotlinx.android.parcel.Parcelize

/*
 * Created by Alexander Efimenko on 2018-12-12.
 */

@Parcelize
@Entity(tableName = "schedules")
data class ScheduleEntity @JvmOverloads constructor(
    @ColumnInfo(name = "formId")
    var formId: Int = 0,
    @PrimaryKey @ColumnInfo(name = "groupId") var groupId: Int = -1,
    @ColumnInfo(name = "groupName")
    var groupName: String = "",
    @ColumnInfo(name = "instituteId")
    var instituteId: Int = -1,
    @ColumnInfo(name = "instituteName")
    var instituteName: String = "",
    @ColumnInfo(name = "schedule")
    var scheduleStr: String = "",
    @ColumnInfo(name = "rawSchedule")
    var rawScheduleStr: String = ""
) : Parcelable {

    val schedule: Schedule
        get() = GsonBuilder()
            .enableComplexMapKeySerialization()
            .create()
            .fromJson(scheduleStr, Schedule::class.java)

    val rawSchedule: Schedule
        get() = GsonBuilder()
            .enableComplexMapKeySerialization()
            .create()
            .fromJson(rawScheduleStr, Schedule::class.java)
}
