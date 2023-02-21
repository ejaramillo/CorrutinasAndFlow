package com.example.coroutines

import kotlin.concurrent.thread
import kotlin.random.Random

fun main() {
    threadsLambda()
}

fun threadsLambda() {
    multiThreadLambda(2, 3){
        println("salida multithreadLambda: ${it}")
    }
}

fun multiThreadLambda(x: Int, y: Int, callback:(input:Int)->Unit){
    var result = 0
    thread {
        Thread.sleep(someTimeLambda())
        result = x * y
        callback(result)
    }

    println("final funció multiThreadLambda------")
}

fun someTimeLambda(): Long = Random.nextLong(500, 2_000)
