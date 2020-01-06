package charactersheet.abilityscores

import kotlin.test.Test
import kotlin.test.assertEquals

class DexterityTests {

    @Test
    fun armorClassModifiersDerivedCorrectly() {

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

        val assertArmorClassModifier = { input: Int, expected: Int ->
            assertEquals(expected, Dexterity(input).armorClassModifier)
        }

        inputToExpectedOutput.forEach {
            assertArmorClassModifier(it.first, it.second)
        }
    }

    @Test
    fun missileModifiersDerivedCorrectly() {

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

        val assertMissileModifier = { input: Int, expected: Int ->
            assertEquals(expected, Dexterity(input).missileModifier)
        }

        inputToExpectedOutput.forEach {
            assertMissileModifier(it.first, it.second)
        }
    }

    @Test
    fun initiativeModifiersDerivedCorrectly() {

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

        val assertInitiativeModifiers = { input: Int, expected: Int ->
            assertEquals(expected, Dexterity(input).initiativeModifier)
        }

        inputToExpectedOutput.forEach {
            assertInitiativeModifiers(it.first, it.second)
        }
    }
}