package charactersheet.characterclass

import charactersheet.Language
import charactersheet.abilityscores.*
import charactersheet.equipment.allBasicArmor
import charactersheet.equipment.allBasicWeapons
import kotlin.math.max

internal class Elf : CharacterClass(
    listOf(AbilityScores.Type.INT, AbilityScores.Type.STR),
    1..6,
    true,
    listOf(
        SpecialAbility(1, "Arcane Magic"),
        SpecialAbility(1, "Detect Secret Doors: 2-in-6"),
        SpecialAbility(1, "Immunity to Ghoul Paralysis"),
        SpecialAbility(1, "Infravision"),
        SpecialAbility(1, "Listening at Doors: 2-in-6"),
        SpecialAbility(9, "Create a Forest Stronghold")
    ),
    listOf(
        SpellcasterProgressionRow(0, 1 to 1..6, 19, 0, BasicProgressionRow.SavingThrows(12, 13, 13, 15, 15), listOf(1, 0, 0, 0, 0)),
        SpellcasterProgressionRow(4000, 2 to 1..6, 19, 0, BasicProgressionRow.SavingThrows(12, 13, 13, 15, 15), listOf(2, 0, 0, 0, 0)),
        SpellcasterProgressionRow(8000, 3 to 1..6, 19, 0, BasicProgressionRow.SavingThrows(12, 13, 13, 15, 15), listOf(2, 1, 0, 0, 0)),
        SpellcasterProgressionRow(16000, 4 to 1..6, 17, 2, BasicProgressionRow.SavingThrows(10, 11, 11, 13, 12), listOf(2, 2, 0, 0, 0)),
        SpellcasterProgressionRow(32000, 5 to 1..6, 17, 2, BasicProgressionRow.SavingThrows(10, 11, 11, 13, 12), listOf(2, 2, 1, 0, 0)),
        SpellcasterProgressionRow(64000, 6 to 1..6, 17, 2, BasicProgressionRow.SavingThrows(10, 11, 11, 13, 12), listOf(2, 2, 2, 0, 0)),
        SpellcasterProgressionRow(120000, 7 to 1..6, 14, 5, BasicProgressionRow.SavingThrows(8, 9, 9, 10, 10), listOf(3, 2, 2, 1, 0)),
        SpellcasterProgressionRow(250000, 8 to 1..6, 14, 5, BasicProgressionRow.SavingThrows(8, 9, 9, 10, 10), listOf(3, 3, 2, 2, 0)),
        SpellcasterProgressionRow(400000, 9 to 1..6, 14, 5, BasicProgressionRow.SavingThrows(8, 9, 9, 10, 10), listOf(3, 3, 3, 2, 1)),
        SpellcasterProgressionRow(600000, 9 to 1..6, 12, 7, BasicProgressionRow.SavingThrows(6, 7, 8, 8, 8), listOf(3, 3, 3, 3, 2))
    ),
    requirement = listOf(AbilityScoreRequirement(AbilityScores.Type.INT, 9)),
    allowedArmor = allBasicArmor,
    allowedWeapons = allBasicWeapons,
    baseLanguages = listOf(
        Language.ALIGNMENT,
        Language.COMMON,
        Language.ELVISH,
        Language.GNOLL,
        Language.HOBGOBLIN,
        Language.ORCISH
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
    ): Double = if (intelligence.score >= 16 && strength.score >= 13) {
        .1
    } else if (intelligence.score >= 13 && strength.score >= 13) {
        .05
    } else {
        0.0
    }
}

