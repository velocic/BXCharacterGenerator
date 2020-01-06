package charactersheet.abilityscores

import kotlin.test.Test
import kotlin.test.assertEquals

class ConstitutionTests {

    @Test
    fun hitPointModifiersDerivedCorrectly() {

        val inputToExpectedOutput = (3..18).map { input ->
            when (input) {
                3 -> input to -3
                in 4..5 -> input to -2
                in 6..8 -> input to -1
                in 9..12 -> input to 0
                in 13..15 -> input to 1
                in 16..17 -> input to 2
                18 -> input to 3
                else -> input to 999
            }
        }

        val assertHitPointModifier = { input: Int, expected: Int ->
            assertEquals(expected, Constitution(input).hitPointsModifier)
        }

        inputToExpectedOutput.forEach {
            assertHitPointModifier(it.first, it.second)
        }
    }
}