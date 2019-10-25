package org.armya

class Units(val numerator: List<BaseUnit>?, val denominator: List<BaseUnit>?) {
    companion object {
        inline fun addLists(l1: List<BaseUnit>?, l2: List<BaseUnit>?): MutableList<BaseUnit>? {
            return if(l1 == null) {
                l2?.toMutableList()
            } else {
                l2?.plus(l1)?.sorted() as MutableList<BaseUnit>?
            }
        }
    }

    override fun toString(): String {
        return "($numerator)/($denominator)"
    }

    operator fun times(other: Units): Units {
        val num1 = addLists(numerator, other.numerator)
        val dem1 = addLists(denominator, other.denominator)
        if(num1 == null || dem1 == null) {
            return Units(num1, dem1)
        }

        val num2 = mutableListOf<BaseUnit>()
        val dem2 = mutableListOf<BaseUnit>()
        var indexN = 0
        var indexD = 0
        while(indexN < num1.size && indexD < dem1.size) {
            val n = num1[indexN]
            val d = dem1[indexD]
            when {
                n < d -> {
                    num2.add(n)
                    indexN++
                }
                d < n -> {
                    dem2.add(d)
                    indexD++
                }
                else -> {
                    indexN++
                    indexD++
                }
            }
        }
        if(indexN < num1.size) {
            num2.addAll(num1.takeLast(num1.size-indexN))
        }
        if(indexD < dem1.size) {
            dem2.addAll(dem1.takeLast(dem1.size-indexD))
        }
        return Units(num2, dem2)
    }
}