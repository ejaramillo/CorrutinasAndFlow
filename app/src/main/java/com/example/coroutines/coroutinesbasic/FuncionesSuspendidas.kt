package com.example.coroutines.coroutinesbasic

import com.example.coroutines.someTime
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {
    suspendFunction()
}

fun suspendFunction() {
    println("Suspend")
    Thread.sleep(someTime())
    GlobalScope.launch {
        delay(someTime())
    }
}
