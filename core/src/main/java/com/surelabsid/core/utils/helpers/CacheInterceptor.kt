package com.surelabsid.core.utils.helpers
import android.content.Context
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.Response
import java.io.File

class CacheInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val path = request.url.encodedPath
        val imageExtensions = listOf(".jpg", ".jpeg", ".png", ".webp", ".gif", ".bmp", ".svg")
        val isImageRequest = imageExtensions.any { path.endsWith(it, ignoreCase = true) }
        if (request.method.equals("GET", true) && isImageRequest) {
            request = if (!NetworkHelper.isNetworkAvailable(context)) {
                request.newBuilder()
                    .header("Cache-Control", "public, max-age=5")
                    .build()
            } else {
                request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=86400")
                    .build()
            }
        }
        return chain.proceed(request)
    }
}


object CacheControl {
    fun setCache(context: Context): Cache {
        val cacheSize = (5 * 1024 * 1024).toLong()
        return Cache(File(context.cacheDir, "http_cache"), cacheSize)

    }
}

