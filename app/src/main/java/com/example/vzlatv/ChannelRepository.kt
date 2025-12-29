package com.example.vzlatv

class ChannelRepository {
    fun getChannels(): List<Channel> {
        // Lista actualizada con los 4 canales M3U proporcionados
        return listOf(
            Channel(
                "0",
                "VTV",
                "",
                "https://live.eu-north-1b.cf.dmcdn.net/sec2(xwyafn0RLGDOem-rBtsx5QmC9awPrtDq2487GgNfgAPjOsmgvY8EYxHHU7ilbJrrMZxW7SQj2nTUpjWqzHTuWKk9-kuYTyq2o-XCs4UGb8RF6hhgB6aehOOyxIyWlNoB)/dm/3/x930kre/d/live-720@60.m3u8",
                ""
            ),
            Channel(
                "1",
                "TVES",
                "",
                "http://38.41.8.1:8000/play/a03k",
                ""
            ),
            Channel(
                "2",
                "TELESUR",
                "",
                "http://38.41.8.1:8000/play/a03l",
                ""
            ),
            Channel(
                "3",
                "TVFANB",
                "",
                "http://38.41.8.1:8000/play/a03r",
                ""
            ),
            Channel(
                "4",
                "Vive TV",
                "",
                "http://38.41.8.1:8000/play/a03j",
                ""
            )
        )
    }
}