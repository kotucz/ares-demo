package kotu.cz.ares.rest

import okhttp3.Interceptor
import java.io.IOException

/**
 * Interceptor to cache data and maintain it for a minute.
 *
 * If the same network request is sent within a minute,
 * the response is retrieved from cache.
 */
class ResponseCacheInterceptor : Interceptor {
    val cacheSeconds = 24 * 60 * 60 // 24h

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalResponse = chain.proceed(chain.request())
        return originalResponse.newBuilder()
            .header("Cache-Control", "public, max-age=" + cacheSeconds)
            .build()
    }
}