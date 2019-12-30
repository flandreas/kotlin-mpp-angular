package ch.flandreas.techstack.domain

import kotlin.js.JsName

object Fibonacci {

    @JsName("of")
    fun of(n: Int): Long {
        if (n == 0) {
            return 0
        }

        var a = 0L
        var b = 1L
        var c: Long

        for (i in 2..n) {
            c = a + b
            a = b
            b = c
        }

        return b
    }

    @JsName("display")
    fun display(n: Int) {
        Output.display("Fibonacci of $n is ${of(n)}")
    }
}