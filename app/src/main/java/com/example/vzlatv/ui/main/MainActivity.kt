package com.example.vzlatv.ui.main

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
        rv.layoutManager = GridLayoutManager(this, 3)
    }
}
