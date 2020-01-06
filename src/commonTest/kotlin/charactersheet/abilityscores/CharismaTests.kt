package charactersheet.abilityscores

import kotlin.test.Test
import kotlin.test.assertEquals

class CharismaTests {

    @Test
    fun NPCReactionModifiersDerivedCorrectly() {

        val inputToExpectedOutput = (3..18).map { input ->
            when (input) {
                3 -> input to -2
                in 4..8 -> input to -1
                in 9..12 -> input to 0
                in 13..17 -> input to 1
                18 -> input to 2
                else -> input to 999
            }
        }

        val assertNPCReactionsModifier = { input: Int, expected: Int ->
            assertEquals(expected, Charisma(input).NPCReactionsModifier)
        }

        inputToExpectedOutput.forEach {
            assertNPCReactionsModifier(it.first, it.second)
        }
    }

    @Test
    fun maxNumRetainersModifiersDerivedCorrectly() {

        val inputToExpectedOutput = (3..18).map { input ->
            when (input) {
                3 -> input to 1
                in 4..5 -> input to 2
                in 6..8 -> input to 3
                in 9..12 -> input to 4
                in 13..15 -> input to 5
                in 16..17 -> input to 6
                18 -> input to 7
                else -> input to 999
            }
        }

        val assertMaxNumRetainersModifier = { input: Int, expected: Int ->
            assertEquals(expected, Charisma(input).maxRetainersModifier)
        }

        inputToExpectedOutput.forEach {
            assertMaxNumRetainersModifier(it.first, it.second)
        }
    }

    @Test
    fun retainerLoyaltyModifiersDerivedCorrectly() {

        val inputToExpectedOutput = (3..18).map { input ->
            when (input) {
                3 -> input to 4
                in 4..5 -> input to 5
                in 6..8 -> input to 6
                in 9..12 -> input to 7
                in 13..15 -> input to 8
                in 16..17 -> input to 9
                18 -> input to 10
                else -> input to 999
            }
        }

        val assertRetainerLoyaltyModifier = { input: Int, expected: Int ->
            assertEquals(expected, Charisma(input).retainersLoyaltyModifier)
        }

        inputToExpectedOutput.forEach {
            assertRetainerLoyaltyModifier(it.first, it.second)
        }
    }
}