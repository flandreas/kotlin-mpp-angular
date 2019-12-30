package ch.flandreas.techstack.domain

import kotlin.test.Test
import kotlin.test.assertEquals

class FibonacciTest {

    @Test
    fun shouldCalculate() {
        assertEquals(0, Fibonacci.of(0))
        assertEquals(1, Fibonacci.of(1))
        assertEquals(1, Fibonacci.of(2))
        assertEquals(2, Fibonacci.of(3))
        assertEquals(3, Fibonacci.of(4))
        assertEquals(5, Fibonacci.of(5))
        assertEquals(8, Fibonacci.of(6))
    }

    @Test
    fun shouldDisplay() {
        Fibonacci.display(6)
    }
}