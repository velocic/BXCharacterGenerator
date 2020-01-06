package charactersheet.abilityscores

import kotlin.test.Test
import kotlin.test.assertEquals

class StrengthTests {

    @Test
    fun meleeModifiersDerivedCorrectly() {
        val inputToExpectedOutput = listOf(
            3 to -3,
            4 to -2,
            5 to -2,
            6 to -1,
            7 to -1,
            8 to -1,
            9 to 0,
            10 to 0,
            11 to 0,
            12 to 0,
            13 to 1,
            14 to 1,
            15 to 1,
            16 to 2,
            17 to 2,
            18 to 3
        )
        val assertMeleeModifier = { input: Int, expected: Int ->
            assertEquals(expected, Strength(input).meleeModifier)
        }

        inputToExpectedOutput.forEach {
            assertMeleeModifier(it.first, it.second)
        }
    }

    @Test
    fun openDoorsModifiersDerivedCorrectly() {

        val inputToExpectedOutput = listOf(
            3 to 1..6,
            4 to 1..6,
            5 to 1..6,
            6 to 1..6,
            7 to 1..6,
            8 to 1..6,
            9 to 2..6,
            10 to 2..6,
            11 to 2..6,
            12 to 2..6,
            13 to 3..6,
            14 to 3..6,
            15 to 3..6,
            16 to 4..6,
            17 to 4..6,
            18 to 5..6
        )

        val assertOpenDoorsModifier = { input: Int, expected: IntRange ->
            assertEquals(expected, Strength(input).openDoorsModifier)
        }

        inputToExpectedOutput.forEach {
            assertOpenDoorsModifier(it.first, it.second)
        }
    }
}