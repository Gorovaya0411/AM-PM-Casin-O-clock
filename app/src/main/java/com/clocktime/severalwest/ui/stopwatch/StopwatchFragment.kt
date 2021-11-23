package com.clocktime.severalwest.ui.stopwatch

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.clocktime.severalwest.R
import com.clocktime.severalwest.databinding.FragmentStopwatchBinding
import com.clocktime.severalwest.model.StyleBg
import com.clocktime.severalwest.store.PreferenceStoreImpl
import com.clocktime.severalwest.ui.base.BaseFragment
import com.clocktime.severalwest.ui.activity.main_two.MainTwoActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
class StopwatchFragment : BaseFragment<FragmentStopwatchBinding>(R.layout.fragment_stopwatch) {

    private val contextTopActivity: MainTwoActivity by lazy(LazyThreadSafetyMode.NONE) {
        (activity as MainTwoActivity)
    }
    private var condition = true

    @Inject
    lateinit var preferenceStoreImpl: PreferenceStoreImpl
    private var seconds = 0
    private lateinit var job: Job

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contextTopActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        initView()
    }

    private fun initView() {

        with(binding) {
            when (preferenceStoreImpl.styleBg) {
                StyleBg.BG_ONE -> stopwatchScreen.setBackgroundColor(Color.BLACK)
                StyleBg.BG_TWO -> stopwatchScreen.setBackgroundResource(R.drawable.bg_main_two)
                StyleBg.BG_THREE -> stopwatchScreen.setBackgroundResource(R.drawable.bg_main_three)
                StyleBg.BG_FOUR -> stopwatchScreen.setBackgroundResource(R.drawable.bg_main_four)
                StyleBg.BG_FIVE -> stopwatchScreen.setBackgroundResource(R.drawable.bg_main_five)
                StyleBg.BG_SIX -> stopwatchScreen.setBackgroundResource(R.drawable.bg_main_six)
                StyleBg.BG_SEVEN -> stopwatchScreen.setBackgroundResource(R.drawable.bg_main_seven)
                StyleBg.BG_EIGHT -> stopwatchScreen.setBackgroundResource(R.drawable.bg_main_eight)
                StyleBg.BG_NINE -> stopwatchScreen.setBackgroundResource(R.drawable.bg_main_nine)
            }

            binding.stopwatchHoursTxt.text = context?.getString(R.string.stopwatch_null) ?: "00"
            binding.stopwatchMinutesTxt.text = context?.getString(R.string.stopwatch_null) ?: "00"
            binding.stopwatchSecondsTxt.text = context?.getString(R.string.stopwatch_null) ?: "00"

            stopwatchStartImgBtn.setOnClickListener {
                startTimer()
            }

            stopwatchDischargeImgBtn.setOnClickListener {
                stopTimer()
            }
        }
    }

    private fun stopTimer() {
        condition = true
        seconds = 0
        binding.stopwatchHoursTxt.text = context?.getString(R.string.stopwatch_null) ?: "00"
        binding.stopwatchMinutesTxt.text = context?.getString(R.string.stopwatch_null) ?: "00"
        binding.stopwatchSecondsTxt.text = context?.getString(R.string.stopwatch_null) ?: "00"
        job.cancel()
    }

    private suspend fun start() {
        seconds++

        if ((seconds / CALCULATING_RIGHT_TIME) <= CHECK_LESS_NINE) {
            binding.stopwatchHoursTxt.text =
                context?.getString(
                    R.string.stopwatch_time,
                    (seconds / CALCULATING_RIGHT_TIME).toString()
                ) ?: "00"
        } else binding.stopwatchHoursTxt.text =
            (seconds / CALCULATING_RIGHT_TIME).toString()

        if (((seconds % CALCULATING_RIGHT_TIME) / TRANSLATION_THE_RIGHT_TIME) <= CHECK_LESS_NINE) {
            binding.stopwatchMinutesTxt.text =
                context?.getString(
                    R.string.stopwatch_time,
                    ((seconds % CALCULATING_RIGHT_TIME) / TRANSLATION_THE_RIGHT_TIME).toString()
                )
                    ?: "00"
        } else binding.stopwatchMinutesTxt.text =
            ((seconds % CALCULATING_RIGHT_TIME) / TRANSLATION_THE_RIGHT_TIME).toString()

        if ((seconds % TRANSLATION_THE_RIGHT_TIME) <= CHECK_LESS_NINE) {
            binding.stopwatchSecondsTxt.text =
                context?.getString(
                    R.string.stopwatch_time,
                    (seconds % TRANSLATION_THE_RIGHT_TIME).toString()
                )
                    ?: "00"
        } else binding.stopwatchSecondsTxt.text =
            (seconds % TRANSLATION_THE_RIGHT_TIME).toString()

        delay(CHANGE_CONDITION_TIME)
        start()
    }

    private fun startTimer() {
        if (condition) {
            condition = false
            job = lifecycleScope.launch(Dispatchers.Main) {
                start()
            }
        }
    }

    companion object {
        private const val CHANGE_CONDITION_TIME = 1000L
        private const val CHECK_LESS_NINE = 9
        private const val CALCULATING_RIGHT_TIME = 3600
        private const val TRANSLATION_THE_RIGHT_TIME = 60
    }
}
