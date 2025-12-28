package com.example.vzlatv

class ChannelRepository {
    fun getChannels(): List<Channel> {
        // Aquí defines tu lista de canales
        return listOf(
            Channel(
                "1",
                "VTV",
                "",
                "https://live.eu-north-1a.cf.dmcdn.net/sec2(9dbeM8Jr0AKqSzScSSfVmV8iDvEnP9bWwheaOwX2bflIP1ffiObCNWdyMn3XT4SFEgEoLGl6jE4JTAFOL8YA6iSkh6uIiHS1TFbKkQkTYSS3FZKdiAb5VWFlkB74Sp2r)/dm/3/x930kre/d/live-480@60.m3u8",
                ""
            ),
            Channel(
                "2",
                "TVES",
                "",
                "http://38.41.8.1:8000/play/a03k",
                ""
            ),
            Channel(
                "3",
                "TELESUR",
                "",
                "http://38.41.8.1:8000/play/a03l",
                ""
            ),
            Channel(
                "4",
                "TVFANB",
                "",
                "http://38.41.8.1:8000/play/a03r",
                ""
            ),
            Channel(
                "5",
                "VIVE",
                "",
                "http://38.41.8.1:8000/play/a03j",
                ""
            )
        )
    }
}