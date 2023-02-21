package com.example.coroutines.coroutinesadvance

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

val countries = listOf("Santader", "CDMX", "Lima", "Buenos Aires", "Alicante")

fun main() {

    //basicChannel()

    //cerrarChannel()

    //produceChannel()

    //pipelines()

    bufferChannel()
}

fun bufferChannel() {
    runBlocking {
        val time = System.currentTimeMillis()
        val channel = Channel<String>()
        launch {
            countries.forEach {
                delay(100)
                channel.send(it)
            }
            channel.close()
        }

        launch {
            delay(1_000)
            channel.consumeEach {
                println(it)
            }
            println("Time: ${System.currentTimeMillis() - time} ms")
        }

        val bufferTime = System.currentTimeMillis()
        val bufferChannel = Channel<String>(capacity = 3)
        launch {
            countries.forEach {
                delay(100)
                bufferChannel.send(it)
            }
            bufferChannel.close()
        }

        launch {
            delay(1_000)
            bufferChannel.consumeEach {
                println(it)
            }
            println("B - Time: ${System.currentTimeMillis() - bufferTime} ms")
        }
    }
}

fun pipelines() {
    runBlocking {
        val citiesChannel = produceCities()
        val foodsChannel = produceFoods(citiesChannel)
        foodsChannel.consumeEach {
            println(it)
        }
        citiesChannel.cancel()
        foodsChannel.cancel()
        println("Todo está perfecto")
    }
}

fun CoroutineScope.produceFoods(cities: ReceiveChannel<String>): ReceiveChannel<String> = produce {
    for (city in cities) {
        val food = getFoodByCity(city)
        send("$food desde $city")
    }
}

suspend fun getFoodByCity(city: String): String {
    delay(300)
    return when(city){
        "Santader" -> "Arepa"
        "CDMX" -> "Taco"
        "Lima" -> "Ceviche"
        "Buenos Aires" -> "Milanesa"
        "Alicante" -> "Paella"
        else -> "Sin datos"
    }
}

fun produceChannel() {
    runBlocking {
        val names = produceCities()
        names.consumeEach {
            println(it)
        }
    }
}

fun CoroutineScope.produceCities(): ReceiveChannel<String> = produce {
    countries.forEach {
        send(it)
    }
}

fun cerrarChannel() {
    runBlocking {
        val channel = Channel<String>()
        launch {
            countries.forEach {
                channel.send(it)
                if (it.equals("Lima")) {
                    channel.close()
                    return@launch
                }
            }
        }
        // otra forma de consumir un channel
        while (!channel.isClosedForReceive) {
            println(channel.receive())
        }

        // otra forma de consumir un channel
        /*channel.consumeEach {
            println(it)
        }*/
    }

}

fun basicChannel() {
    runBlocking {
        val channel = Channel<String>()
        launch {
            countries.forEach {
                channel.send(it)
            }
        }

        // una forma de consumir un channel
        /*repeat(5){
            println(channel.receive())
        }*/

        // otra forma de consumir un channel
        for (value in channel) {
            println(value)
        }
    }
}
