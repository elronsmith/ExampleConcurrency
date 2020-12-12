package ru.example.example5;

import java.util.LinkedList;
import java.util.List;

/**
 * Логировщик выполненных работ.
 * Принимает результат работы и выводит её в своём потоке
 */
public class JobLogger implements Runnable {
    public int counter = 0;
    private final List<String> messageList = new LinkedList<>();

    @Override
    public void run() {
        System.out.println("logger start in thread " + Thread.currentThread().getName());
        String result = null;
        while (true) {
            synchronized (this) {
                if (messageList.size() == 0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                result = messageList.remove(0);
            }
            logResult(result);
        }
    }

    private void logResult(String result) {
        System.out.println("logger in thread " + Thread.currentThread().getName() + " result=" + result);
    }

    public void postResult(String result) {
        System.out.println("logger postResult");
        synchronized (this) {
            messageList.add(result);
            notify();
            counter++;
        }
    }
}
