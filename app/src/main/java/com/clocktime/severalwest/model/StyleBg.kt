package com.clocktime.severalwest.model

enum class StyleBg {
    BG_ONE,
    BG_TWO,
    BG_THREE,
    BG_FOUR,
    BG_FIVE,
    BG_SIX,
    BG_SEVEN,
    BG_EIGHT,
    BG_NINE;

    companion object {
        fun toBgStyle(bgStyle: String?): StyleBg? {
            return try {
                if (bgStyle != null) {
                    valueOf(bgStyle)
                } else {
                    BG_ONE
                }
            } catch (ex: Exception) {
                BG_TWO
            }
        }
    }
}