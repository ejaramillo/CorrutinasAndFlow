package com.example.coroutines.coroutinesbasic

import com.example.coroutines.previousbases.multi
import com.example.coroutines.someTime
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    //cRunblocking()

    //cLaunch()

    //cAsync()

    //job()

    //deferred()

    //cProduce()

    preguntaDeLaClase()
    readLine()
}

fun preguntaDeLaClase() = runBlocking{

    val result = async {
        multi(2,4)
    }.await()
    println("Valor: $result")

    /*val deferred = async {
        multi(2,5)
    }
    println("Valor de deferred: ${deferred.await()}")*/
}

fun cProduce() = runBlocking{
    val names = produceNames()
    names.consumeEach {
        println(it)
    }
}

fun CoroutineScope.produceNames(): ReceiveChannel<String> = produce {
    (1..5).forEach{
        send("name $it")
    }
}

fun deferred() {
    runBlocking {
        println("Deferred")
        val deferred = async {
            startMessage()
            delay(someTime())
            println("deferred ...")
            endMessage()
            multi(2,3)
        }
        println("Deferred: $deferred")
        //cabe señalar que la función println no se ejecutará hasta que termine
        // de evaluarse la función await(), ya que, await() es una función suspendida
        //public suspend fun await(): T
        println("valor del Deferred.await: ${deferred.await()}")

        // otra forma de utilizar async con await
        val result = async {
            multi(3,3)
        }.await()

        println("Result : $result")
    }
}

fun job() {
    runBlocking {
        val job = launch {
            startMessage()
            delay(2_100)
            println("job...")
            endMessage()
        }
        println("job---- $job")
        println("isActive----${job.isActive}")
        println("isCancel----${job.isCancelled}")
        println("isComplete----${job.isCompleted}")

        delay(someTime())
        println("Tarea cancelada o interrumpida")
        job.cancel()

        println("isActive----${job.isActive}")
        println("isCancel----${job.isCancelled}")
        println("isComplete----${job.isCompleted}")
    }
}

fun cAsync() {
    runBlocking {
        val result = async {
            startMessage()
            delay(someTime())
            println("Async...")
            endMessage()
            1
        }
        println("result: ${result.await()}")
    }
}

fun cLaunch() {
    runBlocking {
        launch {
            startMessage()
            delay(someTime())
            println("launch...")
            endMessage()
        }
    }
}


fun cRunblocking() {
    runBlocking {
        startMessage()
        delay(someTime())
        println("runBlocking...")
        endMessage()
    }
}
