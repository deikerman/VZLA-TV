package com.example.vzlatv

import androidx.annotation.DrawableRes

object ChannelIcons {
    @DrawableRes
    fun iconFor(channelName: String): Int {
        return when (channelName.lowercase()) {
            "vtv" -> R.drawable.ic_vtv
            "tves" -> R.drawable.ic_tves
            "telesur" -> R.drawable.ic_telesur
            "tvfanb" -> R.drawable.ic_tvfanb
            "vive" -> R.drawable.ic_vive
            else -> R.drawable.ic_app
        }
    }
}
