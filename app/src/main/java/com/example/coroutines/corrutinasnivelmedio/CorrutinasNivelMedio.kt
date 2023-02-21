package com.example.coroutines.corrutinasnivelmedio

import com.example.coroutines.coroutinesbasic.endMessage
import com.example.coroutines.coroutinesbasic.startMessage
import com.example.coroutines.someTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.math.log
import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
    //dispatchers()

    //nestedDispatcher()

    //changeWithContext()

    //basicFlow()

    //coldFlow()

    //cancelFlow()

    //flowOperatorMap()

    //flowOperatorFilter()

    //flowOperatorTransform()

    //flowOperatorTake()

    //flowOperatorListAndSingle()

    //flowOperatorFirstAndLast()

    //flowOperatorReduce()

    //flowOperatorFold()

    //flowOperatorBuffer()

    //flowOperatorConflate()

    //flowOperatorZipAndCombine()

    //flowOperatorFlatMapConcatAndFlatMapMerge()

    //flowExceptions()

    //flowExceptionsTwo()

    //completionsFlow()

    cancelableForFlow()

}

fun cancelableForFlow() {
    runBlocking {
        getDataByFlowStatic()
            .onCompletion {
                println("Ya no le interesa al usuario...")
            }
            .cancellable()
            .collect{
                if(it > 22.5f) cancel()
                println(it)
            }
    }
}

fun completionsFlow() {
    runBlocking {
        getCitiesFlow()
            .onCompletion {
                println("\nQuitar el progressBar...")
            }
            /*.collect{
                println(it)
            }*/

        getMatchResultsFlowTwo()
            .onCompletion {
                println("Mostrar las estadísticas...")
            }
            .catch {
                emit("Error: $this")
            }
            .collect{
                println(it)
            }
    }
}

fun flowExceptionsTwo() {
    runBlocking {
        getMatchResultsFlowTwo()
            .catch {
                emit("Error: $this")
            }
            .collect{
                println(it)
                if(!it.contains("-")) println("Notifica al programador")
            }
    }
}

fun getMatchResultsFlowTwo() : Flow<String>{
    return flow {
        var homeTeam = 0
        var awayTeam = 0
        (0..45).forEach {
            println("minuto: $it")
            delay(50)
            homeTeam += Random.nextInt(0, 21) / 20
            awayTeam += Random.nextInt(0, 21) / 20
            emit("$homeTeam - $awayTeam")

            if(homeTeam == 2 || awayTeam == 2) throw Exception("Habían acordado 1 y 1 :v")
        }
    }
}

fun flowExceptions() {
    runBlocking {
        try {
            getMatchResultsFlow()
                .collect{
                    println(it)
                    if (it.contains("2")) throw Exception("Habían acordado 1-1: v")
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

fun flowOperatorFlatMapConcatAndFlatMapMerge() {
    runBlocking {
        val milis = measureTimeMillis {
            getCitiesFlow()
                //.buffer()
                .flatMapMerge { city -> // Flow<Flow<TYPE>>
                    println("flatMapMerge. $city")
                    getDataToFlatFlow(city)
                }
                /*.flatMapConcat { city-> // Flow<Flow<TYPE>>
                getDataToFlatFlow(city)
            }*/
                //.map { setFormat(it) }
                .collect { cities ->
                    println(cities)
                }
        }
        println("milis :::: $milis")
    }
}

fun getDataToFlatFlow(city: String): Flow<Float> = flow {
    (1..3)
        .forEach {
            println("Temperatura de ayer en $city...")
            emit(Random.nextInt(10, 30).toFloat())

            println("Temperatura actual en $city")
            delay(500)
            emit(20 + it + Random.nextFloat())
        }
}

fun getCitiesFlow(): Flow<String> = flow {
    listOf("Santander", "CDMX", "Lima")
        .forEach { city ->
            delay(1000)
            println("\nConsultando Ciudad $city---")
            emit(city)
        }
}

fun flowOperatorZipAndCombine() {
    runBlocking {
        getDataByFlowStatic()
            .map { setFormat(it) }
            .combine(getMatchResultsFlow()) { degress, result ->
                "$result with $degress"
            }
            /*.zip(getMatchResultsFlow()){degress, result ->
                "$result with $degress"
            }*/
            .collect {
                println(it)
            }
    }
}

fun flowOperatorConflate() {
    runBlocking {
        val time = measureTimeMillis {
            getMatchResultsFlow()
                .conflate()
                .collect {
                    delay(100)
                    println(it)
                }
        }

        println("Time: $time")
    }
}

fun getMatchResultsFlow(): Flow<String> {
    return flow {
        var homeTeam = 0
        var awayTeam = 0
        (0..45).forEach {
            println("minuto: $it")
            delay(50)
            homeTeam += Random.nextInt(0, 21) / 20
            awayTeam += Random.nextInt(0, 21) / 20
            emit("$homeTeam - $awayTeam")
        }
    }
}

fun flowOperatorBuffer() {
    runBlocking {
        val time = measureTimeMillis {
            getDataByFlowStatic()
                .map {
                    setFormat(it)
                }
                .buffer()
                .collect {
                    delay(500)
                    println(it)
                }
        }
        println()
        println("Time: ${time}ms")
    }
}

fun getDataByFlowStatic(): Flow<Float> {
    return flow {
        (1..5).forEach {
            println("procesado datos ...")
            delay(300)
            emit(20 + it + Random.nextFloat())
        }
    }
}

fun flowOperatorFold() {
    runBlocking {
        val saving = getDataByFlow()
            .reduce { accumulator, value ->
                println("reduce.Acumulator: $accumulator")
                println("reduce.Value: $value")
                println("reduce.Current Saving: ${accumulator + value}")
                println()
                accumulator + value
            }

        val lastSaving = getDataByFlow()
            .fold(saving, { acc, value ->
                println("fold.Acumulador: $acc")
                println("fold.Value: $value")
                println("fold.Current saving: ${acc + value}")
                acc + value
            })
        println(lastSaving)
    }
}

fun flowOperatorReduce() {
    runBlocking {
        val saving = getDataByFlow()
            .reduce { accumulator, value ->
                println("Acumulator: $accumulator")
                println("Value: $value")
                println("Current Saving: ${accumulator + value}")
                println()
                accumulator + value
            }
        println(saving)
    }
}

fun flowOperatorFirstAndLast() {
    runBlocking {
        val first = getDataByFlow()
            .first()
        println("first $first")

        val last = getDataByFlow()
            .last()
        println("last $last")
    }
}

fun flowOperatorListAndSingle() {
    runBlocking {
        val list = getDataByFlow()
            .toList()
        println("list $list")

        val single = getDataByFlow()
            .take(1)
            .single()
        println("single: $single")
    }
}

fun flowOperatorTake() {
    runBlocking {
        getDataByFlow()
            .take(3)
            .map { setFormat(it) }
            .collect {
                println(it)
            }
    }
}

fun flowOperatorTransform() {
    runBlocking {
        getDataByFlow()
            .transform {
                emit(it)
                emit(setFormat(convertCelsToFahr(it), "F"))
            }
            .collect {
                println(it)
            }
    }
}

fun flowOperatorFilter() {
    runBlocking {
        getDataByFlow()
            .filter {
                it < 32
            }
            .map {
                setFormat(it)
            }
            .collect {
                println(it)
            }
    }
}

fun flowOperatorMap() {
    runBlocking {
        getDataByFlow()
            .map {
                //setFormat(it)
                setFormat(convertCelsToFahr(it), "F")
            }
            .collect {
                println(it)
            }
    }
}

fun convertCelsToFahr(cels: Float): Float = ((cels * 9) / 5) + 32

fun setFormat(temp: Float, degree: String = "C"): String = String.format(
    Locale.getDefault(),
    "%.1f°$degree", temp
)

fun cancelFlow() {
    runBlocking {
        val job = launch {
            getDataByFlow()
                .collect {
                    println(it)
                }
        }
        delay(someTime() * 2)
        job.cancel()
    }
}

fun coldFlow() {
    runBlocking {
        val dataFlow = getDataByFlow()
        println("esperando...")
        delay(someTime())
        dataFlow.collect {
            println(it)
        }
    }
}

fun basicFlow() {
    runBlocking {
        launch {
            getDataByFlow()
                .collect {
                    println(it)
                }
        }

        launch {
            (1..50).forEach {
                //delay(someTime())
                println("Tarea 2...")
            }
        }
    }
}

fun getDataByFlow(): Flow<Float> {
    return flow {
        (1..5).forEach {
            println("procesado datos ...")
            delay(someTime())
            emit(20 + it + Random.nextFloat())
        }
    }
}

fun changeWithContext() {
    runBlocking {
        startMessage()

        withContext(newSingleThreadContext("Custom Dispatchers Edu")) {
            startMessage()
            delay(someTime())
            println("Dentro Custom Dispatchers Edu")
            endMessage()
        }

        withContext(Dispatchers.IO) {
            startMessage()
            delay(someTime())
            println("petición al servidor")
            endMessage()
        }

        println("Dentro de la corrutina principal")

        endMessage()
    }
}

fun nestedDispatcher() {
    runBlocking {
        val job = launch {
            startMessage()

            launch {
                startMessage()
                delay(someTime())
                println("Otra tarea")
                endMessage()
            }
            launch(Dispatchers.IO) {
                startMessage()

                launch(newSingleThreadContext("Custom Dispatcher Edu")) {
                    startMessage()
                    println("Tarea con custom dispatcher edu")
                    endMessage()
                }

                delay(someTime())
                println("Tarea en el servidor")
                endMessage()
            }

            var sum = 0
            (1..100).forEach {
                sum += it
                delay(someTime() / 100)
            }
            println("Sum= $sum")
            endMessage()
        }
        delay(someTime() / 2)
        job.cancel()
        println("Job Cancelado...")
    }
}

fun dispatchers() {
    runBlocking {

        launch(newSingleThreadContext("Custom Dispatcher Lalin")) {
            startMessage()
            println("corrutina personalizada con un Dispatcher")
            endMessage()
        }

        /*launch(Dispatchers.Main) {
            startMessage()
            println("none")
            endMessage()
        }
        launch(Dispatchers.Default) {
            startMessage()
            println("Default")
            endMessage()
        }*/

        /*launch {
            startMessage()
            println("none")
            endMessage()
        }
        launch(Dispatchers.IO) {
            startMessage()
            println("IO")
            endMessage()
        }*/
    }
}
