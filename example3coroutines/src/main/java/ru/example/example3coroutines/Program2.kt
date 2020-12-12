package ru.example.example3coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

/**
 * производство-потребление,
 * 1-поток производит товар,
 * 2-поток потребляет товар,
 * 3-поток потребляет товар,
 * склад с ограниченным размером отсутствует
 */
fun main(args: Array<String>) = runBlocking {
    println("main start")
    val channel = Channel<Int>()
    var produced = 0
    var consumed1 = 0
    var consumed2 = 0

    val producer = launch(Dispatchers.IO, CoroutineStart.LAZY) {
        println("Producer start, thread=${Thread.currentThread().name}")

        for (i in 1..50) {
            println("Producer send $i")
            channel.send(i)
            produced++
        }

        println("Producer end")
    }

    val consumer1 = launch(Dispatchers.IO, CoroutineStart.LAZY) {
        println("Consumer 1 start, thread=${Thread.currentThread().name}")

        while (true) {
            val value = channel.receive()
            println("Consumer 1 receive $value")
            consumed1++
        }

        println("Consumer 1 end")
    }
    val consumer2 = launch(Dispatchers.IO, CoroutineStart.LAZY) {
        println("Consumer 2 start, thread=${Thread.currentThread().name}")

        while (true) {
            val value = channel.receive()
            println("Consumer 2 receive $value")
            consumed2++
        }

        println("Consumer 2 end")
    }

    producer.start()
    consumer1.start()
    consumer2.start()

    delay(2000)

    consumer1.cancelAndJoin()
    consumer2.cancelAndJoin()

    println("main end")
    println("main produced=$produced")
    println("main consumed1=$consumed1")
    println("main consumed2=$consumed2")
}
