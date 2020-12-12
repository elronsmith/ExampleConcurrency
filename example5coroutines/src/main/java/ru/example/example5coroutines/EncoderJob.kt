package ru.example.example5coroutines

/**
 * Выполняет работу в другом потоке, например кодирование аудио/видео
 */
class EncoderJob(no: Int, val logger: JobLogger) : JobProducer.IJob(no) {
    override suspend fun work() {
        println("job start #$no on thread ${Thread.currentThread().name}")
        // logger
        logger.postResult("job #$no OK")
        println("job end #$no")
    }
}