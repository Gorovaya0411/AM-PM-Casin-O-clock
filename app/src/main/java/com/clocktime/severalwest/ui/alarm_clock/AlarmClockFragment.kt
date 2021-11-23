package com.clocktime.severalwest.ui.alarm_clock

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.clocktime.severalwest.R
import com.clocktime.severalwest.databinding.FragmentAlarmClockBinding
import com.clocktime.severalwest.store.PreferenceStoreImpl
import com.clocktime.severalwest.ui.activity.main_two.MainTwoActivity
import com.clocktime.severalwest.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class AlarmClockFragment : BaseFragment<FragmentAlarmClockBinding>(R.layout.fragment_alarm_clock) {

    private val contextTopActivity: MainTwoActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainTwoActivity)
    }
    private val alarmManager: AlarmManager by lazy(LazyThreadSafetyMode.NONE) {
        context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    @Inject
    lateinit var preferenceStoreImpl: PreferenceStoreImpl

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        turningAlarmClock()
    }

    private fun initView() {
        val alarmClockAdapter = AdapterAlarmClock(::update, ::delete)
        alarmClockAdapter.setData(
            preferenceStoreImpl.listAlarmClock.alarmClock.filter { alarmClock -> !alarmClock.isDeleted }
                .toMutableList()
        )

        binding.alarmClockRecyclerView.layoutManager = LinearLayoutManager(contextTopActivity)
        binding.alarmClockRecyclerView.adapter = alarmClockAdapter

        binding.alarmClockPlusImgBtn.setOnClickListener {
            findNavController().navigate(R.id.action_alarmClockFragment_to_addedAlarmClockFragment)
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun turningAlarmClock() {

        val intent = Intent(contextTopActivity, Receiver::class.java)

        preferenceStoreImpl.listAlarmClock.alarmClock.forEach { alarm ->
            alarm.day.day.forEach { day ->
                val requestCode = alarm.id + day - 1
                val pendingIntent = PendingIntent.getBroadcast(
                    contextTopActivity,
                    requestCode,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

                if (alarm.condition) {
                    val calendar = Calendar.getInstance()

                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.MILLISECOND, 0)
                    calendar.set(Calendar.HOUR_OF_DAY, alarm.hour.toInt())
                    calendar.set(Calendar.MINUTE, alarm.minute.toInt())
                    calendar.set(Calendar.DAY_OF_WEEK, day)

                    var timeForSet = calendar.timeInMillis

                    if (timeForSet <= System.currentTimeMillis()) {
                        timeForSet += WEEK_IN_MILLIS
                    }

                    alarmManager.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        timeForSet,
                        WEEK_IN_MILLIS,
                        pendingIntent
                    )
                } else {
                    alarmManager.cancel(pendingIntent)
                }
            }
        }
    }


    private fun update(index: Int, condition: Boolean) {
        val newListAlarmClock = preferenceStoreImpl.listAlarmClock
        newListAlarmClock.alarmClock[index / 7].condition = condition
        preferenceStoreImpl.listAlarmClock = newListAlarmClock
        turningAlarmClock()

    }

    private fun delete(index: Int) {
        val newListAlarmClock = preferenceStoreImpl.listAlarmClock
        newListAlarmClock.alarmClock[index / 7].isDeleted = true
        preferenceStoreImpl.listAlarmClock = newListAlarmClock
    }

    companion object {
        private const val WEEK_IN_MILLIS = (1000 * 60 * 60 * 24 * 7).toLong()
    }
}