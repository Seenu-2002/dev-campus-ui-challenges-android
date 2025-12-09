package com.seenu.dev.android.december25

import android.content.Context
import android.media.MediaPlayer

class SantaPianoKeyboardPlayer constructor(
    private val context: Context
) {

    private val fileNames = listOf(
        R.raw.snow_step,
        R.raw.mess_toy_piano,
        R.raw.chime_sound,
        R.raw.jingle_bells,
        R.raw.sleigh_bells,
        R.raw.toy_piano_key,
        R.raw.tiny_metallic_bell,
    )
    private val mediaPlayerCache: MutableMap<Int, MediaPlayer> = mutableMapOf()

    fun playNote(index: Int) {
        if (index > fileNames.lastIndex) {
            throw IndexOutOfBoundsException("Index $index is out of bounds for piano keys.")
        }

        val mediaPlayer = getMediaPlayer(index)
        mediaPlayer.seekTo(0)
        mediaPlayer.start()
    }

    private fun getMediaPlayer(index: Int): MediaPlayer {
        if (mediaPlayerCache.containsKey(index)) {
            return mediaPlayerCache[index]!!
        }

        val mediaPlayer = MediaPlayer.create(
            context,
            fileNames[index]
        )
        mediaPlayerCache[index] = mediaPlayer
        return mediaPlayer
    }

}