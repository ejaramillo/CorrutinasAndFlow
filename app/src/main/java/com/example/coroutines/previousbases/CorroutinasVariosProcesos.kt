package com.example.coroutines

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread
import kotlin.random.Random

fun main() {
    coroutineVariosProcesos()
}

fun coroutineVariosProcesos() {
    runBlocking {
        (1..1_000_000)
            .forEach {
                launch {
                    delay(someTimeCoroutineVariosProcesos())
                    print("#")
                    println("corrutina hilo ---" + Thread.currentThread().name)
                }
            }
    }
}

fun someTimeCoroutineVariosProcesos(): Long = Random.nextLong(500, 2_000)
