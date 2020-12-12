package ru.example.example5coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

/**
 * Принимает логи в своём потоке
 */
class JobLogger {
    private val channel = Channel<String>()
    var job: Job? = null

    fun start() {
        job = CoroutineScope(newSingleThreadContext("LoggerThread")).launch() {
            while (true) {
                val message = channel.receive()
                println("Logger message: $message from thread=${Thread.currentThread().name}")
            }
        }
    }

    suspend fun stop() {
        job?.cancelAndJoin()
    }

    suspend fun postResult(resultMessage: String) {
        channel.send(resultMessage)
    }
}