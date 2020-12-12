package ru.example.example2;

public class Decrementer implements Runnable {
    private Container container;
    private int count;

    Decrementer(Container container, int count) {
        this.count = count;
        this.container = container;
    }

    @Override
    public void run() {
        System.out.println("Decrementer start");
        for (int i = 0; i < count; i++) {
            synchronized (container) {
                container.value--;
                System.out.println("Decrementer value=" + container.value);
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Decrementer end");
    }
}
