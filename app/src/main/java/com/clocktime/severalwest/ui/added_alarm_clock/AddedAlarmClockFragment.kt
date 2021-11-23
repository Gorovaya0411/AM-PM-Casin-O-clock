package com.clocktime.severalwest.ui.added_alarm_clock

import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.CheckBox
import android.widget.TimePicker
import com.clocktime.severalwest.R
import com.clocktime.severalwest.databinding.FragmentAddedAlarmClockBinding
import com.clocktime.severalwest.model.AlarmClock
import com.clocktime.severalwest.model.ArrayListAlarmClock
import com.clocktime.severalwest.model.ArrayListDay
import com.clocktime.severalwest.model.StyleBg
import com.clocktime.severalwest.store.PreferenceStoreImpl
import com.clocktime.severalwest.ui.activity.main_two.MainTwoActivity
import com.clocktime.severalwest.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

@AndroidEntryPoint
class AddedAlarmClockFragment :
    BaseFragment<FragmentAddedAlarmClockBinding>(R.layout.fragment_added_alarm_clock) {

    private val contextTopActivity: MainTwoActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainTwoActivity)
    }
    private lateinit var alarmClockForSave: ArrayList<AlarmClock>
    private lateinit var dayForSave: ArrayList<Int>
    private var enteredResponse = ""

    @Inject
    lateinit var preferenceStoreImpl: PreferenceStoreImpl

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        workEditText()
    }

    private fun initView() {

        val calendar = Calendar.getInstance()

        with(binding) {

            alarmClockForSave = ArrayList()
            dayForSave = ArrayList()
            preferenceStoreImpl.alarmClockComment = ""
            preferenceStoreImpl.alarmClockHour = "00"
            preferenceStoreImpl.alarmClockMinute = "00"
            addedAlarmClockHoursTxt.text = context?.getString(R.string.stopwatch_null) ?: ""
            addedAlarmClockMinutesTxt.text = context?.getString(R.string.stopwatch_null) ?: ""

            when (calendar.get(Calendar.DAY_OF_WEEK)) {
                1 -> saveDay(addedAlarmClockSunCb, 1)
                2 -> saveDay(addedAlarmClockMonCb, 2)
                3 -> saveDay(addedAlarmClockTueCb, 3)
                4 -> saveDay(addedAlarmClockWedCb, 4)
                5 -> saveDay(addedAlarmClockThuCb, 5)
                6 -> saveDay(addedAlarmClockFriCb, 6)
                7 -> saveDay(addedAlarmClockSatCb, 7)
            }

            when (preferenceStoreImpl.styleBg) {
                StyleBg.BG_ONE -> addedAlarmClockScreen.setBackgroundColor(Color.BLACK)
                StyleBg.BG_TWO -> addedAlarmClockScreen.setBackgroundResource(R.drawable.bg_main_two)
                StyleBg.BG_THREE -> addedAlarmClockScreen.setBackgroundResource(R.drawable.bg_main_three)
                StyleBg.BG_FOUR -> addedAlarmClockScreen.setBackgroundResource(R.drawable.bg_main_four)
                StyleBg.BG_FIVE -> addedAlarmClockScreen.setBackgroundResource(R.drawable.bg_main_five)
                StyleBg.BG_SIX -> addedAlarmClockScreen.setBackgroundResource(R.drawable.bg_main_six)
                StyleBg.BG_SEVEN -> addedAlarmClockScreen.setBackgroundResource(R.drawable.bg_main_seven)
                StyleBg.BG_EIGHT -> addedAlarmClockScreen.setBackgroundResource(R.drawable.bg_main_eight)
                StyleBg.BG_NINE -> addedAlarmClockScreen.setBackgroundResource(R.drawable.bg_main_nine)
            }

            addedAlarmClockSaveImgBtn.setOnClickListener {
                save()
            }

            addedAlarmClockMonCb.setOnClickListener {
                animCheckBox(addedAlarmClockMonCb, 2)
            }

            addedAlarmClockTueCb.setOnClickListener {
                animCheckBox(addedAlarmClockTueCb, 3)
            }

            addedAlarmClockWedCb.setOnClickListener {
                animCheckBox(addedAlarmClockWedCb, 4)
            }

            addedAlarmClockThuCb.setOnClickListener {
                animCheckBox(addedAlarmClockThuCb, 5)
            }

            addedAlarmClockFriCb.setOnClickListener {
                animCheckBox(addedAlarmClockFriCb, 6)
            }

            addedAlarmClockSatCb.setOnClickListener {
                animCheckBox(addedAlarmClockSatCb, 7)
            }

            addedAlarmClockSunCb.setOnClickListener {
                animCheckBox(addedAlarmClockSunCb, 1)
            }

            alarmClock.setOnClickListener {
                openDialogFragment()
            }

            addedAlarmClockCommentImgBtn.setOnClickListener {
                addedAlarmClockCommentEditTxt.hint = ""
            }
        }
    }

    private fun workEditText() {
        binding.addedAlarmClockCommentEditTxt.addTextChangedListener(object : MyTextWatcher() {
            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                enteredResponse = s.toString()
            }
        })

        binding.addedAlarmClockCommentEditTxt.setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                binding.addedAlarmClockCommentEditTxt.setText(enteredResponse)
                preferenceStoreImpl.alarmClockComment = enteredResponse
                return@OnKeyListener true
            }
            false
        })
    }

    private fun saveDay(day: CheckBox, index: Int) {
        day.isChecked = true
        animCheckBox(day, index)
    }

    private fun save() {
        with(preferenceStoreImpl) {
            listAlarmClock.alarmClock.forEach { alarmClock ->
                alarmClockForSave.add(alarmClock)
            }
            alarmClockForSave.add(
                AlarmClock(
                    listAlarmClock.alarmClock.size * 7,
                    alarmClockComment,
                    alarmClockHour,
                    alarmClockMinute,
                    listDay,
                    condition = true,
                    isDeleted = false
                )
            )
            listAlarmClock = ArrayListAlarmClock(alarmClockForSave)
            contextTopActivity.onBackPressed()
        }
    }

    private fun animCheckBox(checkBox: CheckBox, index: Int) {
        if (checkBox.isChecked) {
            dayForSave.add(index)
            preferenceStoreImpl.listDay = ArrayListDay(dayForSave)
        } else dayForSave.remove(index)
    }

    private fun openDialogFragment() {
        val cal = Calendar.getInstance()
        val timeSetListener =
            TimePickerDialog.OnTimeSetListener { _: TimePicker?, hour: Int, minute: Int ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                var minuteSave = cal.get(Calendar.MINUTE).toString()
                var hourSave = cal.get(Calendar.HOUR_OF_DAY).toString()

                if (cal.get(Calendar.MINUTE) <= 9) {
                    minuteSave = "0${cal.get(Calendar.MINUTE)}"
                }
                if (cal.get(Calendar.HOUR_OF_DAY) <= 9) {
                    hourSave = "0${cal.get(Calendar.HOUR_OF_DAY)}"
                }

                preferenceStoreImpl.alarmClockMinute = minuteSave
                preferenceStoreImpl.alarmClockHour = hourSave
                binding.addedAlarmClockMinutesTxt.text = minuteSave
                binding.addedAlarmClockHoursTxt.text = hourSave

            }
        TimePickerDialog(
            contextTopActivity,
            timeSetListener,
            cal.get(Calendar.HOUR_OF_DAY),
            cal.get(Calendar.MINUTE),
            true
        ).show()
    }
}

