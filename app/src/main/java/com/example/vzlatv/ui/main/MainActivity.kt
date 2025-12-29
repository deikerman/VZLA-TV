package com.example.vzlatv.ui.main

import android.content.Context
import android.util.Log
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vzlatv.ChannelRepository
import com.example.vzlatv.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.rv_channels)
        val channels = ChannelRepository().getChannels()
        val adapter = ChannelAdapter(channels)
        rv.adapter = adapter
        val span = calculateSpanCount(this)
        rv.layoutManager = GridLayoutManager(this, span)

        handleAutoplayIntent(intent)
    }

    override fun onNewIntent(intent: android.content.Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            setIntent(it)
            handleAutoplayIntent(it)
        }
    }

    private fun handleAutoplayIntent(intent: android.content.Intent) {
        val autoplayFallback = intent.getStringExtra("autoplay_fallback")
        Log.i("VZLATV","handleAutoplayIntent: autoplayFallback=$autoplayFallback")
        if (!autoplayFallback.isNullOrBlank()) {
            Log.i("VZLATV","launching PlayerActivity with fallback=$autoplayFallback")
            val pIntent = android.content.Intent(this, com.example.vzlatv.ui.player.PlayerActivity::class.java).apply {
                putExtra("m3u_url", "")
                putExtra("fallback_url", autoplayFallback)
            }
            startActivity(pIntent)
        }
    }

    private fun isTv(context: Context): Boolean {
        val uiMode = context.resources.configuration.uiMode and Configuration.UI_MODE_TYPE_MASK
        return uiMode == Configuration.UI_MODE_TYPE_TELEVISION
    }

    private fun calculateSpanCount(context: Context): Int {
        // TVs: force landscape and 3 columns
        if (isTv(context)) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            return 3
        }

        // Phones/tablets: adapt by orientation
        return when (resources.configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> 3
            else -> 2
        }
    }
}
