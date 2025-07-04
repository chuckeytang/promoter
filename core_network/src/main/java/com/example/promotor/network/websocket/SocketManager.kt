package com.example.promotor.network.websocket

import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URISyntaxException

/**
 * Singleton object to manage the WebSocket connection using socket.io-client-java.
 */
object SocketManager {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var socket: Socket? = null

    enum class ConnectionState {
        DISCONNECTED, CONNECTING, CONNECTED, ERROR
    }

    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    fun initialize(serverUrl: String) {
        if (socket != null) return

        try {
            // IO.socket() automatically reuses the connection, so it's safe to call.
            val opts = IO.Options().apply {
                // Options like reconnection attempts, timeouts, etc. can be set here
                reconnection = true
            }
            socket = IO.socket(serverUrl, opts)
            setupConnectionStateListener()
        } catch (e: URISyntaxException) {
            e.printStackTrace()
            _connectionState.value = ConnectionState.ERROR
        }
    }

    private fun setupConnectionStateListener() {
        socket?.on(Socket.EVENT_CONNECT) { _connectionState.value = ConnectionState.CONNECTED }
        socket?.on(Socket.EVENT_DISCONNECT) { _connectionState.value = ConnectionState.DISCONNECTED }
        socket?.on(Socket.EVENT_CONNECT_ERROR) { _connectionState.value = ConnectionState.ERROR }
    }

    fun connect() {
        if (socket?.connected() == true) return

        scope.launch {
            _connectionState.value = ConnectionState.CONNECTING
            socket?.connect()
        }
    }

    fun disconnect() {
        scope.launch {
            socket?.disconnect()
        }
    }

    fun emit(event: String, data: JSONObject) {
        scope.launch {
            socket?.emit(event, data)
        }
    }

    /**
     * Listens for a specific event and returns a Flow of the data.
     * Note: The data from socket.io-client-java often comes as an Array of objects.
     * Here, we assume the first element is the data we want.
     */
    fun listen(event: String): Flow<Any> = callbackFlow {
        val listener = Emitter.Listener { args ->
            val data = args.getOrNull(0)
            if (data != null) {
                trySend(data)
            }
        }
        socket?.on(event, listener)

        awaitClose {
            socket?.off(event, listener)
        }
    }

    fun close() {
        disconnect()
        scope.cancel()
    }
}