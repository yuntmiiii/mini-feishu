package com.example.compose.jetchat

import android.app.Application
import com.example.compose.jetchat.net.NettyClient

class ChatApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        Thread {
            try {
                NettyClient.connect("192.168.1.100", 8085) // 自己换一下
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }.start()
    }

}