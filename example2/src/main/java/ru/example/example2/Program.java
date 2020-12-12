package ru.example.example2;

/**
 * 2 потока изменяют одну переменную
 * поочерёдное выполнение не гарантировано
 */
public class Program {
    public static void main(String[] args) throws InterruptedException {
        int count = 1000;
        Container container = new Container();
        System.out.println("main start value=" + container.value);
        Thread thread1 = new Thread(new Decrementer(container, count));
        Thread thread2 = new Thread(new Incrementer(container, count));

        System.out.println("main start threads");
        thread1.start();
        thread2.start();

        Thread.sleep(1000);
        System.out.println("main value=" + container.value);
        System.out.println("main end");
    }
}
