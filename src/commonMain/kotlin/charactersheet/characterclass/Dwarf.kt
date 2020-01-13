package charactersheet.characterclass

import charactersheet.Language
import charactersheet.abilityscores.*
import charactersheet.equipment.Weapon
import charactersheet.equipment.allBasicArmor
import charactersheet.equipment.allBasicWeapons
import kotlin.math.max

internal class Dwarf : CharacterClass(
    listOf(AbilityScores.Type.STR),
    1..8,
    true,
    listOf(
        SpecialAbility(1, "Detect Construction Tricks: 2-in-6"),
        SpecialAbility(1, "Detect Room Traps: 2-in-6"),
        SpecialAbility(1, "Infravision: 60'"),
        SpecialAbility(1, "Listening at Doors: 2-in-6"),
        SpecialAbility(9, "Create an Underground Stronghold")
    ),
    listOf(
        BasicProgressionRow(0, 1 to 1..8, 19, 0, BasicProgressionRow.SavingThrows(8, 9, 10, 13, 12)),
        BasicProgressionRow(2200, 2 to 1..8, 19, 0, BasicProgressionRow.SavingThrows(8, 9, 10, 13, 12)),
        BasicProgressionRow(4400, 3 to 1..8, 19, 0, BasicProgressionRow.SavingThrows(8, 9, 10, 13, 12)),
        BasicProgressionRow(8800, 4 to 1..8, 17, 2, BasicProgressionRow.SavingThrows(6, 7, 8, 10, 10)),
        BasicProgressionRow(17000, 5 to 1..8, 17, 2, BasicProgressionRow.SavingThrows(6, 7, 8, 10, 10)),
        BasicProgressionRow(35000, 6 to 1..8, 17, 2, BasicProgressionRow.SavingThrows(6, 7, 8, 10, 10)),
        BasicProgressionRow(70000, 7 to 1..8, 14, 5, BasicProgressionRow.SavingThrows(4, 5, 6, 7, 8)),
        BasicProgressionRow(140000, 8 to 1..8, 14, 5, BasicProgressionRow.SavingThrows(4, 5, 6, 7, 8)),
        BasicProgressionRow(270000, 9 to 1..8, 14, 5, BasicProgressionRow.SavingThrows(4, 5, 6, 7, 8)),
        BasicProgressionRow(400000, 9 to 1..8, 12, 7, BasicProgressionRow.SavingThrows(2, 3, 4, 4, 6)),
        BasicProgressionRow(530000, 9 to 1..8, 12, 7, BasicProgressionRow.SavingThrows(2, 3, 4, 4, 6)),
        BasicProgressionRow(660000, 9 to 1..8, 12, 7, BasicProgressionRow.SavingThrows(2, 3, 4, 4, 6))
    ),
    requirement = listOf(AbilityScoreRequirement(AbilityScores.Type.CON, 9)),
    allowedArmor = allBasicArmor,
    allowedWeapons = allBasicWeapons.filter { it.type != Weapon.Types.LONGBOW && Weapon.Qualities.TWOHANDED !in it.qualities },
    baseLanguages = listOf(
        Language.ALIGNMENT,
        Language.COMMON,
        Language.DWARVISH,
        Language.GNOMISH,
        Language.GOBLIN,
        Language.KOBOLD
    )
) {
    override fun calculateMaxHitPoints(classLevel: Int, constitution: Constitution): Int {
        var totalHP = 0

        (1..classLevel).forEach {
            when (it) {
                in 1..9 -> totalHP += max(hitDice.random() + constitution.hitPointsModifier, 1)
                else -> totalHP += 3
            }
        }

        return totalHP
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