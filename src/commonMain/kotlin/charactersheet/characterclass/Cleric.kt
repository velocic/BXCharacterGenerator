package charactersheet.characterclass

import charactersheet.Language
import charactersheet.abilityscores.*
import charactersheet.equipment.Weapon
import charactersheet.equipment.allBasicArmor
import charactersheet.equipment.allBasicWeapons
import kotlin.math.max

internal class Cleric : CharacterClass(
    listOf(AbilityScores.Type.WIS),
    1..6,
    true,
    listOf(
        SpecialAbility(1, "Divine Magic"),
        SpecialAbility(1, "Turning the Undead")
    ),
    listOf(
        SpellcasterProgressionRow(0, 1 to 1..6, 19, 0, BasicProgressionRow.SavingThrows(11, 12, 14, 16, 15), listOf(0, 0, 0, 0, 0)),
        SpellcasterProgressionRow(1500, 2 to 1..6, 19, 0, BasicProgressionRow.SavingThrows(11, 12, 14, 16, 15), listOf(1, 0, 0, 0, 0)),
        SpellcasterProgressionRow(3000, 3 to 1..6, 19, 0, BasicProgressionRow.SavingThrows(11, 12, 14, 16, 15), listOf(2, 0, 0, 0, 0)),
        SpellcasterProgressionRow(6000, 4 to 1..6, 19, 0, BasicProgressionRow.SavingThrows(11, 12, 14, 16, 15), listOf(2, 1, 0, 0, 0)),
        SpellcasterProgressionRow(12000, 5 to 1..6, 17, 2, BasicProgressionRow.SavingThrows(9, 10, 12, 14, 12), listOf(2, 2, 0, 0, 0)),
        SpellcasterProgressionRow(25000, 6 to 1..6, 17, 2, BasicProgressionRow.SavingThrows(9, 10, 12, 14, 12), listOf(2, 2, 1, 1, 0)),
        SpellcasterProgressionRow(50000, 7 to 1..6, 17, 2, BasicProgressionRow.SavingThrows(9, 10, 12, 14, 12), listOf(2, 2, 2, 1, 1)),
        SpellcasterProgressionRow(100000, 8 to 1..6, 17, 2, BasicProgressionRow.SavingThrows(9, 10, 12, 14, 12), listOf(3, 3, 2, 2, 1)),
        SpellcasterProgressionRow(200000, 9 to 1..6, 14, 5, BasicProgressionRow.SavingThrows(6, 7, 9, 11, 9), listOf(3, 3, 3, 2, 2)),
        SpellcasterProgressionRow(300000, 9 to 1..6, 14, 5, BasicProgressionRow.SavingThrows(6, 7, 9, 11, 9), listOf(4, 4, 3, 3, 2)),
        SpellcasterProgressionRow(400000, 9 to 1..6, 14, 5, BasicProgressionRow.SavingThrows(6, 7, 9, 11, 9), listOf(4, 4, 4, 3, 3)),
        SpellcasterProgressionRow(500000, 9 to 1..6, 14, 5, BasicProgressionRow.SavingThrows(6, 7, 9, 11, 9), listOf(5, 5, 4, 4, 3)),
        SpellcasterProgressionRow(600000, 9 to 1..6, 12, 7, BasicProgressionRow.SavingThrows(3, 5, 7, 8, 7), listOf(5, 5, 5, 4, 4)),
        SpellcasterProgressionRow(700000, 9 to 1..6, 12, 7, BasicProgressionRow.SavingThrows(3, 5, 7, 8, 7), listOf(6, 5, 5, 5, 4))
    ),
    allowedArmor = allBasicArmor,
    allowedWeapons = allBasicWeapons.filter { Weapon.Qualities.BLUNT in it.qualities },
    baseLanguages = listOf(
        Language.ALIGNMENT,
        Language.COMMON
    )
) {
    override fun calculateMaxHitPoints(classLevel: Int, constitution: Constitution): Int =
        (1..classLevel).fold(0) { result, current ->
            result + when (current) {
                in 1..9 -> max(hitDice.random() + constitution.hitPointsModifier, 1)
                else -> 1
            }
        }


    override fun calculateClassBasedExperienceBonus(
        strength: Strength,
        intelligence: Intelligence,
        dexterity: Dexterity,
        charisma: Charisma,
        wisdom: Wisdom,
        constitution: Constitution
    ): Double = BasePrimeRequisite(wisdom.score).XPModifier
}
