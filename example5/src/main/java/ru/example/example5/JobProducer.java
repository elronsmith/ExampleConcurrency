package ru.example.example5;

import java.util.LinkedList;
import java.util.List;

/**
 * Продюсер, раздаёт работу, если она есть канечно же
 */
public class JobProducer {
    private final List<IJob> jobList = new LinkedList<>();

    public synchronized void addJob(IJob job) {
        jobList.add(job);
        notifyAll();
    }

    public synchronized IJob obtainJob() {
        while (jobList.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return jobList.remove(0);
    }

    /**
     * Абстрактная работа
     */
    public static abstract class IJob {
        public final int no;

        protected IJob(int no) {
            this.no = no;
        }

        abstract void work();
    }
}
