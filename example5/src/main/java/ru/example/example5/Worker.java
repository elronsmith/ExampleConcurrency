package ru.example.example5;

/**
 * Работяга - получает работу и выполняет её в своём потоке
 */
public class Worker implements Runnable {
    public final int no;
    private final JobProducer jobProducer;

    public int counter = 0;

    public Worker(int no, JobProducer jobProducer) {
        this.no = no;
        this.jobProducer = jobProducer;
    }

    @Override
    public void run() {
        System.out.println("Thread-" + no + " start");
        while (true) {
            JobProducer.IJob job = jobProducer.obtainJob();
            System.out.println("Thread#" + no + " get job-" + job.no);
            job.work();
            counter++;
        }
    }
}
