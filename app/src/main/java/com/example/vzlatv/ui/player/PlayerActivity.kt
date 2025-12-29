package com.example.vzlatv.ui.player

import android.os.Bundle
import android.util.Log
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
        Log.d("VzlaTV", "PlayerActivity started, m3u_url=$m3uUrl fallback_url=$fallbackUrl")

        playbackManager.onExhausted = {
            // show final message when nothing worked
            runOnUiThread {
                android.widget.Toast.makeText(
                    this,
                    "Contacte al desarrollador DEIKERMAN",
                    android.widget.Toast.LENGTH_LONG
                ).show()
            }
        }

        if (!m3uUrl.isNullOrBlank()) {
            val parser = M3UParser()
            scope.launch {
                try {
                    Log.d("VzlaTV", "Fetching M3U from $m3uUrl")
                    val entries = parser.fetchAndParse(m3uUrl)
                    val urls = entries.map { it.url }
                    Log.d("VzlaTV", "M3U parsed entries=${entries.size} urls=$urls")
                    if (urls.isEmpty()) {
                        // nothing parsed; try fallback directly or notify
                        if (!fallbackUrl.isNullOrBlank()) {
                            Log.d("VzlaTV", "No playlist entries parsed, will try fallback $fallbackUrl")
                            playbackManager.setPlaylist(emptyList(), fallbackUrl)
                            playbackManager.start()
                        } else {
                            playbackManager.onExhausted?.invoke()
                        }
                    } else {
                        Log.d("VzlaTV", "Starting playback with playlist size=${urls.size}")
                        playbackManager.setPlaylist(urls, fallbackUrl)
                        playbackManager.start()
                    }
                } catch (e: Exception) {
                    Log.e("VzlaTV", "M3U fetch/parse failed: ${e.message}", e)
                    // parsing/fetch failed -> try fallback or notify
                    if (!fallbackUrl.isNullOrBlank()) {
                        Log.d("VzlaTV", "Fetch failed — trying fallback $fallbackUrl")
                        playbackManager.setPlaylist(emptyList(), fallbackUrl)
                        playbackManager.start()
                    } else {
                        playbackManager.onExhausted?.invoke()
                    }
                }
            }
        } else {
            // if no m3u provided, try fallback directly
            Log.d("VzlaTV", "No m3u_url provided — using fallback if available: $fallbackUrl")
            if (!fallbackUrl.isNullOrBlank()) {
                playbackManager.setPlaylist(emptyList(), fallbackUrl)
                playbackManager.start()
            } else {
                playbackManager.onExhausted?.invoke()
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
