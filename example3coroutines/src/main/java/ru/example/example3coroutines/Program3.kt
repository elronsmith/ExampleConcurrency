package ru.example.example3coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.concurrent.atomic.AtomicInteger

/**
 * производство-потребление,
 * 1-поток производит товар,
 * 2-поток потребляет товар,
 * 3-поток потребляет товар,
 * склад с ограниченным размером
 */
fun main(args: Array<String>) = runBlocking {
    println("main start")
    var produced = 0
    var consumed1 = 0
    var consumed2 = 0
    val store = Store3()

    val producer = launch(Dispatchers.IO, CoroutineStart.LAZY) {
        println("Producer start, thread=${Thread.currentThread().name}")

        for (i in 1..50) {
            println("Producer send $i")
            store.addItem()
            produced++
        }

        println("Producer end")
    }
    val consumer1 = launch(Dispatchers.IO, CoroutineStart.LAZY) {
        println("Consumer 1 start, thread=${Thread.currentThread().name}")

        while (true) {
            store.getItem("Consumer 1")
            println("Consumer 1 receive")
            consumed1++
        }

        println("Consumer 1 end")
    }
    val consumer2 = launch(Dispatchers.IO, CoroutineStart.LAZY) {
        println("Consumer 2 start, thread=${Thread.currentThread().name}")

        while (true) {
            store.getItem("Consumer 2")
            println("Consumer 2 receive")
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
    println("main store.items=${store.itemsCount}")
}

class Store3 {
    private val mutex = Mutex()
    private val itemsMax = 3

    var itemsCount = AtomicInteger(0)

    suspend fun addItem() {
        println("Store addItem")
        while (itemsCount.get() >= itemsMax) {
            yield()
        }
        val count = itemsCount.incrementAndGet()
        println("Store addItem $count")
    }

    suspend fun getItem(from: String) {
        println("Store getItem")

        mutex.withLock {
            while (itemsCount.get() <= 0) {
                yield()
            }
            val value = itemsCount.getAndDecrement()
            println("Store getItem $value from $from")
        }
        yield()
    }

}