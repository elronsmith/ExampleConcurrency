package ru.example.example3coroutines

import kotlinx.coroutines.*

/**
 * производство-потребление,
 * 1-поток производит товар,
 * 2-поток потребляет товар,
 * склад с ограниченным размером
 */
fun main(args: Array<String>) = runBlocking {
    println("main start")
    val store = Store()
    var produced = 0
    var consumed = 0

    val producer = launch(Dispatchers.IO, CoroutineStart.LAZY) {
        println("Producer start, thread=${Thread.currentThread().name}")

        for (i in 1..50) {
            store.addItem()
            produced++
        }

        println("Producer end")
    }

    val consumer = launch(Dispatchers.IO, CoroutineStart.LAZY) {
        println("Consumer start, thread=${Thread.currentThread().name}")

        for (i in 1..50) {
            store.getItem()
            consumed++
        }

        println("Consumer end")
    }

    producer.start()
    consumer.start()

    delay(2000)

    println("main produced=$produced")
    println("main consumed=$consumed")
    println("main store.produced=${store.produced}")
    println("main store.consumed=${store.consumed}")
    println("main store.items=${store.itemsCount}")
    println("main end")
}