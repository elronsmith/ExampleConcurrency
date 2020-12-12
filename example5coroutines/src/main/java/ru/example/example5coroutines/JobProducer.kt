package ru.example.example5coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel

/**
 * Принимает работу и раздаёт её своим работникам
 */
class JobProducer {
    private val channel = Channel<IJob>()
    private val coroutineJobList = ArrayList<Job>()

    val workerList = ArrayList<Worker>()

    fun startWorkers() {
        workerList.forEach {
            coroutineJobList.add(it.obtainJob())
        }
    }

    suspend fun stopWorkers() {
        coroutineJobList.forEach { job ->
            job.cancelAndJoin()
        }
    }

    suspend fun addJob(job: IJob) {
        channel.send(job)
    }
    private fun obtainWorker(): Worker = Worker(workerList.size+1)

    /**
     * Работа которую нужно выполнить в другом потоке
     */
    abstract class IJob(val no: Int) {
        abstract suspend fun work()
    }

    /**
     * Работник который работает в своём потоке, ждёт работу и выполняет её
     */
    inner class Worker(val no: Int) {
        var counter = 0

        suspend fun run() {
            println("worker #$no start on thread ${Thread.currentThread().name}")
            while (true) {
                val job = channel.receive()
                job.work()
                counter++
            }
            println("worker end")
        }
    }

    class Builder() {
        private var count: Int = 0

        fun withWorkersCount(count: Int): Builder {
            this.count = count
            return this
        }

        fun build(): JobProducer {
            val result = JobProducer()

            for (i in 1..count) {
                result.workerList.add(result.obtainWorker())
            }

            return result
        }
    }
}

fun JobProducer.Worker.obtainJob(): Job {
    return CoroutineScope(Dispatchers.IO).launch {
        this@obtainJob.run()
    }
}
