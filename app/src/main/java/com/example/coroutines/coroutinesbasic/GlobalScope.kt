package com.example.coroutines.coroutinesbasic

import com.example.coroutines.someTime
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    globalScope()

    readLine()
}

fun globalScope() {
    newTopic("Global Scope")
    GlobalScope.launch {
        startMessage()
        delay(someTime())
        println("Mi corrutina")
        endMessage()
    }
}

fun endMessage() {
    println("Corrutina -${Thread.currentThread().name}- finalizada")
}

fun startMessage() {
    println("comenzando corrutina -${Thread.currentThread().name}-")
}

fun newTopic(s: String) {
    println(s)
}
