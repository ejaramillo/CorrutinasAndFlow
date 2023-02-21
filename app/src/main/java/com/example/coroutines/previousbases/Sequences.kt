package com.example.coroutines.previousbases

import com.example.coroutines.someTime
import kotlin.random.Random

fun main() {
    secuences()
}

fun secuences() {
    getDataBySeq()
        .forEach {
            println("${it}°")
        }

}

fun getDataBySeq(): Sequence<Float>{
    return sequence {
        (1..5).forEach {
            println("procesado datos ...")
            Thread.sleep(someTime())
            yield(20 +it + Random.nextFloat())
        }
    }
}
