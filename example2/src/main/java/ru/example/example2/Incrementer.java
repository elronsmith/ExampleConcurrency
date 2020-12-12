package ru.example.example2;

public class Incrementer implements Runnable {
    private Container container;
    private Integer count = 0;

    Incrementer(Container container, int count) {
        this.count = count;
        this.container = container;
    }

    @Override
    public void run() {
        System.out.println("Incrementer start");
        for (int i = 0; i < count; i++) {
            synchronized (container) {
                container.value++;
                System.out.println("Incrementer value=" + container.value);
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Incrementer end");
    }
}
