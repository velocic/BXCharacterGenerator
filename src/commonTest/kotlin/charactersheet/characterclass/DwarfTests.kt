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
    }

    @Test
    fun ascendingAttackBonusCalculatedCorrectlyWithPositiveBonus() {
    }

    @Test
    fun ascendingAttackBonusCalculatedCorrectlyWithNegativeBonus() {
    }

    @Test
    fun magicSavingThrowsCalculatedCorrectlyWithPositiveBonus() {
    }

    @Test
    fun magicSavingThrowsCalculatedCorrectlyWithNegativeBonus() {
    }
}
