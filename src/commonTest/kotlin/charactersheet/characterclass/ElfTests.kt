package charactersheet.characterclass

import charactersheet.abilityscores.*
import charactersheet.equipment.Weapon
import charactersheet.equipment.allBasicArmor
import charactersheet.equipment.allBasicWeapons
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ElfTests {

    @Test
    fun calculatedHitPointsFallInValidRangeWhenConBonusNegative() {
        val abilityScores = AbilityScores().apply {
            constitution = Constitution(3)
        }
        val validRangesByLevel = listOf(
            1..3,
            2..6,
            3..9,
            4..12,
            5..15,
            6..18,
            7..21,
            8..24,
            9..27,
            10..29
        )

        for (i in 0 until 100) {
            validRangesByLevel.forEachIndexed { index, expectedRange ->
                val characterLevel = index + 1
                val calculatedHitPoints = Elf().calculateMaxHitPoints(characterLevel, abilityScores.constitution)

                assertTrue { calculatedHitPoints in expectedRange }
            }
        }
    }

    @Test
    fun calculatedHitPointsFallInValidRangeWhenConBonusPositive() {
        val abilityScores = AbilityScores().apply {
            constitution = Constitution(18)
        }
        val validRangesByLevel = listOf(
            4..9,
            8..18,
            12..27,
            16..36,
            20..45,
            24..54,
            28..63,
            32..72,
            36..81,
            38..83
        )

        for (i in 0 until 100) {
            validRangesByLevel.forEachIndexed { index, expectedRange ->
                val characterLevel = index + 1
                val calculatedHitPoints = Elf().calculateMaxHitPoints(characterLevel, abilityScores.constitution)

                assertTrue { calculatedHitPoints in expectedRange }
            }
        }
    }

    @Test
    fun classBasedXPBonusCalculatedCorrectly() {
        val testInputToExpectedOutput = listOf(
            AbilityScores().apply { strength.score = 3; intelligence.score = 3 } to 0.0,
            AbilityScores().apply { strength.score = 13; intelligence.score = 10 } to 0.0,
            AbilityScores().apply { strength.score = 10; intelligence.score = 13 } to 0.0,
            AbilityScores().apply { strength.score = 13; intelligence.score = 13 } to 0.05,
            AbilityScores().apply { strength.score = 13; intelligence.score = 16 } to 0.10
        )

        testInputToExpectedOutput.forEach {
            val (inputData, expectedResult) = it

            val actualResult = Elf().calculateClassBasedExperienceBonus(
                inputData.strength,
                inputData.intelligence,
                inputData.dexterity,
                inputData.charisma,
                inputData.wisdom,
                inputData.constitution
            )

            assertEquals(expectedResult, actualResult)
        }
    }

    @Test
    fun elfCanAccessCorrectArmor() {
        assertEquals(allBasicArmor, Elf().allowedArmor)
    }

    @Test
    fun elfCanAccessCorrectWeaponTypes() {
        assertEquals(allBasicWeapons, Elf().allowedWeapons)
    }

    @Test
    fun ascendingAttackBonusCalculatedCorrectlyWithPositiveBonus() {
        val strength = Strength(18)
        val dexterity = Dexterity(18)
        val elf = Elf()
        val testWeapon = allBasicWeapons.filter {
            it.qualities.contains(Weapon.Qualities.MELEE) && it.qualities.contains(Weapon.Qualities.MISSILE)
        }[0]

        val expectedBonusByLevel = listOf(
            3, 3, 3,
            5, 5, 5,
            8, 8, 8,
            10
        )

        expectedBonusByLevel.forEachIndexed { index, expectedBonus ->
            val classLevel = index + 1
            val (meleeBonus, missileBonus) = elf.calculateAscendingAttackBonusesForWeapon(
                classLevel,
                testWeapon,
                strength,
                dexterity
            )

            assertEquals(expectedBonus, meleeBonus)
            assertEquals(expectedBonus, missileBonus)
        }
    }

    @Test
    fun ascendingAttackBonusCalculatedCorrectlyWithNegativeBonus() {
        val strength = Strength(3)
        val dexterity = Dexterity(3)
        val elf = Elf()
        val testWeapon = allBasicWeapons.filter {
            it.qualities.contains(Weapon.Qualities.MELEE) && it.qualities.contains(Weapon.Qualities.MISSILE)
        }[0]

        val expectedBonusByLevel = listOf(
            -3, -3, -3,
            -1, -1, -1,
            2, 2, 2,
            4
        )

        expectedBonusByLevel.forEachIndexed { index, expectedBonus ->
            val classLevel = index + 1
            val (meleeBonus, missileBonus) = elf.calculateAscendingAttackBonusesForWeapon(
                classLevel,
                testWeapon,
                strength,
                dexterity
            )

            assertEquals(expectedBonus, meleeBonus)
            assertEquals(expectedBonus, missileBonus)
        }
    }

    @Test
    fun magicSavingThrowsCalculatedCorrectlyWithPositiveBonus() {
        val wisdom = Wisdom(18)
        val elf = Elf()

        val expectedSavingThrows = listOf(
            BasicProgressionRow.SavingThrows(9, 10, 10, 15, 12),
            BasicProgressionRow.SavingThrows(9, 10, 10, 15, 12),
            BasicProgressionRow.SavingThrows(9, 10, 10, 15, 12),
            BasicProgressionRow.SavingThrows(7, 8, 8, 13, 9),
            BasicProgressionRow.SavingThrows(7, 8, 8, 13, 9),
            BasicProgressionRow.SavingThrows(7, 8, 8, 13, 9),
            BasicProgressionRow.SavingThrows(5, 6, 6, 10, 7),
            BasicProgressionRow.SavingThrows(5, 6, 6, 10, 7),
            BasicProgressionRow.SavingThrows(5, 6, 6, 10, 7),
            BasicProgressionRow.SavingThrows(3, 4, 5, 8, 5)
        )

        expectedSavingThrows.forEachIndexed { index, expectedSavingThrowsForLevel ->
            val classLevel = index + 1
            val actualSavingThrows = elf.magicSavingThrows(classLevel, wisdom)

            assertEquals(expectedSavingThrowsForLevel, actualSavingThrows)
        }
    }

    @Test
    fun magicSavingThrowsCalculatedCorrectlyWithNegativeBonus() {
        val wisdom = Wisdom(3)
        val elf = Elf()

        val expectedSavingThrows = listOf(
            BasicProgressionRow.SavingThrows(15, 16, 16, 15, 18),
            BasicProgressionRow.SavingThrows(15, 16, 16, 15, 18),
            BasicProgressionRow.SavingThrows(15, 16, 16, 15, 18),
            BasicProgressionRow.SavingThrows(13, 14, 14, 13, 15),
            BasicProgressionRow.SavingThrows(13, 14, 14, 13, 15),
            BasicProgressionRow.SavingThrows(13, 14, 14, 13, 15),
            BasicProgressionRow.SavingThrows(11, 12, 12, 10, 13),
            BasicProgressionRow.SavingThrows(11, 12, 12, 10, 13),
            BasicProgressionRow.SavingThrows(11, 12, 12, 10, 13),
            BasicProgressionRow.SavingThrows(9, 10, 11, 8, 11)
        )

        expectedSavingThrows.forEachIndexed { index, expectedSavingThrowsForLevel ->
            val classLevel = index + 1
            val actualSavingThrows = elf.magicSavingThrows(classLevel, wisdom)

            assertEquals(expectedSavingThrowsForLevel, actualSavingThrows)
        }
    }
}