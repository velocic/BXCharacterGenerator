package charactersheet.characterclass

import charactersheet.abilityscores.*
import charactersheet.equipment.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class DwarfTests {

    @Test
    fun calculatedHitPointsFallInValidRangeWhenConBonusNegative() {
        val abilityScores = AbilityScores().apply {
            constitution = Constitution(3)
        }
        val validRangesByLevel = listOf(
            1..5,
            2..10,
            3..15,
            4..20,
            5..25,
            6..30,
            7..35,
            8..40,
            9..45,
            10..46,
            11..47,
            12..48
        )

        //Testing randomized content is weird. Run the assertions enough times that there is very likely
        //a full spread over the range of random values
        for (i in 0 until 100) {
            validRangesByLevel.forEachIndexed { index, expectedRange ->
                val characterLevel = index + 1
                val calculatedHitPoints = Dwarf().calculateMaxHitPoints(characterLevel, abilityScores.constitution)

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
            4..11,
            8..22,
            12..33,
            16..44,
            20..55,
            24..66,
            28..77,
            32..88,
            36..99,
            39..102,
            42..105,
            45..108
        )

        //Testing randomized content is weird. Run the assertions enough times that there is very likely
        //a full spread over the range of random values
        for (i in 0 until 100) {
            validRangesByLevel.forEachIndexed { index, expectedRange ->
                val characterLevel = index + 1
                val calculatedHitPoints = Dwarf().calculateMaxHitPoints(characterLevel, abilityScores.constitution)

                assertTrue { calculatedHitPoints in expectedRange }
            }
        }
    }

    @Test
    fun classBasedXPBonusCalculatedCorrectly() {
        val testInputToExpectedOutput = listOf(
            AbilityScores().apply { strength.score = 3 } to -.2,
            AbilityScores().apply { strength.score = 6 } to -.1,
            AbilityScores().apply { strength.score = 9 } to 0.0,
            AbilityScores().apply { strength.score = 13 } to .05,
            AbilityScores().apply { strength.score = 16 } to .1
        )

        testInputToExpectedOutput.forEach {
            val (inputData, expectedResult) = it

            val actualResult = Dwarf().calculateClassBasedExperienceBonus(
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
    fun dwarfCanAccessCorrectArmor() {
        assertEquals(allBasicArmor, Dwarf().allowedArmor)
    }

    @Test
    fun dwarfCanAccessCorrectWeaponTypes() {
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
        val actualAllowedWeaponTypes = Dwarf().allowedWeapons.map { it.type }

        expectedAllowedWeaponTypes.forEach {  expectedAllowedType ->
            assertTrue { expectedAllowedType in actualAllowedWeaponTypes }
        }
    }

    @Test
    fun dwarfCannotAccessIncorrectWeaponTypes() {
        val expectedDisallowedWeaponTypes = listOf(
            Weapon.Types.LONGBOW,
            Weapon.Types.TWOHANDEDSWORD
        )
        val actualAllowedWeaponTypes = Dwarf().allowedWeapons.map { it.type }

        expectedDisallowedWeaponTypes.forEach { expectedDisallowedType ->
            assertTrue { expectedDisallowedType !in actualAllowedWeaponTypes }
        }
    }

    @Test
    fun ascendingAttackBonusCalculatedCorrectlyWithPositiveBonus() {
        val strength = Strength(18)
        val dexterity = Dexterity(18)
        val dwarf = Dwarf()
        val testWeapon = allBasicWeapons.filter {
            it.qualities.contains(Weapon.Qualities.MELEE) && it.qualities.contains(Weapon.Qualities.MISSILE)
        }[0]

        val expectedBonusByLevel = listOf(
            3, 3, 3,
            5, 5, 5,
            8, 8, 8,
            10, 10, 10
        )

        expectedBonusByLevel.forEachIndexed { index, expectedBonus ->
            val classLevel = index + 1
            val (meleeBonus, missileBonus) = dwarf.calculateAscendingAttackBonusesForWeapon(
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
        val dwarf = Dwarf()
        val testWeapon = allBasicWeapons.filter {
            it.qualities.contains(Weapon.Qualities.MELEE) && it.qualities.contains(Weapon.Qualities.MISSILE)
        }[0]

        val expectedBonusByLevel = listOf(
            -3, -3, -3,
            -1, -1, -1,
            2, 2, 2,
            4, 4, 4
        )

        expectedBonusByLevel.forEachIndexed { index, expectedBonus ->
            val classLevel = index + 1
            val (meleeBonus, missileBonus) = dwarf.calculateAscendingAttackBonusesForWeapon(
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
        val dwarf = Dwarf()

        val expectedSavingThrows = listOf(
            BasicProgressionRow.SavingThrows(5, 6, 7, 13, 9),
            BasicProgressionRow.SavingThrows(5, 6, 7, 13, 9),
            BasicProgressionRow.SavingThrows(5, 6, 7, 13, 9),
            BasicProgressionRow.SavingThrows(3, 4, 5, 10, 7),
            BasicProgressionRow.SavingThrows(3, 4, 5, 10, 7),
            BasicProgressionRow.SavingThrows(3, 4, 5, 10, 7),
            BasicProgressionRow.SavingThrows(1, 2, 3, 7, 5),
            BasicProgressionRow.SavingThrows(1, 2, 3, 7, 5),
            BasicProgressionRow.SavingThrows(1, 2, 3, 7, 5),
            BasicProgressionRow.SavingThrows(1, 1, 1, 4, 3),
            BasicProgressionRow.SavingThrows(1, 1, 1, 4, 3),
            BasicProgressionRow.SavingThrows(1, 1, 1, 4, 3)
        )

        expectedSavingThrows.forEachIndexed { index, expectedSavingThrowsForLevel ->
            val classLevel = index + 1
            val actualSavingThrows = dwarf.magicSavingThrows(classLevel, wisdom)

            assertEquals(expectedSavingThrowsForLevel, actualSavingThrows)
        }
    }

    @Test
    fun magicSavingThrowsCalculatedCorrectlyWithNegativeBonus() {
        val wisdom = Wisdom(3)
        val dwarf = Dwarf()

        val expectedSavingThrows = listOf(
            BasicProgressionRow.SavingThrows(11, 12, 13, 13, 15),
            BasicProgressionRow.SavingThrows(11, 12, 13, 13, 15),
            BasicProgressionRow.SavingThrows(11, 12, 13, 13, 15),
            BasicProgressionRow.SavingThrows(9, 10, 11, 10, 13),
            BasicProgressionRow.SavingThrows(9, 10, 11, 10, 13),
            BasicProgressionRow.SavingThrows(9, 10, 11, 10, 13),
            BasicProgressionRow.SavingThrows(7, 8, 9, 7, 11),
            BasicProgressionRow.SavingThrows(7, 8, 9, 7, 11),
            BasicProgressionRow.SavingThrows(7, 8, 9, 7, 11),
            BasicProgressionRow.SavingThrows(5, 6, 7, 4, 9),
            BasicProgressionRow.SavingThrows(5, 6, 7, 4, 9),
            BasicProgressionRow.SavingThrows(5, 6, 7, 4, 9)
        )

        expectedSavingThrows.forEachIndexed { index, expectedSavingThrowsForLevel ->
            val classLevel = index + 1
            val actualSavingThrows = dwarf.magicSavingThrows(classLevel, wisdom)

            assertEquals(expectedSavingThrowsForLevel, actualSavingThrows)
        }
    }
}
