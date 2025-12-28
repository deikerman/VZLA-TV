package com.example.vzlatv

data class Channel(
    val id: String,
    val name: String,
    val logoUrl: String,      // URL del icono del canal
    val primaryStreamUrl: String, // URL principal (IPTV)
    val fallbackStreamUrl: String // URL de respaldo (red)
)