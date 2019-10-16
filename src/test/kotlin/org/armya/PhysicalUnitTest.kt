package org.armya

import org.armya.histogram.DataSet
import org.junit.Test
import kotlin.random.Random
import kotlin.system.measureNanoTime

//import org.junit.jupiter.api.Test

internal class HistogramTest {
    @Test
    fun test1() {
        val dataSet = DataSet()
        println("$dataSet")

        for (index in 0..999) {
            val values = ints()
            val time1 = measureNanoTime { values.sort() }
            dataSet.sample("sort", time1.toDouble())
            val time2 = measureNanoTime { values.bubble() }
            dataSet.sample("bubble", time2.toDouble())
            val time3 = measureNanoTime { values.bubble1() }
            dataSet.sample("bubble1", time3.toDouble())
        }
        Histogram(dataSet, header="sort time max", cutOf = 2, measure = { 0.0 })
    }

    fun ints(): MutableList<Int> {
        val result = mutableListOf<Int>()
        for (index in 0..99 ) { result.add(Random.nextInt(1000)) }
        return result
    }
}

fun MutableList<Int>.bubble() {
    for (index1 in 0..size-1) {
        for (index2 in 0..size-index1-1) {
            if(this[index2] > this[index2+1]) {
                val tmp = this[index2]
                this[index2] = this[index2+1]
                this[index2+1] = tmp
            }
        }
    }
}

fun MutableList<Int>.bubble1() {
    for (index1 in 0..size-1) {
        var change = false
        for (index2 in 0..size-index1-1) {
            if(this[index2] > this[index2+1]) {
                val tmp = this[index2]
                this[index2] = this[index2+1]
                this[index2+1] = tmp
                change = true
            }
        }
        if(!change) { break }
    }
}