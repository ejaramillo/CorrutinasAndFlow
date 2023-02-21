package com.example.coroutines

import kotlin.concurrent.thread
import kotlin.random.Random

fun main() {
    threadsOutMemory()
}

fun threadsOutMemory() {
    println(multiThread(2, 3))
}

fun multiThread(x: Int, y: Int): Int {
    var result = 0
    thread {
        Thread.sleep(someTime())
        result = x * y
    }
    Thread.sleep(2_100)
    return result
}

fun someTime(): Long = Random.nextLong(500, 2_000)
