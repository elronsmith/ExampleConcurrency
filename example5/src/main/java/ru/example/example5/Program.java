package ru.example.example5;

import java.util.ArrayList;
import java.util.List;

/**
 * несколько потоков конкурируют за захват коллекции
 *   для извлечения объекта и работы над ним, пример кодирование аудио-видео
 */
public class Program {
    public static final int THREADS_COUNT = 3;
    public static final int JOBS_COUNT = 30;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");

        JobLogger logger = new JobLogger();
        Thread loggerThread = new Thread(logger, "ThreadLogger");
        loggerThread.setDaemon(true);
        loggerThread.start();

        JobProducer jobProducer = new JobProducer();
        List<Worker> workerList = obtainEncoderList(jobProducer, THREADS_COUNT);
        List<Thread> threadList = new ArrayList<>();
        for (Worker worker : workerList)
            threadList.add(new Thread(worker, "Thread-" + worker.no));

        for (Thread thread : threadList) {
            thread.setDaemon(true);
            thread.start();
        }

        // создаём работу
        for (int i = 0; i < JOBS_COUNT; i++) {
            jobProducer.addJob(new EncoderJob(i+1, logger));
        }

        Thread.sleep(500);

        System.out.println("main end");
        System.out.println("main threads=" + THREADS_COUNT);
        System.out.println("main jobs=" + JOBS_COUNT);
        for (Worker worker : workerList) {
            System.out.println("main worker-" + worker.no + " counter=" + worker.counter);
        }
        System.out.println("main end");
    }

    private static List<Worker> obtainEncoderList(JobProducer jobProducer, int count) {
        List<Worker> result = new ArrayList<Worker>();

        for (int i = 0; i < count; i++) {
            result.add(new Worker(i+1, jobProducer));
        }

        return result;
    }
}
