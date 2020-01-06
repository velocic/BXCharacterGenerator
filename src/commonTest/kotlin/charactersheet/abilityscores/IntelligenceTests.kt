package charactersheet.abilityscores

import kotlin.test.Test
import kotlin.test.assertEquals

class IntelligenceTests {

    @Test
    fun additionalSpokenLanguageModifiersDerivedCorrectly() {

        val inputToExpectedOutput = (3..18).map { input ->
            when (input) {
                in 3..12 -> input to 0
                in 13..15 -> input to 1
                in 16..17 -> input to 2
                18 -> input to 3
                else -> input to 999
            }
        }

        val assertAdditionalLanguagesModifier = { input: Int, expected: Int ->
            assertEquals(expected, Intelligence(input).spokenLanguagesModifier)
        }

        inputToExpectedOutput.forEach {
            assertAdditionalLanguagesModifier(it.first, it.second)
        }
    }

    @Test
    fun literacyModifiersDerivedCorrectly() {
        val inputToExpectedOutput = (3..18).map { input ->
            when (input) {
                in 3..5 -> input to Literacy.ILLITERATE
                in 6..8 -> input to Literacy.BASIC
                in 9..18 -> input to Literacy.LITERATE
                else -> input to Literacy.ILLITERATE
            }
        }

        val assertLiteracyModifier = { input: Int, expected: Literacy ->
            assertEquals(expected, Intelligence(input).literacy)
        }

        inputToExpectedOutput.forEach {
            assertLiteracyModifier(it.first, it.second)
        }
    }
}