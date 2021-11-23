package com.clocktime.severalwest.ui.alarm_clock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import com.clocktime.severalwest.R


class Receiver : BroadcastReceiver() {
    private var mediaPlayer: MediaPlayer? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        mediaPlayer = MediaPlayer.create(context, R.raw.sound);
        mediaPlayer?.start()
    }
}