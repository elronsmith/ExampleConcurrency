package ru.example.example3;

public class Producer implements Runnable {
    private final Store store;
    public int produced = 0;

    public Producer(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        System.out.println("Producer start");
        for (int i = 0; i < 50; i++) {
            store.addItem();
            produced++;
        }
        System.out.println("Producer end");
    }
}
