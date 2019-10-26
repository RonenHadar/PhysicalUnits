package org.armya.physical_units

class PhysicalUnitException(operator: String, first: PhysicalValue, second: PhysicalValue):
        Throwable("Cannot perform '$operator': Pysical units ${first.unitText()} and ${second.unitText()} are incompatible")