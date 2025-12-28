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
import com.example.vzlatv.ui.player.PlayerActivity
import com.bumptech.glide.Glide

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
        // load logo if available
        if (ch.logoUrl.isNotBlank()) {
            Glide.with(holder.logo).load(ch.logoUrl).into(holder.logo)
        } else {
            holder.logo.setImageResource(android.R.color.darker_gray)
        }

        holder.itemView.setOnClickListener {
            val ctx = holder.itemView.context
            val intent = Intent(ctx, PlayerActivity::class.java).apply {
                putExtra("m3u_url", "")
                putExtra("fallback_url", ch.primaryStreamUrl)
            }
            ctx.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = items.size
}
