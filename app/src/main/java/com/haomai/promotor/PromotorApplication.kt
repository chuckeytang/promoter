package com.haomai.promotor

import android.app.Application
import com.haomai.promotor.network.websocket.SocketManager

class PromotorApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Initialize the SocketManager with your server's URL
        SocketManager.initialize("https://your.socket.server.com")
    }

    override fun onTerminate() {
        super.onTerminate()
        // Clean up the socket connection when the app is killed
        SocketManager.close()
    }
}