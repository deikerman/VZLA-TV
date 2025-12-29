package com.example.vzlatv.ui.main

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vzlatv.Channel
import com.example.vzlatv.R
import com.example.vzlatv.ChannelIcons
import com.example.vzlatv.ui.player.PlayerActivity

class ChannelAdapter(private val items: List<Channel>) : RecyclerView.Adapter<ChannelAdapter.VH>() {

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.channel_title)
        val logo: ImageView = view.findViewById(R.id.channel_logo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_channel, parent, false)
        return VH(v)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val ch = items[position]
        holder.title.text = ch.name
        holder.itemView.isFocusable = true
        // usar icono local fijo según nombre del canal si existe
        val iconRes = ChannelIcons.iconFor(ch.name)
        if (iconRes != null) {
            holder.logo.setImageResource(iconRes)
        } else {
            holder.logo.setImageDrawable(null)
        }

        holder.itemView.setOnClickListener {
            val ctx = holder.itemView.context
            val intent = Intent(ctx, PlayerActivity::class.java).apply {
                putExtra("m3u_url", ch.primaryStreamUrl)
                putExtra("fallback_url", ch.fallbackStreamUrl)
            }
            ctx.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}
