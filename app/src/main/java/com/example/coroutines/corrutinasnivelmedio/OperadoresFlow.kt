package com.example.coroutines.corrutinasnivelmedio

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach



suspend fun main() {
    flowOf("A", "B", "C", "D","F","G")
        .flatMapMerge { flowFrom(it) }
        //.flatMapConcat { flowFrom(it) }
        .collect { println("$it") }
}

fun flowFrom(elem: String) = flowOf(1, 2, 3)
    .onEach { delay(1000) }
    .map { "${elem} - ${it}" }