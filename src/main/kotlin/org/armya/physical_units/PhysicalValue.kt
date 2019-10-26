package org.armya.physical_units

// The main constructor is used only from within the class, when we keep the unit details ordered by design
class PhysicalValue private constructor(sorted: Boolean, val value: Double, val numerator: List<BaseUnit>? = null, val denominator: List<BaseUnit>? = null) {

    // all users call this constructor which makes sure the units details are ordered before creating the object
    constructor(value: Double, numerator: List<BaseUnit>? = null, denominator: List<BaseUnit>? = null): this(true, value, numerator?.sorted(), denominator?.sorted())

    companion object {

        fun addLists(l1: List<BaseUnit>?, l2: List<BaseUnit>?): MutableList<BaseUnit>? {
            return if(l1 == null) {
                l2?.toMutableList()
            } else {
                l2?.plus(l1)?.sorted() as MutableList<BaseUnit>? ?: l1.toMutableList()
            }
        }

        fun reduce(value: Double, num1: List<BaseUnit>, den1: List<BaseUnit>): PhysicalValue {
            val num2 = mutableListOf<BaseUnit>()
            val den2 = mutableListOf<BaseUnit>()
            var indexN = 0
            var indexD = 0
            while(indexN < num1.size && indexD < den1.size) {
                val n = num1[indexN]
                val d = den1[indexD]
                when {
                    n < d -> {
                        num2.add(n)
                        indexN++
                    }
                    d < n -> {
                        den2.add(d)
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
            } else if(indexD < den1.size) {
                den2.addAll(den1.takeLast(den1.size-indexD))
            }
            return PhysicalValue(true, value, num2, den2)
        }

        fun combine(value: Double, num1: List<BaseUnit>?, den1: List<BaseUnit>?, num2: List<BaseUnit>?, den2: List<BaseUnit>?): PhysicalValue {
            val num = addLists(num1, num2)
            val den = addLists(den1, den2)
            if(num == null || den == null) {
                return PhysicalValue(true, value, num, den)
            }
            return reduce(value, num, den)
        }


    }

    override fun toString(): String {
        return "$value ${unitText()}"
    }

    fun unitText() = "$numerator/$denominator"

    operator fun times(other: PhysicalValue): PhysicalValue = combine(value*other.value, numerator, denominator, other.numerator, other.denominator)
    operator fun div(other: PhysicalValue): PhysicalValue = combine(value/other.value, numerator, denominator, other.denominator, other.numerator)

    fun sameBases(other: PhysicalValue) = (numerator == other.numerator && denominator == other.denominator)

    operator fun plus(other: PhysicalValue): PhysicalValue {
        if(!sameBases(other)) {
            throw(PhysicalUnitException("+", this, other))
        }
        return PhysicalValue(true, value+other.value, numerator, denominator)
    }

    operator fun minus(other: PhysicalValue): PhysicalValue {
        if(!sameBases(other)) {
            throw(PhysicalUnitException("-", this, other))
        }
        return PhysicalValue(true,value-other.value, numerator, denominator)
    }

    operator fun compareTo(other: PhysicalValue): Int {
        if(!sameBases(other)) {
            throw(PhysicalUnitException("==", this, other))
        }
        return value.compareTo(other.value)
    }

    override operator fun equals(other: Any?): Boolean = ((this === other) || (other is PhysicalValue && compareTo(other) == 0))
}
