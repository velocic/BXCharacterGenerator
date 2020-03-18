package charactersheet.characterclass

import charactersheet.abilityscores.*
import charactersheet.equipment.Weapon
import charactersheet.equipment.allBasicArmor
import charactersheet.equipment.allBasicWeapons
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MagicUserTests {

    @Test
    fun calculatedHitPointsFallInValidRangeWhenConBonusNegative() {
        val abilityScores = AbilityScores().apply {
            constitution = Constitution(3)
        }
        val validRangesByLevel = listOf(
            1..1,
            2..2,
            3..3,
            4..4,
            5..5,
            6..6,
            7..7,
            8..8,
            9..9,
            10..10,
            11..11,
            12..12,
            13..13,
            14..14
        )

        for (i in 0 until 100) {
            validRangesByLevel.forEachIndexed { index, expectedRange ->
                val characterLevel = index + 1
                val calculatedHitPoints = MagicUser().calculateMaxHitPoints(characterLevel, abilityScores.constitution)

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
            4..7,
            8..14,
            12..21,
            16..28,
            20..35,
            24..42,
            28..49,
            32..56,
            36..63,
            37..64,
            38..65,
            39..66,
            40..67,
            41..68
        )

        for (i in 0 until 100) {
            validRangesByLevel.forEachIndexed { index, expectedRange ->
                val characterLevel = index + 1
                val calculatedHitPoints = MagicUser().calculateMaxHitPoints(characterLevel, abilityScores.constitution)

                assertTrue { calculatedHitPoints in expectedRange }
            }
        }
    }

    @Test
    fun classBasedXPBonusCalculatedCorrectly() {
        val testInputToexpectedOutput = listOf(
            AbilityScores().apply { intelligence.score = 3 } to -0.2,
            AbilityScores().apply { intelligence.score = 6 } to -0.1,
            AbilityScores().apply { intelligence.score = 9 } to 0.0,
            AbilityScores().apply { intelligence.score = 13 } to 0.05,
            AbilityScores().apply { intelligence.score = 16 } to 0.1
        )

        testInputToexpectedOutput.forEach { (inputData, expectedResult) ->
            val actualResult = MagicUser().calculateClassBasedExperienceBonus(
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
    fun magicUserCanAccessCorrectArmor() {
        assertEquals(emptyList(), MagicUser().allowedArmor)
    }

    @Test
    fun magicUserCanAccessCorrectWeaponTypes() {
        val expectedAllowedWeaponTypes = listOf(
            Weapon.Types.DAGGER,
            Weapon.Types.SILVERDAGGER
        )
        val actualAllowedWeaponTypes = MagicUser().allowedWeapons.map { it.type }

        expectedAllowedWeaponTypes.forEach { expectedAllowedType ->
            assertTrue { expectedAllowedType in actualAllowedWeaponTypes }
        }
    }

    @Test
    fun magicUserCannotAccessIncorrectWeaponTypes() {
        val expectedDisallowedWeaponTypes = listOf(
            Weapon.Types.BATTLEAXE,
            Weapon.Types.CLUB,
            Weapon.Types.CROSSBOW,
            Weapon.Types.HANDAXE,
            Weapon.Types.HOLYWATERVIAL,
            Weapon.Types.JAVELIN,
            Weapon.Types.LANCE,
            Weapon.Types.LONGBOW,
            Weapon.Types.MACE,
            Weapon.Types.BURNINGOILFLASK,
            Weapon.Types.POLEARM,
            Weapon.Types.SHORTBOW,
            Weapon.Types.SHORTSWORD,
            Weapon.Types.SLING,
            Weapon.Types.SPEAR,
            Weapon.Types.STAFF,
            Weapon.Types.SWORD,
            Weapon.Types.TORCH,
            Weapon.Types.TWOHANDEDSWORD,
            Weapon.Types.WARHAMMER
        )
        val actualAllowedWeaponTypes = MagicUser().allowedWeapons.map { it.type }

        expectedDisallowedWeaponTypes.forEach { expectedDisallowedType ->
            assertTrue { expectedDisallowedType !in actualAllowedWeaponTypes }
        }
    }

    @Test
    fun ascendingAttackBonusCalculatedCorrectlyWithPositiveBonus() {
        val strength = Strength(18)
        val dexterity = Dexterity(18)
        val magicUser = MagicUser()
        val testWeapon = allBasicWeapons.filter {
            it.qualities.contains(Weapon.Qualities.MELEE) && it.qualities.contains(Weapon.Qualities.MISSILE)
        }[0]

        val expectedBonusByLevel = listOf(
            3, 3, 3, 3, 3,
            5, 5, 5, 5, 5,
            8, 8, 8, 8
        )

        expectedBonusByLevel.forEachIndexed { index, expectedBonus ->
            val classLevel = index + 1
            val (meleeBonus, missileBonus) = magicUser.calculateAscendingAttackBonusesForWeapon(
                classLevel,
                testWeapon,
                strength,
                dexterity
            )

            assertEquals(expectedBonus, meleeBonus)
        }
    }

    @Test
    fun ascendingAttackBonusCalculatedCorrectlyWithNegativeBonus() {
        val strength = Strength(3)
        val dexterity = Dexterity(3)
        val magicUser = MagicUser()
        val testWeapon = allBasicWeapons.filter {
            it.qualities.contains(Weapon.Qualities.MELEE) && it.qualities.contains(Weapon.Qualities.MISSILE)
        }[0]

        val expectedBonusByLevel = listOf(
            -3, -3, -3, -3, -3,
            -1, -1, -1, -1, -1,
            2, 2, 2, 2
        )

        expectedBonusByLevel.forEachIndexed { index, expectedBonus ->
            val classLevel = index + 1
            val (meleeBonus, missileBonus) = magicUser.calculateAscendingAttackBonusesForWeapon(
                classLevel,
                testWeapon,
                strength,
                dexterity
            )

            assertEquals(expectedBonus, meleeBonus)
        }
    }

    @Test
    fun magicSavingThrowsCalculatedCorrectlyWithPositiveBonus() {
        val wisdom = Wisdom(18)
        val magicUser = MagicUser()

        val expectedSavingThrows = listOf(
            BasicProgressionRow.SavingThrows(10, 11, 10, 16, 12),
            BasicProgressionRow.SavingThrows(10, 11, 10, 16, 12),
            BasicProgressionRow.SavingThrows(10, 11, 10, 16, 12),
            BasicProgressionRow.SavingThrows(10, 11, 10, 16, 12),
            BasicProgressionRow.SavingThrows(10, 11, 10, 16, 12),
            BasicProgressionRow.SavingThrows(8, 9, 8, 14, 9),
            BasicProgressionRow.SavingThrows(8, 9, 8, 14, 9),
            BasicProgressionRow.SavingThrows(8, 9, 8, 14, 9),
            BasicProgressionRow.SavingThrows(8, 9, 8, 14, 9),
            BasicProgressionRow.SavingThrows(8, 9, 8, 14, 9),
            BasicProgressionRow.SavingThrows(5, 6, 5, 11, 5),
            BasicProgressionRow.SavingThrows(5, 6, 5, 11, 5),
            BasicProgressionRow.SavingThrows(5, 6, 5, 11, 5),
            BasicProgressionRow.SavingThrows(5, 6, 5, 11, 5)
        )

        expectedSavingThrows.forEachIndexed { index, expectedSavingThrowsForLevel ->
            val classLevel = index + 1
            val actualSavingThrows = magicUser.magicSavingThrows(classLevel, wisdom)

            assertEquals(expectedSavingThrowsForLevel, actualSavingThrows)
        }
    }

    @Test
    fun magicSavingThrowsCalculatedCorrectlyWithNegativeBonus() {
        val wisdom = Wisdom(3)
        val magicUser = MagicUser()

        val expectedSavingThrows = listOf(
            BasicProgressionRow.SavingThrows(16, 17, 16, 16, 18),
            BasicProgressionRow.SavingThrows(16, 17, 16, 16, 18),
            BasicProgressionRow.SavingThrows(16, 17, 16, 16, 18),
            BasicProgressionRow.SavingThrows(16, 17, 16, 16, 18),
            BasicProgressionRow.SavingThrows(16, 17, 16, 16, 18),
            BasicProgressionRow.SavingThrows(14, 15, 14, 14, 15),
            BasicProgressionRow.SavingThrows(14, 15, 14, 14, 15),
            BasicProgressionRow.SavingThrows(14, 15, 14, 14, 15),
            BasicProgressionRow.SavingThrows(14, 15, 14, 14, 15),
            BasicProgressionRow.SavingThrows(14, 15, 14, 14, 15),
            BasicProgressionRow.SavingThrows(11, 12, 11, 11, 11),
            BasicProgressionRow.SavingThrows(11, 12, 11, 11, 11),
            BasicProgressionRow.SavingThrows(11, 12, 11, 11, 11),
            BasicProgressionRow.SavingThrows(11, 12, 11, 11, 11)
        )

        expectedSavingThrows.forEachIndexed { index, expectedSavingThrowsForLevel ->
            val classLevel = index + 1
            val actualSavingThrows = magicUser.magicSavingThrows(classLevel, wisdom)

            assertEquals(expectedSavingThrowsForLevel, actualSavingThrows)
        }
    }
}