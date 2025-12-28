package com.example.vzlatv

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player

class PlaybackManager(private val player: ExoPlayer) {
    private var urls: List<String> = emptyList()
    private var fallback: String? = null
    private var index = 0

    private val listener = object : Player.Listener {
        override fun onPlayerError(error: PlaybackException) {
            // try next
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
    }

    fun start() {
        if (urls.isEmpty()) {
            fallback?.let { playUrl(it) }
            return
        }
        index = 0
        playUrl(urls[index])
    }

    private fun playNextOrFallback() {
        index++
        if (index < urls.size) {
            playUrl(urls[index])
        } else {
            fallback?.let { playUrl(it) }
        }
    }

    private fun playUrl(url: String) {
        player.setMediaItem(MediaItem.fromUri(url))
        player.prepare()
        player.playWhenReady = true
    }

    fun release() {
        player.removeListener(listener)
    }
}
