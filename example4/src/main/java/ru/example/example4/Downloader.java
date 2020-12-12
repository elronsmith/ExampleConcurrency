package ru.example.example4;

import java.util.List;

public class Downloader implements Runnable {
    public static final int STATE_IDLE = 0;
    public static final int STATE_STARTED = 1;
    public static final int STATE_WORKING = 2;
    public static final int STATE_FINISHED = 3;

    public final int no;
    private final List<String> list;
    public int counter = 0;
    public int state = STATE_IDLE;
    public int time = 10;

    public Downloader(int no, List<String> urlList) {
        this.no = no;
        this.list = urlList;
    }

    @Override
    public void run() {
        System.out.println("Downloader #" + no + " start");
        state = STATE_STARTED;

        while (true) {
            state = STATE_WORKING;
            String url = null;
            if (list.size() == 0) break;
            url = list.remove(0);
            download(url);
        }

        state = STATE_FINISHED;
        System.out.println("Downloader #" + no + " end");
    }

    private void download(String url) {
        System.out.println("Downloader #" + no + " start " + url);
//        try {
//            Thread.sleep(time);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        counter++;
        System.out.println("Downloader #" + no + " end " + url);
    }
}
