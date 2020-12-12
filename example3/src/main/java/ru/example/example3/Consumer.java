package ru.example.example3;

public class Consumer implements Runnable {
    private final Store store;
    public int consumed = 0;

    public Consumer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        System.out.println("Consumer start");
        while (true) {
            store.getItem();
            consumed++;
        }
//        System.out.println("Consumer end");
    }
}
