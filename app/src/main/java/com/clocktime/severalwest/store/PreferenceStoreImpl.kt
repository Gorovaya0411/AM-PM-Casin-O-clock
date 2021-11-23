package com.clocktime.severalwest.store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.clocktime.severalwest.model.ArrayListAlarmClock
import com.clocktime.severalwest.model.ArrayListDay
import com.clocktime.severalwest.model.StyleBg
import com.clocktime.severalwest.utill.extension.*
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PreferenceStoreImpl @Inject constructor(
    @ApplicationContext context: Context
) : PreferenceStore {

    private val datastore: DataStore<Preferences> = context.dataStore

    override var styleBg: StyleBg?
        get() = runBlocking { StyleBg.toBgStyle(datastore.getString(KEY_CHECK_BG)) }
        set(value) = runBlocking {
            datastore.putString(value.toString(), KEY_CHECK_BG)
        }

    override var listAlarmClock: ArrayListAlarmClock
        get() = runBlocking {
            try {
                val gson = Gson().fromJson(
                    datastore.getString(KEY_LIST_ALARM_CLOCK, ""),
                    ArrayListAlarmClock::class.java
                )
                return@runBlocking gson ?: ArrayListAlarmClock(ArrayList())
            } catch (e: Exception) {
                ArrayListAlarmClock(ArrayList())
            }
        }
        set(value) = runBlocking {
            datastore.putString(Gson().toJson(value) ?: "", KEY_LIST_ALARM_CLOCK)
        }

    override var listDay: ArrayListDay
        get() = runBlocking {
            try {
                val gson = Gson().fromJson(
                    datastore.getString(KEY_LIST_DAY, ""),
                    ArrayListDay::class.java
                )
                return@runBlocking gson ?: ArrayListDay(ArrayList())
            } catch (e: Exception) {
                ArrayListDay(ArrayList())
            }
        }
        set(value) = runBlocking {
            datastore.putString(Gson().toJson(value) ?: "", KEY_LIST_DAY)
        }

    override var alarmClockComment: String
        get() = runBlocking { datastore.getString(KEY_SAVE_ALARM_CLOCK_COMMENT) }
        set(value) = runBlocking { datastore.putString(value, KEY_SAVE_ALARM_CLOCK_COMMENT) }

    override var alarmClockHour: String
        get() = runBlocking { datastore.getString(KEY_SAVE_ALARM_CLOCK_HOUR) }
        set(value) = runBlocking { datastore.putString(value, KEY_SAVE_ALARM_CLOCK_HOUR) }

    override var alarmClockMinute: String
        get() = runBlocking { datastore.getString(KEY_SAVE_ALARM_CLOCK_MINUTE) }
        set(value) = runBlocking { datastore.putString(value, KEY_SAVE_ALARM_CLOCK_MINUTE) }

    override var alarmCloclUri: String
        get() = runBlocking { datastore.getString(URI, "") }
        set(value) = runBlocking { datastore.putString(value, URI) }

    override var firstRun: Boolean
        get() = runBlocking { datastore.getBoolean(KEY_FIRST_RUN) }
        set(value) = runBlocking { datastore.putBoolean(value, KEY_FIRST_RUN) }

    override var reAlarmClock: Int
        get() = runBlocking { datastore.getInt(KEY_RE_PIN, 0) }
        set(value) = runBlocking { datastore.putInt(value, KEY_RE_PIN) }

    override var uuIdAlarmClocl: String
        get() = runBlocking { datastore.getString(KEY_UU_ID, "") }
        set(value) = runBlocking { datastore.putString(value, KEY_UU_ID) }

    override var adIdAlarmClocl: String
        get() = runBlocking { datastore.getString(KEY_AD_ID, "") }
        set(value) = runBlocking { datastore.putString(value, KEY_AD_ID) }

    override var installationId: String
        get() = runBlocking { datastore.getString(KEY_INSTALLATION_ID, "") }
        set(value) = runBlocking { datastore.putString(value, KEY_INSTALLATION_ID) }

    override var deAlarmClocl: Int
        get() = runBlocking { datastore.getInt(KEY_DE_PIN, 0) }
        set(value) = runBlocking { datastore.putInt(value, KEY_DE_PIN) }

    companion object {
        const val KEY_CHECK_BG = "check_bg"
        const val KEY_LIST_ALARM_CLOCK = "key_list_alarm_clock"
        const val KEY_LIST_DAY = "key_list_day"
        const val KEY_SAVE_ALARM_CLOCK_COMMENT = "key_save_alarm_clock_comment"
        const val KEY_SAVE_ALARM_CLOCK_HOUR = "key_save_alarm_clock_hour"
        const val KEY_SAVE_ALARM_CLOCK_MINUTE = "key_save_alarm_clock_minute"
        private const val KEY_FIRST_RUN = "key first launch"
        private const val URI = "URI"
        const val KEY_INSTALLATION_ID = "installation_id"
        const val KEY_RE_PIN = "key re pin"
        private const val KEY_UU_ID = "uuid"
        const val KEY_DE_PIN = "key de pin"
        const val KEY_AD_ID = "ad_id"
    }
}