package org.armya.physical_units

// Create new PhysicalValue through the create function, the constructor is for internal use only
class PhysicalValue private constructor(val value: Double, val numerator: List<BaseUnit>? = null, val denominator: List<BaseUnit>? = null) {

    companion object {
        // utility function for creating PhysicalUnits
        fun create(value: Double, numerator: List<BaseUnit>? = null, denominator: List<BaseUnit>? = null): PhysicalValue {
            val num = numerator?.sorted()
            val den = denominator?.sorted()
            return orderedCreate(value, num, den)
        }

        private fun orderedCreate(value: Double, numerator: List<BaseUnit>?, denominator: List<BaseUnit>?): PhysicalValue {
            if(numerator == null || denominator == null) {
                return PhysicalValue(value, numerator, denominator)
            }
            return reduce(value, numerator, denominator)
        }

        private fun addLists(l1: List<BaseUnit>?, l2: List<BaseUnit>?): MutableList<BaseUnit>? {
            return if(l1 == null) {
                l2?.toMutableList()
            } else {
                l2?.plus(l1)?.sorted() as MutableList<BaseUnit>? ?: l1.toMutableList()
            }
        }

        private fun reduce(value: Double, num1: List<BaseUnit>, den1: List<BaseUnit>): PhysicalValue {
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
            return PhysicalValue(value, if(num2.isEmpty()) { null } else { num2 }, if(den2.isEmpty()) { null } else { den2 })
        }

        private fun combine(value: Double, num1: List<BaseUnit>?, den1: List<BaseUnit>?, num2: List<BaseUnit>?, den2: List<BaseUnit>?): PhysicalValue {
            val num = addLists(num1, num2)
            val den = addLists(den1, den2)
            return orderedCreate(value, num, den)
        }
    }

    override fun toString(): String {
        return "$value ${unitText()}"
    }

    // TODO: take care of special cases such as no units, single values, no numerator and no denominator
    fun unitText(): String = "$numerator/$denominator"

    operator fun times(other: PhysicalValue): PhysicalValue = combine(value*other.value, numerator, denominator, other.numerator, other.denominator)
    operator fun div(other: PhysicalValue): PhysicalValue = combine(value/other.value, numerator, denominator, other.denominator, other.numerator)

    private fun sameBases(other: PhysicalValue) = (numerator == other.numerator && denominator == other.denominator)

    operator fun plus(other: PhysicalValue): PhysicalValue {
        if(!sameBases(other)) {
            throw(PhysicalUnitException("+", this, other))
        }
        return PhysicalValue(value+other.value, numerator, denominator)
    }

    operator fun minus(other: PhysicalValue): PhysicalValue {
        if(!sameBases(other)) {
            throw(PhysicalUnitException("-", this, other))
        }
        return PhysicalValue(value-other.value, numerator, denominator)
    }

    operator fun compareTo(other: PhysicalValue): Int {
        if(!sameBases(other)) {
            throw(PhysicalUnitException("==", this, other))
        }
        return value.compareTo(other.value)
    }

    override operator fun equals(other: Any?): Boolean = ((this === other) || (other is PhysicalValue && compareTo(other) == 0))
}
