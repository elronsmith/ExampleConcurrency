package ru.example.example3;

/**
 * производство-потребление,
 * 1-поток производит товар,
 * 2-поток потребляет товар,
 * 3-поток потребляет товар,
 * склад с ограниченным размером
 */
public class Program2 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");
        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer1 = new Consumer(store);
        Consumer consumer2 = new Consumer(store);

        Thread thread1 = new Thread(producer);
        Thread thread2 = new Thread(consumer1);
        Thread thread3 = new Thread(consumer2);
        thread1.setDaemon(true);
        thread2.setDaemon(true);
        thread3.setDaemon(true);

        System.out.println("main start threads");
        thread1.start();
        thread2.start();
        thread3.start();

        Thread.sleep(1000);

        System.out.println("main producedTotal=" + store.produced);
        System.out.println("main consumedTotal=" + store.consumed);
        System.out.println("main consumed1=" + consumer1.consumed);
        System.out.println("main consumed2=" + consumer2.consumed);
        System.out.println("main store.items=" + store.itemsCount);
        System.out.println("main end");
    }
}
