package ru.example.example5;

/**
 * Работа, в которой выполняется кодирование аудио-видео.
 * Результат работы передаёт Логеру
 */
public class EncoderJob extends JobProducer.IJob {
    private final JobLogger logger;

    protected EncoderJob(int no, JobLogger logger) {
        super(no);
        this.logger = logger;
    }

    @Override
    public void work() {
        System.out.println("job-" + no + " start in thread " + Thread.currentThread().getName());
        logger.postResult("job-" + no + " work ok");
        System.out.println("job end");
    }
}
