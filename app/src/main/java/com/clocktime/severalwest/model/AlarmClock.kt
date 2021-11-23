package com.clocktime.severalwest.model

data class AlarmClock(
    val id: Int,
    val comment: String,
    val hour: String,
    val minute: String,
    val day: ArrayListDay,
    var condition: Boolean,
    var isDeleted: Boolean
)