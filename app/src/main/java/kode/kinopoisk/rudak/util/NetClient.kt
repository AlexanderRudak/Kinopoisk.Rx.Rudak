package kode.kinopoisk.rudak.util

import android.os.Handler
import android.os.Message
import java.io.IOException
import okhttp3.OkHttpClient
import okhttp3.Request


class NetClient(internal var callback:
                NetClient.NetResponseCallback) {
    var jsonLoaded: String = ""

    interface NetResponseCallback {
        fun onJsonLoaded(json: String)
    }

    fun fetchResponse(iUrl: String?) {
        if (iUrl == null) {
            return
        }

        val handler = object : Handler() {
            override fun handleMessage(message: Message) {
                jsonLoaded = message.obj as String
                if (callback != null)
                    try {
                        callback.onJsonLoaded(jsonLoaded)
                    }catch (e: Exception) {
                        e.printStackTrace()
                    }

            }
        }

        val thread = object : Thread() {

            override fun run() {
                val strJsonFilms: String
                try {
                    val str = get(iUrl)
                    if (str != null) {
                        val message = handler.obtainMessage()
                        message.obj = str
                        handler.sendMessage(message)
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }
        //thread.setPriority(3);
        thread.start()
    }


    @Throws(IOException::class)
    operator fun get(url: String): String? {
        val client = OkHttpClient()
        val request = Request.Builder().url(url).get().build()
        try {
            client.newCall(request).execute().use { response -> return response.body().string() }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }
}
