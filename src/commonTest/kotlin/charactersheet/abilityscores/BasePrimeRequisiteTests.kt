package charactersheet.abilityscores

import kotlin.test.Test
import kotlin.test.assertEquals

class BasePrimeRequisiteTests {

    @Test
    fun experienceModifierDerivedCorrectly() {

        val inputToExpectedOutput = (3..18).map { input ->
            when (input) {
                in 3..5 -> input to -.2
                in 6..8 -> input to -.1
                in 9..12 -> input to 0.0
                in 13..15 -> input to .05
                in 16..18 -> input to .1
                else -> input to 0.0
            }
        }

        val assertExperienceModifier = { input: Int, expected: Double ->
            assertEquals(expected, BasePrimeRequisite(input).XPModifier)
        }

        inputToExpectedOutput.forEach {
            assertExperienceModifier(it.first, it.second)
        }
    }
}