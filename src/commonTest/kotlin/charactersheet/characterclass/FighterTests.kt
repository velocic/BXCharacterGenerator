package charactersheet.characterclass

import charactersheet.abilityscores.*
import charactersheet.equipment.Weapon
import charactersheet.equipment.allBasicArmor
import charactersheet.equipment.allBasicWeapons
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue


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
        10..47,
        11..49,
        12..51,
        13..53,
        14..55
    )

    for (i in 0 until 100) {
        validRangesByLevel.forEachIndexed { index, expectedRange ->
            val characterLevel = index + 1
            val calculatedHitPoints = Fighter().calculateMaxHitPoints(characterLevel, abilityScores.constitution)

            assertTrue { calculatedHitPoints in expectedRange }
        }
    }
}

@Test
fun calculatedHitPointsFallInValidRangeWhenConBonusPositive() {
    val abilityScores = AbilityScores().apply {
        constitution = Constitution(3)
    }
    val validRangesByLevel = listOf(
        1..11,
        2..22,
        3..33,
        4..44,
        5..55,
        6..66,
        7..77,
        8..88,
        9..99,
        10..101,
        11..103,
        12..105,
        13..107,
        14..109
    )

    for (i in 0 until 100) {
        validRangesByLevel.forEachIndexed { index, expectedRange ->
            val characterLevel = index + 1
            val calculatedHitPoints = Fighter().calculateMaxHitPoints(characterLevel, abilityScores.constitution)

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

        val actualResult = Fighter().calculateClassBasedExperienceBonus(
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
fun fighterCanAccessCorrectArmor() {
    assertEquals(allBasicArmor, Fighter().allowedArmor)
}

@Test
fun fighterCanAccessCorrectWeaponTypes() {
    assertEquals(allBasicWeapons, Fighter().allowedWeapons)
}

@Test
fun ascendingAttackBonusCalculatedCorrectlyWithPositiveBonus() {
    val strength = Strength(18)
    val dexterity = Dexterity(18)
    val fighter = Fighter()
    val testWeapon = allBasicWeapons.filter {
        it.qualities.contains(Weapon.Qualities.MELEE) && it.qualities.contains(Weapon.Qualities.MISSILE)
    }[0]

    val expectedBonusByLevel = listOf(
        3, 3, 3,
        5, 5, 5,
        8, 8, 8,
        10, 10, 10,
        12, 12
    )

    expectedBonusByLevel.forEachIndexed { index, expectedBonus ->
        val classLevel = index + 1
        val (meleeBonus, missileBonus) = fighter.calculateAscendingAttackBonusesForWeapon(
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
    val fighter = Fighter()
    val testWeapon = allBasicWeapons.filter {
        it.qualities.contains(Weapon.Qualities.MELEE) && it.qualities.contains(Weapon.Qualities.MISSILE)
    }[0]

    val expectedBonusByLevel = listOf(
        -3, -3, -3,
        -1, -1, -1,
        2, 2, 2,
        4, 4, 4,
        6, 6
    )

    expectedBonusByLevel.forEachIndexed { index, expectedBonus ->
        val classLevel = index + 1
        val (meleeBonus, missileBonus) = fighter.calculateAscendingAttackBonusesForWeapon(
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
    val fighter = Fighter()

    val expectedSavingThrows = listOf(
        BasicProgressionRow.SavingThrows(9, 10, 11, 15, 13),
        BasicProgressionRow.SavingThrows(9, 10, 11, 15, 13),
        BasicProgressionRow.SavingThrows(9, 10, 11, 15, 13),
        BasicProgressionRow.SavingThrows(7, 8, 9, 13, 11),
        BasicProgressionRow.SavingThrows(7, 8, 9, 13, 11),
        BasicProgressionRow.SavingThrows(7, 8, 9, 13, 11),
        BasicProgressionRow.SavingThrows(5, 6, 7, 10, 9),
        BasicProgressionRow.SavingThrows(5, 6, 7, 10, 9),
        BasicProgressionRow.SavingThrows(5, 6, 7, 10, 9),
        BasicProgressionRow.SavingThrows(3, 4, 5, 8, 7),
        BasicProgressionRow.SavingThrows(3, 4, 5, 8, 7),
        BasicProgressionRow.SavingThrows(3, 4, 5, 8, 7),
        BasicProgressionRow.SavingThrows(1, 2, 3, 5, 5),
        BasicProgressionRow.SavingThrows(1, 2, 3, 5, 5)
    )

    expectedSavingThrows.forEachIndexed { index, expectedSavingThrowsForLevel ->
        val classLevel = index + 1
        val actualSavingThrows = fighter.magicSavingThrows(classLevel, wisdom)

        assertEquals(expectedSavingThrowsForLevel, actualSavingThrows)
    }
}

@Test
fun magicSavingThrowsCalculatedCorrectlyWithNegativeBonus() {
    val wisdom = Wisdom(3)
    val fighter = Fighter()

    val expectedSavingThrows = listOf(
        BasicProgressionRow.SavingThrows(15, 16, 17, 15, 19),
        BasicProgressionRow.SavingThrows(15, 16, 17, 15, 19),
        BasicProgressionRow.SavingThrows(15, 16, 17, 15, 19),
        BasicProgressionRow.SavingThrows(13, 14, 15, 13, 17),
        BasicProgressionRow.SavingThrows(13, 14, 15, 13, 17),
        BasicProgressionRow.SavingThrows(13, 14, 15, 13, 17),
        BasicProgressionRow.SavingThrows(11, 12, 13, 10, 15),
        BasicProgressionRow.SavingThrows(11, 12, 13, 10, 15),
        BasicProgressionRow.SavingThrows(11, 12, 13, 10, 15),
        BasicProgressionRow.SavingThrows(9, 10, 11, 8, 13),
        BasicProgressionRow.SavingThrows(9, 10, 11, 8, 13),
        BasicProgressionRow.SavingThrows(9, 10, 11, 8, 13),
        BasicProgressionRow.SavingThrows(7, 8, 9, 5, 11),
        BasicProgressionRow.SavingThrows(7, 8, 9, 5, 11)
    )

    expectedSavingThrows.forEachIndexed { index, expectedSavingThrowsForLevel ->
        val classLevel = index + 1
        val actualSavingThrows = fighter.magicSavingThrows(classLevel, wisdom)

        assertEquals(expectedSavingThrowsForLevel, actualSavingThrows)
    }
}
