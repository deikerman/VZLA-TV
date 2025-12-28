package com.example.vzlatv.ui.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vzlatv.R
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.cancel
import com.example.vzlatv.M3UParser
import com.example.vzlatv.PlaybackManager

class PlayerActivity : AppCompatActivity() {
    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView
    private lateinit var playbackManager: PlaybackManager

    private val scope = CoroutineScope(Dispatchers.Main + Job())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        playerView = findViewById(R.id.player_view)

        player = ExoPlayer.Builder(this).build()
        playerView.player = player
        playbackManager = PlaybackManager(player)

        val m3uUrl = intent.getStringExtra("m3u_url")
        val fallbackUrl = intent.getStringExtra("fallback_url")

        if (!m3uUrl.isNullOrBlank()) {
            val parser = M3UParser()
            scope.launch {
                val entries = parser.fetchAndParse(m3uUrl)
                val urls = entries.map { it.url }
                playbackManager.setPlaylist(urls, fallbackUrl)
                playbackManager.start()
            }
        } else {
            // if no m3u provided, try fallback directly
            fallbackUrl?.let {
                playbackManager.setPlaylist(emptyList(), it)
                playbackManager.start()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        playbackManager.release()
        player.release()
        scope.cancel()
    }
}
