package ru.example.example1;

/**
 * В другом потоке выполнить работу и передать результат.
 * В главном потоке получить результат и вывести его на экран.
 *
 * PS: примеры в этом проекте не претендуют на то что это единственно верное решение
 */
public class Program {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("main start");
        Container container = new Container();
        MyWorkRunnable myWorkRunnable = new MyWorkRunnable(container);

        new Thread(myWorkRunnable).start();
        System.out.println("main wait");
        synchronized (myWorkRunnable) {
            myWorkRunnable.wait();
        }

        System.out.println("main result=" + container.result);
        System.out.println("main end");
    }
}
