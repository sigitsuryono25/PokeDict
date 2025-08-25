package com.surelabsid.core.utils.helpers

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream

object FileDownloader {

    private val client = OkHttpClient()

    suspend fun downloadImage(
        context: Context,
        url: String,
        fileName: String,
        onDone: (Uri?, String?) -> Unit
    ) = withContext(Dispatchers.IO) {
        try {
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            if (!response.isSuccessful) {
                withContext(Dispatchers.Main) { onDone(null, "Failed to download image") }
            } else {
                val dir = File(context.filesDir, "pokeImage")
                if (!dir.exists()) dir.mkdirs()

                val file = File(dir, fileName)
                response.body?.byteStream()?.use { input ->
                    FileOutputStream(file).use { output ->
                        input.copyTo(output)
                    }
                }

                val uri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    file
                )
                withContext(Dispatchers.Main) { onDone(uri, null) }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) { onDone(null, e.message) }
        }
    }

    fun shareToWhatsApp(context: Context, uri: Uri?, caption: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            if (uri != null) {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_TEXT, caption)
            } else {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, caption)
            }
            setPackage("com.whatsapp")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        context.startActivity(Intent.createChooser(intent, "Share to WhatsApp"))
    }
}