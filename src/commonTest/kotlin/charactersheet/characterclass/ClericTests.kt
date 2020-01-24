package charactersheet.characterclass

import charactersheet.abilityscores.*
import charactersheet.equipment.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ClericTests {

    @Test
    fun calculatedHitPointsFallInValidRangeWhenConBonusNegative() {
        val abilityScores = AbilityScores().apply {
            constitution = Constitution(3) //-3 hit point modifier
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
            10..28,
            11..29,
            12..30,
            13..31,
            14..32
        )

        //Testing randomized content is weird. Run the assertions enough times that there is very likely
        //a full spread over the range of random values
        for(i in 0 until 100) {
            validRangesByLevel.forEachIndexed { index, intRange ->
                val characterLevel = index + 1
                val calculatedHitPoints = Cleric().calculateMaxHitPoints(characterLevel, abilityScores.constitution)

                assertTrue { calculatedHitPoints in validRangesByLevel[index] }
            }
        }
    }

    @Test
    fun calculatedHitPointsFallInValidRangeWhenConBonusPositive() {
        val abilityScores = AbilityScores().apply {
            constitution = Constitution(18) //+3 hit point modifier
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
            37..82,
            38..83,
            39..84,
            40..85,
            41..86
        )

        //Testing randomized content is weird. Run the assertions enough times that there is very likely
        //a full spread over the range of random values
        for(i in 0 until 100) {
            validRangesByLevel.forEachIndexed { index, expectedRange ->
                val characterLevel = index + 1
                val calculatedHitPoints = Cleric().calculateMaxHitPoints(characterLevel, abilityScores.constitution)

                assertTrue { calculatedHitPoints in expectedRange }
            }
        }
    }

    @Test
    fun classBasedXPBonusCalculatedCorrectly() {
        val testInputToExpectedOutput = listOf(
            AbilityScores().apply { wisdom.score = 3 } to -.2,
            AbilityScores().apply { wisdom.score = 6 } to -.1,
            AbilityScores().apply { wisdom.score = 9 } to 0.0,
            AbilityScores().apply { wisdom.score = 13 } to .05,
            AbilityScores().apply { wisdom.score = 16 } to .1
        )

        testInputToExpectedOutput.forEach {
            val inputData = it.first
            val expectedResult = it.second

            val actualResult = Cleric().calculateClassBasedExperienceBonus(
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
    fun clericCanAccessCorrectArmor() {
        val expected = listOf(leatherArmor, chainMailArmor, plateMailArmor)
        val cleric = Cleric()

        expected.forEach { expectedType ->
            assertTrue { cleric.allowedArmor.contains(expectedType) }
        }
    }

    @Test
    fun clericCanAccessCorrectWeaponTypes() {
        val expectedAllowedWeaponTypes = listOf(
            Weapon.Types.CLUB,
            Weapon.Types.MACE,
            Weapon.Types.SLING,
            Weapon.Types.STAFF,
            Weapon.Types.WARHAMMER
        )
        val actualAllowedWeaponTypes = Cleric().allowedWeapons.map { it.type }

        expectedAllowedWeaponTypes.forEach { expectedAllowedType ->
            assertTrue { expectedAllowedType in actualAllowedWeaponTypes }
        }
    }

    @Test
    fun clericCannotAccessIncorrectWeaponTypes() {
        val expectedDisallowedWeaponTypes = listOf(
            Weapon.Types.BATTLEAXE,
            Weapon.Types.CROSSBOW,
            Weapon.Types.DAGGER,
            Weapon.Types.HANDAXE,
            Weapon.Types.HOLYWATERVIAL,
            Weapon.Types.JAVELIN,
            Weapon.Types.LANCE,
            Weapon.Types.LONGBOW,
            Weapon.Types.BURNINGOILFLASK,
            Weapon.Types.POLEARM,
            Weapon.Types.SHORTBOW,
            Weapon.Types.SHORTSWORD,
            Weapon.Types.SILVERDAGGER,
            Weapon.Types.SPEAR,
            Weapon.Types.SWORD,
            Weapon.Types.TORCH,
            Weapon.Types.TWOHANDEDSWORD
        )
        val actualAllowedWeaponTypes = Cleric().allowedWeapons.map { it.type }

        expectedDisallowedWeaponTypes.forEach { expectedDisallowedType ->
            assertTrue { expectedDisallowedType !in actualAllowedWeaponTypes }
        }
    }

    @Test
    fun ascendingAttackBonusCalculatedCorrectlyWithPositiveBonus() {
        val strength = Strength(18)
        val dexterity = Dexterity(18)
        val cleric = Cleric()
        val testWeapon = allBasicWeapons.filter {
            it.qualities.contains(Weapon.Qualities.MELEE) && it.qualities.contains(Weapon.Qualities.MISSILE)
        }[0]

        val expectedBonusByLevel = listOf(
            3, 3, 3, 3,
            5, 5, 5, 5,
            8, 8, 8, 8,
            10, 10
        )

        expectedBonusByLevel.forEachIndexed { index, expectedBonus ->
            val classLevel = index + 1
            val attackBonuses = cleric.calculateAscendingAttackBonusesForWeapon(
                classLevel,
                testWeapon,
                strength,
                dexterity
            )

            assertEquals(expectedBonus, attackBonuses.melee)
            assertEquals(expectedBonus, attackBonuses.missile)
        }
    }

    @Test
    fun ascendingAttackBonusCalculatedCorrectlyWithNegativeBonus() {
        val strength = Strength(3)
        val dexterity = Dexterity(3)
        val cleric = Cleric()
        val testWeapon = allBasicWeapons.filter {
            it.qualities.contains(Weapon.Qualities.MELEE) && it.qualities.contains(Weapon.Qualities.MISSILE)
        }[0]

        val expectedBonusByLevel = listOf(
            -3, -3, -3, -3,
            -1, -1, -1, -1,
            2, 2, 2, 2,
            4, 4
        )

        expectedBonusByLevel.forEachIndexed { index, expectedBonus ->
            val classLevel = index + 1
            val attackBonuses = cleric.calculateAscendingAttackBonusesForWeapon(
                classLevel,
                testWeapon,
                strength,
                dexterity
            )
            assertEquals(expectedBonus, attackBonuses.melee)
            assertEquals(expectedBonus, attackBonuses.missile)
        }
    }

    @Test
    fun magicSavingThrowsCalculatedCorrectlyWithPositiveBonus() {
        val wisdom = Wisdom(18)
        val cleric = Cleric()

        val expectedSavingThrows = listOf(
            BasicProgressionRow.SavingThrows(8, 9, 11, 16, 12),
            BasicProgressionRow.SavingThrows(8, 9, 11, 16, 12),
            BasicProgressionRow.SavingThrows(8, 9, 11, 16, 12),
            BasicProgressionRow.SavingThrows(8, 9, 11, 16, 12),
            BasicProgressionRow.SavingThrows(6, 7, 9, 14, 9),
            BasicProgressionRow.SavingThrows(6, 7, 9, 14, 9),
            BasicProgressionRow.SavingThrows(6, 7, 9, 14, 9),
            BasicProgressionRow.SavingThrows(6, 7, 9, 14, 9),
            BasicProgressionRow.SavingThrows(3, 4, 6, 11, 6),
            BasicProgressionRow.SavingThrows(3, 4, 6, 11, 6),
            BasicProgressionRow.SavingThrows(3, 4, 6, 11, 6),
            BasicProgressionRow.SavingThrows(3, 4, 6, 11, 6),
            BasicProgressionRow.SavingThrows(1, 2, 4, 8, 4),
            BasicProgressionRow.SavingThrows(1, 2, 4, 8, 4)
        )

        expectedSavingThrows.forEachIndexed { index, expectedSavingThrowsForLevel ->
            val classLevel = index + 1
            val actualSavingThrows = cleric.magicSavingThrows(classLevel, wisdom)

            assertEquals(expectedSavingThrowsForLevel, actualSavingThrows)
        }
    }

    @Test
    fun magicSavingThrowsCalculatedCorrectlyWithNegativeBonus() {
        val wisdom = Wisdom(3)
        val cleric = Cleric()

        val expectedSavingThrows = listOf(
            BasicProgressionRow.SavingThrows(14, 15, 17, 16, 18),
            BasicProgressionRow.SavingThrows(14, 15, 17, 16, 18),
            BasicProgressionRow.SavingThrows(14, 15, 17, 16, 18),
            BasicProgressionRow.SavingThrows(14, 15, 17, 16, 18),
            BasicProgressionRow.SavingThrows(12, 13, 15, 14, 15),
            BasicProgressionRow.SavingThrows(12, 13, 15, 14, 15),
            BasicProgressionRow.SavingThrows(12, 13, 15, 14, 15),
            BasicProgressionRow.SavingThrows(12, 13, 15, 14, 15),
            BasicProgressionRow.SavingThrows(9, 10, 12, 11, 12),
            BasicProgressionRow.SavingThrows(9, 10, 12, 11, 12),
            BasicProgressionRow.SavingThrows(9, 10, 12, 11, 12),
            BasicProgressionRow.SavingThrows(9, 10, 12, 11, 12),
            BasicProgressionRow.SavingThrows(6, 8, 10, 8, 10),
            BasicProgressionRow.SavingThrows(6, 8, 10, 8, 10)
        )

        expectedSavingThrows.forEachIndexed { index, expectedSavingThrowsForLevel ->
            val classLevel = index + 1
            val actualSavingThrows = cleric.magicSavingThrows(classLevel, wisdom)

            assertEquals(expectedSavingThrowsForLevel, actualSavingThrows)
        }
    }
}
