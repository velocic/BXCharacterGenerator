package charactersheet.characterclass

import charactersheet.Language
import charactersheet.abilityscores.AbilityScores
import charactersheet.equipment.Weapon
import charactersheet.equipment.allBasicWeapons

internal class MagicUser : CharacterClass(
    listOf(AbilityScores.Type.INT),
    1..4,
    false,
    listOf(
        SpecialAbility(1, "Arcane Magic"),
        SpecialAbility(11, "Create a Stronghold")
    ),
    listOf(
        SpellcasterProgressionRow(0, 1 to 1..4, 19, 0, BasicProgressionRow.SavingThrows(13, 14, 13, 16, 15), listOf(1, 0, 0, 0, 0, 0)),
        SpellcasterProgressionRow(2500, 2 to 1..4, 19, 0, BasicProgressionRow.SavingThrows(13, 14, 13, 16, 15), listOf(2, 0, 0, 0, 0, 0)),
        SpellcasterProgressionRow(5000, 3 to 1..4, 19, 0, BasicProgressionRow.SavingThrows(13, 14, 13, 16, 15), listOf(2, 1, 0, 0, 0, 0)),
        SpellcasterProgressionRow(10000, 4 to 1..4, 19, 0, BasicProgressionRow.SavingThrows(13, 14, 13, 16, 15), listOf(2, 2, 0, 0, 0, 0)),
        SpellcasterProgressionRow(20000, 5 to 1..4, 19, 0, BasicProgressionRow.SavingThrows(13, 14, 13, 16, 15), listOf(2, 2, 1, 0, 0, 0)),
        SpellcasterProgressionRow(40000, 6 to 1..4, 17, 2, BasicProgressionRow.SavingThrows(11, 12, 11, 14, 12), listOf(2, 2, 2, 0, 0, 0)),
        SpellcasterProgressionRow(80000, 7 to 1..4, 17, 2, BasicProgressionRow.SavingThrows(11, 12, 11, 14, 12), listOf(3, 2, 2, 1, 0, 0)),
        SpellcasterProgressionRow(150000, 8 to 1..4, 17, 2, BasicProgressionRow.SavingThrows(11, 12, 11, 14, 12), listOf(3, 3, 2, 2, 0, 0)),
        SpellcasterProgressionRow(300000, 9 to 1..4, 17, 2, BasicProgressionRow.SavingThrows(11, 12, 11, 14, 12), listOf(3, 3, 3, 2, 1, 0)),
        SpellcasterProgressionRow(450000, 9 to 1..4, 17, 2, BasicProgressionRow.SavingThrows(11, 12, 11, 14, 12), listOf(3, 3, 3, 3, 2, 0)),
        SpellcasterProgressionRow(600000, 9 to 1..4, 14, 5, BasicProgressionRow.SavingThrows(8, 9, 8, 11, 8), listOf(4, 3, 3, 3, 2, 1)),
        SpellcasterProgressionRow(750000, 9 to 1..4, 14, 5, BasicProgressionRow.SavingThrows(8, 9, 8, 11, 8), listOf(4, 4, 3, 3, 3, 2)),
        SpellcasterProgressionRow(900000, 9 to 1..4, 14, 5, BasicProgressionRow.SavingThrows(8, 9, 8, 11, 8), listOf(4, 4, 4, 3, 3, 3)),
        SpellcasterProgressionRow(1050000, 9 to 1..4, 14, 5, BasicProgressionRow.SavingThrows(8, 9, 8, 11, 8), listOf(4, 4, 4, 4, 3, 3))
    ),
    allowedArmor = listOf(),
    allowedWeapons = allBasicWeapons.filter { it.type == Weapon.Types.DAGGER || it.type == Weapon.Types.SILVERDAGGER },
    baseLanguages = listOf(
        Language.ALIGNMENT,
        Language.COMMON
    )
) {
    override fun calculateHitPoints(): Int {
        return 0
    }
}