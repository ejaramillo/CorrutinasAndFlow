package com.example.coroutines.previousbases

fun main() {
    lambda()
}

fun lambda() {
    println(multi(2, 3))

    multiLambda(2, 3, { result ->
        println(result)
    })

    multiLambda(2, 3) { result ->
        println(result)
    }

    val suma = { y: Int ->
        y + 3
    }

    println(suma(2))

    multiLambda(
        x = 2,
        y = 3,
        callback = { result ->
            println(result)
        }
    )

    multiLambda(
        x = 2,
        y = 3
    ) { result ->
        println(result)
    }

    val more: (String, Int) -> String = { str, int ->
        str + int
    }
    //println(more("hola ",123))

    val more2: (String, Int, (String,Int) -> Unit) -> Unit = { str, int, fn ->
        fn(str + int,int)
    }

    more2("hola Unit inside more2 ", 234) { result,resultInt ->
        println(result + resultInt)
    }

    fun String.evaluarLetras(entrada: Char){

    }

    val list = listOf("manzana","tomates","cuchillo","zanahora")
    val listaCosasFiltrada = (0..list.size -1)
        .map {
            Cosas(it, list.get(it))
        }
        .filter {
            it.name != "manzana"
        }
        .toList()

    println("listaCosasFiltrada --> " + listaCosasFiltrada)

}

data class Cosas(val index: Int, val name: String)

fun extendString(arg: String, num: Int) : String {
    val another : String.(Int) -> String = { this + it }

    return arg.another(num)
}

fun multiLambda(x: Int, y: Int, callback: (input: Int) -> Unit) {
    callback(x * y)
}

fun multi(x: Int, y: Int): Int {
    return x * y
}




