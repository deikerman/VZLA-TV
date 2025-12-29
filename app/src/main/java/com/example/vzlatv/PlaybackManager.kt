package com.example.vzlatv

import android.util.Log
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player

class PlaybackManager(private val player: ExoPlayer) {
    private var urls: List<String> = emptyList()
    private var fallback: String? = null
    private var index = 0
    private var triedFallback = false

    /**
     * Callback invoked when all attempts (playlist + fallback) have failed.
     */
    var onExhausted: (() -> Unit)? = null

    private val listener = object : Player.Listener {
        override fun onPlayerError(error: PlaybackException) {
            Log.e("VzlaTV", "player error: ${error.message}", error)
            playNextOrFallback()
        }
    }

    init {
        player.addListener(listener)
    }

    fun setPlaylist(urls: List<String>, fallbackUrl: String?) {
        this.urls = urls
        this.fallback = fallbackUrl
        index = 0
        triedFallback = false
    }

    fun start() {
        if (urls.isEmpty()) {
            // no playlist entries, try fallback directly
            fallback?.let {
                triedFallback = true
                playUrl(it)
            } ?: run {
                onExhausted?.invoke()
            }
            return
        }
        index = 0
        triedFallback = false
        playUrl(urls[index])
    }

    private fun playNextOrFallback() {
        // if there are more playlist urls, try next
        index++
        if (index < urls.size) {
            playUrl(urls[index])
            return
        }

        // playlist exhausted; try fallback once
        if (!triedFallback && !fallback.isNullOrBlank()) {
            triedFallback = true
            playUrl(fallback!!)
            return
        }

        // nothing left to try
        onExhausted?.invoke()
    }

    private fun playUrl(url: String) {
        logPlay(url)
        player.setMediaItem(MediaItem.fromUri(url))
        player.prepare()
        try {
            player.play()
        } catch (t: Throwable) {
            player.playWhenReady = true
        }
    }
    
    private fun logPlay(url: String) {
        Log.d("VzlaTV", "PlaybackManager playUrl called with url=$url")
    }

    fun release() {
        player.removeListener(listener)
    }
}
