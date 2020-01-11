package charactersheet.characterclass

import charactersheet.Language
import charactersheet.abilityscores.AbilityScores
import charactersheet.equipment.Weapon
import charactersheet.equipment.allBasicArmor
import charactersheet.equipment.allBasicWeapons

internal class Halfling : CharacterClass(
    listOf(AbilityScores.Type.CON, AbilityScores.Type.DEX),
    1..6,
    true,
    listOf(
        SpecialAbility(1, "Defensive Bonus: +2 AC bonus vs large opponents"),
        SpecialAbility(1, "Hiding (woods/undergrowth): 90% chance of success"),
        SpecialAbility(1, "Hiding (dungeons): 2-in-6"),
        SpecialAbility(1, "Initiative Bonus (individual only): +1"),
        SpecialAbility(1, "Listening at Doors: 2-in-6"),
        SpecialAbility(1, "Missile Attack Bonus: +1"),
        SpecialAbility(1, "Create a Halfling Community")
    ),
    listOf(
        BasicProgressionRow(0, 1 to 1..6, 19, 0, BasicProgressionRow.SavingThrows(8, 9, 10, 13, 12)),
        BasicProgressionRow(2000, 2 to 1..6, 19, 0, BasicProgressionRow.SavingThrows(8, 9, 10, 13, 12)),
        BasicProgressionRow(4000, 3 to 1..6, 19, 0, BasicProgressionRow.SavingThrows(8, 9, 10, 13, 12)),
        BasicProgressionRow(8000, 4 to 1..6, 17, 2, BasicProgressionRow.SavingThrows(6, 7, 8, 10, 10)),
        BasicProgressionRow(16000, 5 to 1..6, 17, 2, BasicProgressionRow.SavingThrows(6, 7, 8, 10, 10)),
        BasicProgressionRow(32000, 6 to 1..6, 17, 2, BasicProgressionRow.SavingThrows(6, 7, 8, 10, 10)),
        BasicProgressionRow(64000, 7 to 1..6, 14, 5, BasicProgressionRow.SavingThrows(4, 5, 6, 7, 8)),
        BasicProgressionRow(120000, 8 to 1..6, 14, 5, BasicProgressionRow.SavingThrows(4, 5, 6, 7, 8))
    ),
    requirement = listOf(
        AbilityScoreRequirement(AbilityScores.Type.CON, 9),
        AbilityScoreRequirement(AbilityScores.Type.DEX, 9)
    ),
    allowedArmor = allBasicArmor,
    allowedWeapons = allBasicWeapons.filter { it.type != Weapon.Types.LONGBOW && Weapon.Qualities.TWOHANDED !in it.qualities },
    baseLanguages = listOf(
        Language.ALIGNMENT,
        Language.COMMON,
        Language.HALFLING
    )
) {
    override fun calculateHitPoints(): Int {
        return 0
    }
}