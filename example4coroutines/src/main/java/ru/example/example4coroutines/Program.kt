package ru.example.example4coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import java.util.*
import kotlin.collections.ArrayList

/**
 * Скачивание файлов по ссылке.
 * Кол-во потоков и кол-во ссылок заранее указано.
 */
val THREADS_COUNT = 3
val URLS_COUNT = 20
fun main(args: Array<String>) = runBlocking {
    println("main start")
    val channel = Channel<String>()
    val urlList = obtainUrls(URLS_COUNT)
    val downloaderList = obtainDownloaders(THREADS_COUNT, channel)
    val jobsList = obtainJobs(downloaderList)

    jobsList.forEach { it.start() }

    urlList.forEach { channel.send(it) }

    delay(1000)

    println("main end")
    println("main urls=${urlList.size}")
    downloaderList.forEach {
        println("main downloader.counter=${it.counter}")
    }
}

class Downloader(val id: Int, val channel: Channel<String>) {
    var counter = 0

    suspend fun run() {
        while (true) {
            val url = channel.receive()
            download(url)
            yield()
        }
    }

    suspend private fun download(url: String) {
        println("#$id download start $url")
        counter++
//        Thread.sleep(100)
        println("#$id download end $url")
    }
}

private fun obtainDownloaders(count: Int, channel: Channel<String>): List<Downloader> {
    val result = ArrayList<Downloader>()

    for (i in 1..count) {
        result.add(Downloader(i, channel))
    }

    return result
}

private fun obtainJobs(list: List<Downloader>): List<Job> {
    val result = ArrayList<Job>()

    list.forEach {
        result.add(it.obtainJob())
    }

    return result
}

fun Downloader.obtainJob(): Job {
    return CoroutineScope(Dispatchers.IO).launch(start = CoroutineStart.LAZY) {
        println("Thread start ${Thread.currentThread().name}")
        this@obtainJob.run()
    }
}

private fun obtainUrls(count: Int): List<String> {
    val result = LinkedList<String>()

    for (i in 1..count)
        result.add("url-$i")

    return result
}