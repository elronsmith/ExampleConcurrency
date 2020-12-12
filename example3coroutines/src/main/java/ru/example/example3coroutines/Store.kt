package ru.example.example3coroutines

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.yield
import java.util.concurrent.atomic.AtomicInteger

class Store {
    private val mutex = Mutex()
    private val itemsMax = 3

    var itemsCount = AtomicInteger(0)
    var produced = 0
    var consumed = 0

    suspend fun addItem() {
        println("Store addItem")
        while (itemsCount.get() >= itemsMax) {
            // TODO текущая реализация работает, но она не идеальна
            //   т.к. поток приостанавливается на 1 квант времени
            //   поток должен засыпать и просыпаться когда его тригерят другие потоки
            //   это сделано в Java-версии
            yield()
        }

        mutex.withLock {
            itemsCount.incrementAndGet()
            produced++
            println("Store addItem itemsCount=${itemsCount.get()}")
        }
        yield()
        println("Store addItem end")
    }

    suspend fun getItem() {
        println("Store getItem")
        while (itemsCount.get() < 1){
            yield()
        }

        mutex.withLock {
            itemsCount.decrementAndGet()
            consumed++
            println("Store getItem itemsCount=${itemsCount.get()}")
        }
        yield()
        println("Store getItem end")
    }
}