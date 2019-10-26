package org.armya.physical_units

import junit.framework.TestCase.assertEquals
import org.junit.Test

class PhysicalUnitTests {

    @Test
    fun equal() {
        val x1 = PhysicalValue.create(1.0, listOf(BaseUnit.Meter), listOf(BaseUnit.Second))
        val y1 = PhysicalValue.create(1.0, listOf(BaseUnit.Meter), listOf(BaseUnit.Second))
        val z1 = x1 == y1
        assertEquals(true, z1)

        val y2 = PhysicalValue.create(1.0, listOf(BaseUnit.Meter))
        try {
            x1 == y2
            assert(false,{"Expected Incompatible physical unit exception"})
        } catch (e: PhysicalUnitException) {
            assertEquals("Cannot perform '==': Pysical units [Meter]/[Second] and [Meter]/null are incompatible", e.message)
        }

        val y3 = PhysicalValue.create(2.0, listOf(BaseUnit.Meter), listOf(BaseUnit.Second))
        val z3 = x1 == y3
        assertEquals(false, z3)
    }

    @Test
    fun plus() {
        val x = PhysicalValue.create(2.0, listOf(BaseUnit.Meter), listOf(BaseUnit.Second))
        val y = PhysicalValue.create(1.0, listOf(BaseUnit.Meter), listOf(BaseUnit.Second))
        val z = x + y
        assertEquals(PhysicalValue.create(3.0, listOf(BaseUnit.Meter), listOf(BaseUnit.Second)), z)
    }

    @Test
    fun minus() {
        val x = PhysicalValue.create(1.0, listOf(BaseUnit.Meter), listOf(BaseUnit.Second))
        val y = PhysicalValue.create(2.0, listOf(BaseUnit.Meter), listOf(BaseUnit.Second))
        val z = x - y
        assertEquals(PhysicalValue.create(-1.0, listOf(BaseUnit.Meter), listOf(BaseUnit.Second)), z)
    }

    @Test
    fun `physical mult physical`() {
        val x1 = PhysicalValue.create(3.0, listOf(BaseUnit.Meter), listOf(BaseUnit.Second))
        val y1 = PhysicalValue.create(2.0, listOf(BaseUnit.Kelvin), listOf(BaseUnit.Kilogram))
        val z1 = x1 * y1
        assertEquals(PhysicalValue.create(6.0, listOf(BaseUnit.Meter, BaseUnit.Kelvin), listOf(BaseUnit.Second, BaseUnit.Kilogram)), z1)

        val y2 = PhysicalValue.create(2.0, null, listOf(BaseUnit.Kilogram))
        val z2 = x1 * y2
        assertEquals(PhysicalValue.create(6.0, listOf(BaseUnit.Meter), listOf(BaseUnit.Second, BaseUnit.Kilogram)), z2)

        val x3 = PhysicalValue.create(3.0, null, listOf(BaseUnit.Second))
        val z3 = x3 * y1
        assertEquals(PhysicalValue.create(6.0, listOf(BaseUnit.Kelvin), listOf(BaseUnit.Second, BaseUnit.Kilogram)), z3)

        val y4 = PhysicalValue.create(2.0)
        val z4 = x1 * y4
        assertEquals(PhysicalValue.create(6.0, listOf(BaseUnit.Meter), listOf(BaseUnit.Second)), z4)
    }

    @Test
    fun `physical div physical`() {
        val x1 = PhysicalValue.create(3.0, listOf(BaseUnit.Meter), listOf(BaseUnit.Second))
        val y1 = PhysicalValue.create(2.0, listOf(BaseUnit.Kelvin), listOf(BaseUnit.Kilogram))
        val z1 = x1 / y1
        assertEquals(PhysicalValue.create(1.5, listOf(BaseUnit.Meter, BaseUnit.Kilogram), listOf(BaseUnit.Second, BaseUnit.Kelvin)), z1)

        val y2 = PhysicalValue.create(2.0, null, listOf(BaseUnit.Kilogram))
        val z2 = x1 / y2
        assertEquals(PhysicalValue.create(1.5, listOf(BaseUnit.Meter, BaseUnit.Kilogram), listOf(BaseUnit.Second)), z2)

        val x3 = PhysicalValue.create(3.0, null, listOf(BaseUnit.Second))
        val z3 = x3 / y1
        assertEquals(PhysicalValue.create(1.5, listOf(BaseUnit.Kilogram), listOf(BaseUnit.Second, BaseUnit.Kelvin)), z3)

        val y4 = PhysicalValue.create(2.0)
        val z4 = x1 / y4
        assertEquals(PhysicalValue.create(1.5, listOf(BaseUnit.Meter), listOf(BaseUnit.Second)), z4)

        val x5 = PhysicalValue.create(3.0)
        val z5 = x5 / y1
        assertEquals(PhysicalValue.create(1.5, listOf(BaseUnit.Kilogram), listOf(BaseUnit.Kelvin)), z5)
    }

    @Test
    fun order() {
        val x1 = PhysicalValue.create(1.0, listOf(BaseUnit.Meter, BaseUnit.Second, BaseUnit.Kelvin, BaseUnit.Kilogram, BaseUnit.Ampere, BaseUnit.Kandela, BaseUnit.Meter, BaseUnit.Second, BaseUnit.Kelvin, BaseUnit.Kilogram, BaseUnit.Ampere, BaseUnit.Kandela),
                listOf(BaseUnit.Second, BaseUnit.Meter, BaseUnit.Kilogram, BaseUnit.Kelvin, BaseUnit.Kandela, BaseUnit.Ampere, BaseUnit.Meter, BaseUnit.Second, BaseUnit.Kelvin, BaseUnit.Kilogram, BaseUnit.Ampere, BaseUnit.Kandela))
        assertEquals("null/null", x1.unitText())

        val x2 = PhysicalValue.create(1.0, listOf(BaseUnit.Meter, BaseUnit.Second, BaseUnit.Kelvin, BaseUnit.Kilogram, BaseUnit.Ampere, BaseUnit.Kandela, BaseUnit.Meter, BaseUnit.Second, BaseUnit.Kelvin, BaseUnit.Kilogram, BaseUnit.Ampere, BaseUnit.Kandela))
        assertEquals("[Meter, Meter, Second, Second, Kilogram, Kilogram, Ampere, Ampere, Kelvin, Kelvin, Kandela, Kandela]/null", x2.unitText())

        val x3 = PhysicalValue.create(1.0, null, listOf(BaseUnit.Second, BaseUnit.Meter, BaseUnit.Kilogram, BaseUnit.Kelvin, BaseUnit.Kandela, BaseUnit.Ampere, BaseUnit.Meter, BaseUnit.Second, BaseUnit.Kelvin, BaseUnit.Kilogram, BaseUnit.Ampere, BaseUnit.Kandela))
        assertEquals("null/[Meter, Meter, Second, Second, Kilogram, Kilogram, Ampere, Ampere, Kelvin, Kelvin, Kandela, Kandela]", x3.unitText())

        val y1 = PhysicalValue.create(1.0, listOf(BaseUnit.Meter, BaseUnit.Second, BaseUnit.Kelvin, BaseUnit.Second, BaseUnit.Second),
                                                 listOf(BaseUnit.Kandela, BaseUnit.Kilogram, BaseUnit.Ampere, BaseUnit.Kandela))
        val z1 = PhysicalValue.create(1.0, listOf(BaseUnit.Kelvin, BaseUnit.Meter, BaseUnit.Second, BaseUnit.Kelvin),
                                                 listOf(BaseUnit.Ampere, BaseUnit.Kilogram, BaseUnit.Ampere, BaseUnit.Kandela))
        val w1 = y1 * z1
        val w2 = z1 * y1
        assertEquals("[Meter, Meter, Second, Second, Second, Second, Kelvin, Kelvin, Kelvin]/[Kilogram, Kilogram, Ampere, Ampere, Ampere, Kandela, Kandela, Kandela]", w1.unitText())
        assertEquals(w2.unitText(), w1.unitText())
    }

    @Test
    fun `physical and numeric`() {
        val x1 = PhysicalValue.create(2.0, listOf(BaseUnit.Kelvin))
        val y1 = 4.0
        val z1 = x1 * y1
        assertEquals(PhysicalValue.create(8.0, listOf(BaseUnit.Kelvin)), z1)

        val z2 = x1 / y1
        assertEquals(PhysicalValue.create(0.5, listOf(BaseUnit.Kelvin)), z2)

        val z3 = y1 * x1
        assertEquals(PhysicalValue.create(8.0, listOf(BaseUnit.Kelvin)), z3)

        val z4 = y1 / x1
        assertEquals(PhysicalValue.create(2.0, null, listOf(BaseUnit.Kelvin)), z4)
    }

}