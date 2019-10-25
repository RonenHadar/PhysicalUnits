package org.armya

enum class BaseUnit: Comparable<BaseUnit> {
    Meter {
        override val order: Int = 0
    },
    Second {
        override val order: Int = 1
    },
    Kilogram {
        override val order: Int = 2
    },
    Ampere {
        override val order: Int = 3
    },
    Kelvin {
        override val order: Int = 4
    },
    Mole {
        override val order: Int = 5
    },
    Kandela {
        override val order: Int = 6
    };

    // maybe no need for order, needs to see
    abstract val order: Int
}