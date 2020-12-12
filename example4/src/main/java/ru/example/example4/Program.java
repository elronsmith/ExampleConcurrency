package ru.example.example4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Скачивание файлов по ссылке.
 * Кол-во потоков и кол-во ссылок заранее указано.
 */
public class Program {
    public static final int THREADS_COUNT = 3;
    public static final int URLS_COUNT = 20;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");
        List<String> urlList = obtainUrls(URLS_COUNT);

        List<Downloader> downloaderList = obtainThreads(THREADS_COUNT, urlList);
        List<Thread> threadList = new ArrayList<>();

        for (Downloader downloader : downloaderList) {
            threadList.add(new Thread(downloader));
        }
        for (Thread thread : threadList) {
            thread.start();
        }

        while (true) {
            int counter = 0;
            for (int i = 0; i < downloaderList.size(); i++) {
                if (downloaderList.get(i).state == Downloader.STATE_FINISHED)
                    counter++;
            }

            if (counter == downloaderList.size())
                break;
            else {
                Thread.sleep(100);
            }
        }

        System.out.println("main threads count=" + downloaderList.size());
        System.out.println("main urls count=" + urlList.size());
        for (Downloader downloader : downloaderList) {
            System.out.println("main thread#" + downloader.no + " counter=" + downloader.counter);
        }
        System.out.println("main end");
    }

    private static List<Downloader> obtainThreads(int count, List<String> urlList) {
        List<Downloader> result = new ArrayList<Downloader>();

        for (int i = 0; i < count; i++) {
            result.add(new Downloader(i+1, urlList));
        }

        return result;
    }

    private static List<String> obtainUrls(int count) {
        List<String> result = Collections.synchronizedList(new LinkedList<String>());
//        List<String> result = new LinkedList<String>();

        for (int i = 0; i < count; i++) {
            result.add("url-" + (i+1));
        }

        return result;
    }
}
