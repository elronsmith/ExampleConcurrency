package ru.example.example1coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * В другом потоке выполнить работу и передать результат.
 * В главном потоке получить результат и вывести его на экран.
 */
fun main(args: Array<String>) = runBlocking<Unit> {
    println("main start")
    val param = 1
    var result = ""

    val deffered = async(context = Dispatchers.IO) {
        println("thread ${Thread.currentThread().name} start")

        delay(1000)
        result = "r$param"

        println("thread ${Thread.currentThread().name} end")
    }

    println("main wait")
    deffered.await()
    println("main result=$result")

    println("main end")
}