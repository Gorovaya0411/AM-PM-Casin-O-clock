package com.clocktime.severalwest.utill.extension

import androidx.annotation.StringRes
import com.clocktime.severalwest.R

enum class CustomEvent(
    @StringRes val customEventTextId: Int
) {
    TIME(
        customEventTextId = R.string.custom_minute
    ),
    SECONDS(
        customEventTextId = R.string.custom_sound
    ),
    MINUTE(
        customEventTextId = R.string.custom_hour
    ),
    HOUR(
    customEventTextId = R.string.custom_stopwatch
    ),
    CUSTOM_GEO(
        customEventTextId = R.string.custom_time
    ),
}