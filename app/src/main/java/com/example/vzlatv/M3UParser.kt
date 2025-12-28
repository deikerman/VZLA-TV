package com.example.vzlatv

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

data class M3UEntry(val title: String, val url: String)

class M3UParser {
    private val client = OkHttpClient()

    suspend fun fetchAndParse(url: String, timeoutMs: Long = 10000): List<M3UEntry> =
        withContext(Dispatchers.IO) {
            val req = Request.Builder().url(url).build()
            client.newCall(req).execute().use { resp ->
                if (!resp.isSuccessful) return@withContext emptyList()
                val body = resp.body?.string() ?: return@withContext emptyList()
                parse(body)
            }
        }

    private fun parse(content: String): List<M3UEntry> {
        val lines = content.lineSequence().map { it.trim() }.filter { it.isNotEmpty() }.toList()
        val result = mutableListOf<M3UEntry>()
        var i = 0
        while (i < lines.size) {
            val line = lines[i]
            if (line.startsWith("#EXTINF", ignoreCase = true)) {
                val title = line.substringAfter(',').trim()
                val next = (i + 1).takeIf { it < lines.size }?.let { lines[it] } ?: ""
                if (next.isNotEmpty() && !next.startsWith("#")) {
                    result.add(M3UEntry(title.ifEmpty { next }, next))
                    i += 2
                    continue
                }
            }
            if (!line.startsWith("#")) {
                result.add(M3UEntry(line, line))
            }
            i++
        }
        return result
    }
}
