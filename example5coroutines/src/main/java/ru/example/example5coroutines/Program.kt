package ru.example.example5coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

/**
 * несколько потоков конкурируют за захват коллекции
 *   для извлечения объекта и работы над ним, пример кодирование аудио-видео
 */
val WORKERS_COUNT = 3
val JOBS_COUNT = 30
fun main(args: Array<String>) = runBlocking {
    println("main start")
    val logger = JobLogger()
    val jobProducer = JobProducer.Builder().withWorkersCount(WORKERS_COUNT).build()

    logger.start()
    jobProducer.startWorkers()

    for (i in 1..JOBS_COUNT) {
        jobProducer.addJob(EncoderJob(i, logger))
    }

    delay(1000)

    jobProducer.stopWorkers()
    logger.stop()

    println("main end")
    println("main jobs=$JOBS_COUNT")
    jobProducer.workerList.forEach { worker ->
        println("main worker #${worker.no} counter=${worker.counter}")
    }
}