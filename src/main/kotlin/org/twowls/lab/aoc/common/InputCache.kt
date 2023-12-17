package org.twowls.lab.aoc.common

import okhttp3.*
import java.io.File
import java.time.Duration
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.readText

private const val SESSION_COOKIE_NAME = "session"
private const val SESSION_COOKIE_DOMAIN = "adventofcode.com"
private const val SESSION_COOKIE_PATH = "/"
private const val SESSION_COOKIE_FILE = "session.local.txt"
private const val CACHE_DIR = "build/tmp/puzzle-input"
private const val CALL_TIMEOUT_MILLIS = 10_000L

fun cachedInput(year: Int, day: Int): File {
    val path = Path(CACHE_DIR, year.toString()).toAbsolutePath()
    path.createDirectories()

    val cacheFile = path.resolve("$day.txt").toFile()
    if (!cacheFile.exists() || cacheFile.length() == 0L) {
        println("[cache] downloading puzzle input for year $year, day $day...")
        fetchInput(year, day, cacheFile)
    }

    return cacheFile
}

internal fun fetchInput(year: Int, day: Int, cache: File) {
    val sessionId = Path(SESSION_COOKIE_FILE).readText().trim()
    check(sessionId.isNotEmpty()) { "Session id is empty" }

    val client =
        OkHttpClient.Builder()
            .callTimeout(Duration.ofMillis(CALL_TIMEOUT_MILLIS))
            .cookieJar(object : CookieJar {
                override fun loadForRequest(url: HttpUrl): List<Cookie> = listOf(
                    Cookie.Builder()
                        .secure()
                        .httpOnly()
                        .name(SESSION_COOKIE_NAME)
                        .domain(SESSION_COOKIE_DOMAIN)
                        .path(SESSION_COOKIE_PATH)
                        .value(sessionId).build()
                )

                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {}
            }).build()

    val request = Request.Builder()
        .url("https://adventofcode.com/$year/day/$day/input")
        .build()

    client.newCall(request).execute().use { response ->
        if (response.code != 200)
            throw IllegalStateException("Server returned response code ${response.code}")
        else if (response.body == null)
            throw IllegalStateException("Server returned empty response")

        cache.outputStream().use { os ->
            val bytesCopied = response.body!!.byteStream().copyTo(os)
            println("[cache] saved puzzle input to $cache, $bytesCopied byte(s)")
        }
    }
}