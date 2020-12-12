package ru.example.example3;

/**
 * производство-потребление,
 * 1-поток производит товар,
 * 2-поток потребляет товар,
 * склад с ограниченным размером
 */
public class Program {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");
        Store store = new Store();
        Producer producer = new Producer(store);
        Consumer consumer = new Consumer(store);

        Thread thread1 = new Thread(producer);
        Thread thread2 = new Thread(consumer);
        thread1.setDaemon(true);
        thread2.setDaemon(true);

        System.out.println("main start threads");
        thread1.start();
        thread2.start();

        Thread.sleep(1000);

        System.out.println("main produced=" + store.produced);
        System.out.println("main consumed=" + store.consumed);
        System.out.println("main store.items=" + store.itemsCount);
        System.out.println("main end");
    }
}
