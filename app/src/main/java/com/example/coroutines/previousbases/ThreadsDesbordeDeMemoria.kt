package com.example.coroutines

import kotlin.concurrent.thread
import kotlin.random.Random

fun main() {
    threadsOutMemoryRange()
}

fun threadsOutMemoryRange() {
    (1..1_000_000).forEach {
        thread {
            Thread.sleep(someTimeOutMemoryRange())
            println("#")
        }
    }
}

fun someTimeOutMemoryRange(): Long = Random.nextLong(500, 2_000)
