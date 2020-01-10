package charactersheet.characterclass

import charactersheet.Language
import charactersheet.abilityscores.AbilityScores
import charactersheet.equipment.Weapon
import charactersheet.equipment.allBasicArmor
import charactersheet.equipment.allBasicWeapons

class Cleric : CharacterClass(
    AbilityScores.Type.WIS,
    1..6,
    true,
    listOf(
        SpecialAbility(1, "Divine Magic"),
        SpecialAbility(1, "Turning the Undead")
    ),
    listOf(
        ProgressionRow(0, 1 to 1..6, 19, 0, ProgressionRow.SavingThrows(11, 12, 14, 16, 15), listOf(0, 0, 0, 0, 0)),
        ProgressionRow(1500, 2 to 1..6, 19, 0, ProgressionRow.SavingThrows(11, 12, 14, 16, 15), listOf(1, 0, 0, 0, 0)),
        ProgressionRow(3000, 3 to 1..6, 19, 0, ProgressionRow.SavingThrows(11, 12, 14, 16, 15), listOf(2, 0, 0, 0, 0)),
        ProgressionRow(6000, 4 to 1..6, 19, 0, ProgressionRow.SavingThrows(11, 12, 14, 16, 15), listOf(2, 1, 0, 0, 0)),
        ProgressionRow(12000, 5 to 1..6, 17, 2, ProgressionRow.SavingThrows(9, 10, 12, 14, 12), listOf(2, 2, 0, 0, 0)),
        ProgressionRow(25000, 6 to 1..6, 17, 2, ProgressionRow.SavingThrows(9, 10, 12, 14, 12), listOf(2, 2, 1, 1, 0)),
        ProgressionRow(50000, 7 to 1..6, 17, 2, ProgressionRow.SavingThrows(9, 10, 12, 14, 12), listOf(2, 2, 2, 1, 1)),
        ProgressionRow(100000, 8 to 1..6, 17, 2, ProgressionRow.SavingThrows(9, 10, 12, 14, 12), listOf(3, 3, 2, 2, 1)),
        ProgressionRow(200000, 9 to 1..6, 14, 5, ProgressionRow.SavingThrows(6, 7, 9, 11, 9), listOf(3, 3, 3, 2, 2)),
        ProgressionRow(300000, 10 to 1..6, 14, 5, ProgressionRow.SavingThrows(6, 7, 9, 11, 9), listOf(4, 4, 3, 3, 2)),
        ProgressionRow(400000, 11 to 1..6, 14, 5, ProgressionRow.SavingThrows(6, 7, 9, 11, 9), listOf(4, 4, 4, 3, 3)),
        ProgressionRow(500000, 12 to 1..6, 14, 5, ProgressionRow.SavingThrows(6, 7, 9, 11, 9), listOf(5, 5, 4, 4, 3)),
        ProgressionRow(600000, 13 to 1..6, 12, 7, ProgressionRow.SavingThrows(3, 5, 7, 8, 7), listOf(5, 5, 5, 4, 4)),
        ProgressionRow(700000, 14 to 1..6, 12, 7, ProgressionRow.SavingThrows(3, 5, 7, 8, 7), listOf(6, 5, 5, 5, 4))
    ),
    allowedArmor = allBasicArmor,
    allowedWeapons = allBasicWeapons.filter { Weapon.Qualities.BLUNT in it.qualities },
    baseLanguages = listOf(
        Language.ALIGNMENT,
        Language.COMMON
    )
) {

}
