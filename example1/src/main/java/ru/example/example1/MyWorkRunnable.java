package ru.example.example1;

public class MyWorkRunnable implements Runnable {
    private final Container container;

    public MyWorkRunnable(Container container) {
        this.container = container;
    }

    @Override
    public void run() {
        synchronized (this) {
            System.out.println("thread start");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            container.result = "r" + container.param*2;
            System.out.println("thread result=" + container.result);
            System.out.println("thread notify");
            notify();
            System.out.println("thread end");
        }
    }
}
