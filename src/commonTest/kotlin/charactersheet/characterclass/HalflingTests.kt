package charactersheet.characterclass

import charactersheet.abilityscores.*
import charactersheet.equipment.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class HalflingTests {

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
            8..24
        )

        for (i in 0 until 100) {
            validRangesByLevel.forEachIndexed { index, expectedRange ->
                val characterLevel = index + 1
                val calculatedHitPoints = Halfling().calculateMaxHitPoints(characterLevel, abilityScores.constitution)

                assertTrue { calculatedHitPoints in expectedRange }
            }
        }
    }

    @Test
    fun calculatedHitPointsFallInValidRangeWhenConBonusPositive() {
        val abilityScores = AbilityScores().apply {
            constitution = Constitution(3)
        }
        val validRangesBylevel = listOf(
            1..9,
            1..18,
            1..27,
            1..36,
            1..45,
            1..54,
            1..63,
            1..72
        )

        for (i in 0 until 100) {
            validRangesBylevel.forEachIndexed { index, expectedRange ->
                val characterLevel = index + 1
                val calculatedHitPoints = Halfling().calculateMaxHitPoints(characterLevel, abilityScores.constitution)

                assertTrue { calculatedHitPoints in expectedRange }
            }
        }
    }

    @Test
    fun classBasedXPBonusCalculatedCorrectly() {
        val testInputToExpectedOutput = listOf(
            AbilityScores().apply { dexterity.score = 3; strength.score = 3 } to 0.0,
            AbilityScores().apply { dexterity.score = 13; strength.score = 10 } to 0.05,
            AbilityScores().apply { dexterity.score = 10; strength.score = 13 } to 0.05,
            AbilityScores().apply { dexterity.score = 13; strength.score = 13 } to 0.05,
            AbilityScores().apply { dexterity.score = 16; strength.score = 13 } to 0.05,
            AbilityScores().apply { dexterity.score = 13; strength.score = 16 } to 0.05,
            AbilityScores().apply { dexterity.score = 16; strength.score = 16 } to 0.1
        )

        testInputToExpectedOutput.forEach { (inputData, expectedResult) ->
            val actualResult = Halfling().calculateClassBasedExperienceBonus(
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
    fun halflingCanAccessCorrectArmor() {
        assertEquals(allBasicArmor, Halfling().allowedArmor)
    }

    @Test
    fun halflingCanAccessCorrectWeaponTypes() {
        val expectedAllowedWeaponTypes = listOf(
            Weapon.Types.BATTLEAXE,
            Weapon.Types.CLUB,
            Weapon.Types.CROSSBOW,
            Weapon.Types.DAGGER,
            Weapon.Types.HANDAXE,
            Weapon.Types.HOLYWATERVIAL,
            Weapon.Types.JAVELIN,
            Weapon.Types.LANCE,
            Weapon.Types.MACE,
            Weapon.Types.BURNINGOILFLASK,
            Weapon.Types.POLEARM,
            Weapon.Types.SHORTBOW,
            Weapon.Types.SHORTSWORD,
            Weapon.Types.SILVERDAGGER,
            Weapon.Types.SLING,
            Weapon.Types.SPEAR,
            Weapon.Types.STAFF,
            Weapon.Types.SWORD,
            Weapon.Types.TORCH,
            Weapon.Types.WARHAMMER
        )
        val actualAllowedWeaponTypes = Halfling().allowedWeapons.map { it.type }

        expectedAllowedWeaponTypes.forEach {  expectedAllowedType ->
            assertTrue { expectedAllowedType in actualAllowedWeaponTypes }
        }
    }

    @Test
    fun halflingCannotAccessIncorrectWeaponTypes() {
        val expectedDisallowedWeaponTypes = listOf(
            Weapon.Types.LONGBOW,
            Weapon.Types.TWOHANDEDSWORD
        )
        val actualAllowedWeaponTypes = Halfling().allowedWeapons.map { it.type }

        expectedDisallowedWeaponTypes.forEach { expectedDisallowedType ->
            assertTrue { expectedDisallowedType !in actualAllowedWeaponTypes }
        }
    }

    @Test
    fun ascendingAttackBonusCalculatedCorrectlyWithPositiveBonus() {
        val strength = Strength(18)
        val dexterity = Dexterity(18)
        val halfling = Halfling()
        val testWeapon = allBasicWeapons.filter {
            it.qualities.contains(Weapon.Qualities.MELEE) && it.qualities.contains(Weapon.Qualities.MISSILE)
        }[0]

        val expectedMeleeBonusByLevel = listOf(
            3, 3, 3,
            5, 5, 5,
            8, 8
        )

        val expectedMissileBonusByLevel = listOf(
            4, 4, 4,
            6, 6, 6,
            9, 9
        )

        expectedMeleeBonusByLevel.forEachIndexed { index, expectedBonus ->
            val classLevel = index + 1
            val (meleeBonus, _) = halfling.calculateAscendingAttackBonusesForWeapon(
                classLevel,
                testWeapon,
                strength,
                dexterity
            )

            assertEquals(expectedBonus, meleeBonus)
        }

        expectedMissileBonusByLevel.forEachIndexed { index, expectedBonus ->
            val classLevel = index + 1
            val (_, missileBonus) = halfling.calculateAscendingAttackBonusesForWeapon(
                classLevel,
                testWeapon,
                strength,
                dexterity
            )

            assertEquals(expectedBonus, missileBonus)
        }
    }

    @Test
    fun ascendingAttackBonusCalculatedCorrectlyWithNegativeBonus() {
        val strength = Strength(3)
        val dexterity = Dexterity(3)
        val halfling = Halfling()
        val testWeapon = allBasicWeapons.filter {
            it.qualities.contains(Weapon.Qualities.MELEE) && it.qualities.contains(Weapon.Qualities.MISSILE)
        }[0]

        val expectedMeleeBonusByLevel = listOf(
            -3, -3, -3,
            -1, -1, -1,
            2, 2
        )

        val expectedMissileBonusByLevel = listOf(
            -2, -2, -2,
            0, 0, 0,
            3, 3
        )

        expectedMeleeBonusByLevel.forEachIndexed { index, expectedBonus ->
            val classLevel = index + 1
            val (meleeBonus, _) = halfling.calculateAscendingAttackBonusesForWeapon(
                classLevel,
                testWeapon,
                strength,
                dexterity
            )

            assertEquals(expectedBonus, meleeBonus)
        }

        expectedMissileBonusByLevel.forEachIndexed { index, expectedBonus ->
            val classLevel = index + 1
            val (_, missileBonus) = halfling.calculateAscendingAttackBonusesForWeapon(
                classLevel,
                testWeapon,
                strength,
                dexterity
            )

            assertEquals(expectedBonus, missileBonus)
        }
    }

    @Test
    fun magicSavingThrowsCalculatedCorrectlyWithPositiveBonus() {
        val wisdom = Wisdom(18)
        val halfling = Halfling()

        val expectedSavingThrows = listOf(
            BasicProgressionRow.SavingThrows(5, 6, 7, 13, 9),
            BasicProgressionRow.SavingThrows(5, 6, 7, 13, 9),
            BasicProgressionRow.SavingThrows(5, 6, 7, 13, 9),
            BasicProgressionRow.SavingThrows(3, 4, 5, 10, 7),
            BasicProgressionRow.SavingThrows(3, 4, 5, 10, 7),
            BasicProgressionRow.SavingThrows(3, 4, 5, 10, 7),
            BasicProgressionRow.SavingThrows(1, 2, 3, 7, 5),
            BasicProgressionRow.SavingThrows(1, 2, 3, 7, 5)
        )

        expectedSavingThrows.forEachIndexed { index, expectedSavingThrowsForLevel ->
            val classLevel = index + 1
            val actualSavingThrows = halfling.magicSavingThrows(classLevel, wisdom)

            assertEquals(expectedSavingThrowsForLevel, actualSavingThrows)
        }
    }

    @Test
    fun magicSavingThrowsCalculatedCorrectlyWithNegativeBonus() {
        val wisdom = Wisdom(3)
        val halfling = Halfling()

        val expectedSavingThrows = listOf(
            BasicProgressionRow.SavingThrows(11, 12, 13, 13, 15),
            BasicProgressionRow.SavingThrows(11, 12, 13, 13, 15),
            BasicProgressionRow.SavingThrows(11, 12, 13, 13, 15),
            BasicProgressionRow.SavingThrows(9, 10, 11, 10, 13),
            BasicProgressionRow.SavingThrows(9, 10, 11, 10, 13),
            BasicProgressionRow.SavingThrows(9, 10, 11, 10, 13),
            BasicProgressionRow.SavingThrows(7, 8, 9, 7, 11),
            BasicProgressionRow.SavingThrows(7, 8, 9, 7, 11)
        )

        expectedSavingThrows.forEachIndexed { index, expectedSavingThrowsForLevel ->
            val classLevel = index + 1
            val actualSavingThrows = halfling.magicSavingThrows(classLevel, wisdom)

            assertEquals(expectedSavingThrowsForLevel, actualSavingThrows)
        }
    }
}