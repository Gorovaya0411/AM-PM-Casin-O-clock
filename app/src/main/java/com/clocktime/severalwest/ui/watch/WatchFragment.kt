package com.clocktime.severalwest.ui.watch

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.clocktime.severalwest.R
import com.clocktime.severalwest.databinding.FragmentWatchBinding
import com.clocktime.severalwest.model.StyleBg
import com.clocktime.severalwest.store.PreferenceStoreImpl
import com.clocktime.severalwest.ui.base.BaseFragment
import com.clocktime.severalwest.ui.activity.main_two.MainTwoActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class WatchFragment : BaseFragment<FragmentWatchBinding>(R.layout.fragment_watch) {

    private val contextTopActivity: MainTwoActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainTwoActivity)
    }

    @Inject
    lateinit var preferenceStoreImpl: PreferenceStoreImpl
    private var checkButton = false

    @SuppressLint("SourceLockedOrientationActivity", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contextTopActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        initView()
    }

    private fun initView() {
        with(binding) {
            when (preferenceStoreImpl.styleBg) {
                StyleBg.BG_ONE -> watchScreen.setBackgroundColor(Color.BLACK)
                StyleBg.BG_TWO -> watchScreen.setBackgroundResource(R.drawable.bg_main_two)
                StyleBg.BG_THREE -> watchScreen.setBackgroundResource(R.drawable.bg_main_three)
                StyleBg.BG_FOUR -> watchScreen.setBackgroundResource(R.drawable.bg_main_four)
                StyleBg.BG_FIVE -> watchScreen.setBackgroundResource(R.drawable.bg_main_five)
                StyleBg.BG_SIX -> watchScreen.setBackgroundResource(R.drawable.bg_main_six)
                StyleBg.BG_SEVEN -> watchScreen.setBackgroundResource(R.drawable.bg_main_seven)
                StyleBg.BG_EIGHT -> watchScreen.setBackgroundResource(R.drawable.bg_main_eight)
                StyleBg.BG_NINE -> watchScreen.setBackgroundResource(R.drawable.bg_main_nine)
            }

            watchLocationSearchImgBtn.setOnClickListener { timing() }
            watchTimeTxt.text = context?.getString(R.string.watch_null) ?: ""
        }
    }

    private fun timing() {
        checkButton = true
        val c = Calendar.getInstance()

        var month = c.get(Calendar.MONTH).toString()
        val day = c.get(Calendar.DAY_OF_MONTH).toString()
        var dayOfWeek = c.get(Calendar.DAY_OF_WEEK).toString()

        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        if (minute <= CHECK_LESS_NINE) binding.watchTimeTxt.text =
            context?.getString(R.string.watch_time_less_nine, hour.toString(), minute.toString())
                ?: "00:00"
        else binding.watchTimeTxt.text =
            context?.getString(R.string.watch_time, hour.toString(), minute.toString()) ?: "00:00"

        month = when (month) {
            "1" -> "февр."
            "2" -> "мар."
            "3" -> "апр."
            "4" -> "май."
            "5" -> "июн"
            "6" -> "июл."
            "7" -> "авг."
            "8" -> "сент."
            "9" -> "окт."
            "10" -> "нояб."
            "11" -> "дек."
            else -> "янв."
        }

        dayOfWeek = when (dayOfWeek) {
            "1" -> "вс"
            "2" -> "пн"
            "3" -> "вт"
            "4" -> "ср"
            "5" -> "чет"
            "6" -> "пт"
            else -> "сб"
        }

        binding.watchDateTxt.text =
            context?.getString(R.string.watch_date, dayOfWeek, day, month) ?: ""

        lifecycleScope.launch(Dispatchers.Main) {
            delay(CHECK_CHANGE_CONDITION_TIME)
            timing()
        }
    }

    companion object {
        private const val CHECK_CHANGE_CONDITION_TIME = 1000L
        private const val CHECK_LESS_NINE = 9
    }
}