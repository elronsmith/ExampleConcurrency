package ru.example.example2coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * 2 потока изменяют одну переменную
 * поочерёдное выполнение не гарантировано
 */
fun main(args: Array<String>) = runBlocking {
    println("main start")
    val count = 1000
    var counter = 0
    val mutex = Mutex()

    val incrementerJob = launch(Dispatchers.IO, CoroutineStart.LAZY) {
        println("incrementerJob start")

        for (i in 1..count) {
            mutex.withLock {
                counter++
                println("incrementerJob counter=$counter")
            }
            delay(1)
        }

        println("incrementerJob end")
    }
    val decrementerJob = launch(Dispatchers.IO, CoroutineStart.LAZY) {
        println("decrementerJob start")

        for (i in 1..count) {
            mutex.withLock {
                counter--
                println("decrementerJob counter=$counter")
            }
            delay(1)
        }

        println("decrementerJob end")
    }

    println("main start incrementerJob")
    incrementerJob.start()
    println("main start decrementerJob")
    decrementerJob.start()

    delay(1000)
    println("main counter=$counter")
    println("main end")
}