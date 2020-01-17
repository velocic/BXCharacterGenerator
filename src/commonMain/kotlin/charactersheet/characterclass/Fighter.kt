package charactersheet.characterclass

import charactersheet.Language
import charactersheet.abilityscores.*
import charactersheet.equipment.allBasicArmor
import charactersheet.equipment.allBasicWeapons
import kotlin.math.max

internal class Fighter : CharacterClass(
    listOf(AbilityScores.Type.STR),
    1..8,
    true,
    listOf(
        SpecialAbility(1, "Create a Stronghold"),
        SpecialAbility(9, "Earn a Title")
    ),
    listOf(
        BasicProgressionRow(0, 1 to 1..8, 19, 0, BasicProgressionRow.SavingThrows(12, 13, 14, 15, 16)),
        BasicProgressionRow(2000, 2 to 1..8, 19, 0, BasicProgressionRow.SavingThrows(12, 13, 14, 15, 16)),
        BasicProgressionRow(4000, 3 to 1..8, 19, 0, BasicProgressionRow.SavingThrows(12, 13, 14, 15, 16)),
        BasicProgressionRow(8000, 4 to 1..8, 17, 2, BasicProgressionRow.SavingThrows(10, 11, 12, 13, 14)),
        BasicProgressionRow(16000, 5 to 1..8, 17, 2, BasicProgressionRow.SavingThrows(10, 11, 12, 13, 14)),
        BasicProgressionRow(32000, 6 to 1..8, 17, 2, BasicProgressionRow.SavingThrows(10, 11, 12, 13, 14)),
        BasicProgressionRow(64000, 7 to 1..8, 14, 5, BasicProgressionRow.SavingThrows(8, 9, 10, 10, 12)),
        BasicProgressionRow(120000, 8 to 1..8, 14, 5, BasicProgressionRow.SavingThrows(8, 9, 10, 10, 12)),
        BasicProgressionRow(240000, 9 to 1..8, 14, 5, BasicProgressionRow.SavingThrows(8, 9, 10, 10, 12)),
        BasicProgressionRow(360000, 9 to 1..8, 12, 7, BasicProgressionRow.SavingThrows(6, 7, 8, 8, 10)),
        BasicProgressionRow(480000, 9 to 1..8, 12, 7, BasicProgressionRow.SavingThrows(6, 7, 8, 8, 10)),
        BasicProgressionRow(600000, 9 to 1..8, 12, 7, BasicProgressionRow.SavingThrows(6, 7, 8, 8, 10)),
        BasicProgressionRow(720000, 9 to 1..8, 10, 9, BasicProgressionRow.SavingThrows(4, 5, 6, 5, 8)),
        BasicProgressionRow(840000, 9 to 1..8, 10, 9, BasicProgressionRow.SavingThrows(4, 5, 6, 5, 8))
    ),
    allowedArmor = allBasicArmor,
    allowedWeapons = allBasicWeapons,
    baseLanguages = listOf(
        Language.ALIGNMENT,
        Language.COMMON
    )
) {
    override fun calculateMaxHitPoints(classLevel: Int, constitution: Constitution): Int =
        (1..classLevel).fold(0) { result, current ->
            result + when (current) {
                in 1..9 -> max(hitDice.random() + constitution.hitPointsModifier, 1)
                else -> 2
            }
        }

    override fun calculateClassBasedExperienceBonus(
        strength: Strength,
        intelligence: Intelligence,
        dexterity: Dexterity,
        charisma: Charisma,
        wisdom: Wisdom,
        constitution: Constitution
    ): Double = BasePrimeRequisite(strength.score).XPModifier
}