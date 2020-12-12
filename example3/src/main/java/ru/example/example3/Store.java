package ru.example.example3;

public class Store {
    private final int itemsMax = 3;
    public int itemsCount = 0;
    public int produced = 0;
    public int consumed = 0;

    public synchronized void addItem() {
        System.out.println("addItem itemsCount=" + itemsCount);
        while (itemsCount >= itemsMax) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        itemsCount++;
        produced++;
        notify();
    }

    public synchronized void getItem() {
        System.out.println("getItem itemsCount=" + itemsCount);
        while (itemsCount < 1){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        itemsCount--;
        consumed++;
        notify();
        Thread.yield();
    }
}
