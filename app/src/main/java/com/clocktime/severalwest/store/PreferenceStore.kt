package com.clocktime.severalwest.store

import com.clocktime.severalwest.model.ArrayListAlarmClock
import com.clocktime.severalwest.model.ArrayListDay
import com.clocktime.severalwest.model.StyleBg

interface PreferenceStore {
    var styleBg: StyleBg?
    var listAlarmClock: ArrayListAlarmClock
    var listDay: ArrayListDay
    var alarmClockComment: String
    var alarmClockHour: String
    var alarmClockMinute: String
    var firstRun: Boolean
    var alarmCloclUri: String
    var reAlarmClock: Int
    var uuIdAlarmClocl: String
    var deAlarmClocl: Int
    var adIdAlarmClocl: String
    var installationId: String
}