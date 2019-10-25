package org.armya

class PhysicalValue(val value: Double, val numerator: List<BaseUnit>, val denominator: List<BaseUnit>) {
    companion object {
        inline fun mutualPlus(d1: PhysicalValue, d2: PhysicalValue): PhysicalValue {
            return PhysicalValue(0.0, Units.Meter)
        }
    }

    operator fun plus(other: PhysicalValue): PhysicalValue {
        if(!sameBases(other)) {
            throw(UnitException())
        }
        return when {
            this.units.denominator < other.units.denominator -> {
                mutualPlus(other, this)
            }
            this.units.denominator == other.units.denominator -> {
               PhysicalValue(this.value+other.value, this.units)
            }
            else -> {
                mutualPlus(this, other)
            }
        }
    }

}