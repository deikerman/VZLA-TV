package com.example.vzlatv

import androidx.annotation.DrawableRes

object ChannelIcons {
    @DrawableRes
    fun iconFor(channelName: String): Int? {
        val key = channelName.lowercase()
        return when {
            key.contains("vtv") -> R.drawable.ic_vtv
            key.contains("tves") -> R.drawable.ic_tves
            key.contains("telesur") -> R.drawable.ic_telesur
            key.contains("tvfanb") -> R.drawable.ic_tvfanb
            key.contains("vive") -> R.drawable.ic_vive
            else -> null
        }
    }
}
