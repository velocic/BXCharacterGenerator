package charactersheet.characterclass

import charactersheet.Language
import charactersheet.abilityscores.AbilityScores
import charactersheet.equipment.allBasicWeapons
import charactersheet.equipment.leatherArmor

internal class Thief : CharacterClass(
    listOf(AbilityScores.Type.DEX),
    1..4,
    false,
    listOf(
        SpecialAbility(1, "Back-stab"),
        SpecialAbility(4, "Read Languages"),
        SpecialAbility(10, "Arcane Scroll Use: 10% chance of error"),
        SpecialAbility(1, "Thief Skills")
    ),
    listOf(
        ThiefProgressionRow(0, 1 to 1..4, 19, 0, BasicProgressionRow.SavingThrows(13, 14, 13, 16, 15), ThiefSkillRow(87, 10, 1..2, 10, 20, 15, 20)),
        ThiefProgressionRow(1200, 2 to 1..4, 19, 0, BasicProgressionRow.SavingThrows(13, 14, 13, 16, 15), ThiefSkillRow(88, 15, 1..2, 15, 25, 20, 25)),
        ThiefProgressionRow(2400, 3 to 1..4, 19, 0, BasicProgressionRow.SavingThrows(13, 14, 13, 16, 15), ThiefSkillRow(89, 20, 1..3, 20, 30, 25, 30)),
        ThiefProgressionRow(4800, 4 to 1..4, 19, 0, BasicProgressionRow.SavingThrows(13, 14, 13, 16, 15), ThiefSkillRow(90, 25, 1..3, 25, 35, 30, 35)),
        ThiefProgressionRow(9600, 5 to 1..4, 17, 2, BasicProgressionRow.SavingThrows(12, 13, 11, 14, 13), ThiefSkillRow(91, 30, 1..3, 30, 40, 35, 40)),
        ThiefProgressionRow(20000, 6 to 1..4, 17, 2, BasicProgressionRow.SavingThrows(12, 13, 11, 14, 13), ThiefSkillRow(92, 40, 1..3, 36, 45, 45, 45)),
        ThiefProgressionRow(40000, 7 to 1..4, 17, 2, BasicProgressionRow.SavingThrows(12, 13, 11, 14, 13), ThiefSkillRow(93, 50, 1..4, 45, 55, 55, 55)),
        ThiefProgressionRow(80000, 8 to 1..4, 17, 2, BasicProgressionRow.SavingThrows(12, 13, 11, 14, 13), ThiefSkillRow(94, 60, 1..4, 55, 65, 65, 65)),
        ThiefProgressionRow(160000, 9 to 1..4, 14, 5, BasicProgressionRow.SavingThrows(10, 11, 9, 12, 10), ThiefSkillRow(95, 70, 1..4, 65, 75, 75, 75)),
        ThiefProgressionRow(280000, 9 to 1..4, 14, 5, BasicProgressionRow.SavingThrows(10, 11, 9, 12, 10), ThiefSkillRow(96, 80, 1..4, 75, 85, 85, 85)),
        ThiefProgressionRow(400000, 9 to 1..4, 14, 5, BasicProgressionRow.SavingThrows(10, 11, 9, 12, 10), ThiefSkillRow(97, 90, 1..5, 85, 95, 95, 95)),
        ThiefProgressionRow(520000, 9 to 1..4, 14, 5, BasicProgressionRow.SavingThrows(10, 11, 9, 12, 10), ThiefSkillRow(98, 95, 1..5, 90, 96, 96, 105)),
        ThiefProgressionRow(640000, 9 to 1..4, 12, 7, BasicProgressionRow.SavingThrows(8, 9, 7, 10, 8), ThiefSkillRow(99, 97, 1..5, 95, 98, 97, 115)),
        ThiefProgressionRow(760000, 9 to 1..4, 12, 7, BasicProgressionRow.SavingThrows(8, 9, 7, 10, 8), ThiefSkillRow(99, 99, 1..5, 99, 99 99, 125))
    ),
    allowedArmor = listOf(leatherArmor),
    allowedWeapons = allBasicWeapons,
    baseLanguages = listOf(
        Language.ALIGNMENT,
        Language.COMMON
    )
) {
    override fun calculateHitPoints(): Int {
        return 0
    }
}

internal class ThiefProgressionRow(
    XP: Int,
    HD: Pair<Int, IntRange>,
    THAC0: Int,
    ascendingACAttackBonus: Int,
    savingThrows: SavingThrows,
    val skills: ThiefSkillRow
) : BasicProgressionRow(XP, HD, THAC0, ascendingACAttackBonus, savingThrows)

internal data class ThiefSkillRow(
    val climbSheerSurfaces: Int,
    val findOrRemoveTreasureTraps: Int,
    val hearNoise: IntRange,
    val hideInShadows: Int,
    val moveSilently: Int,
    val openLocks: Int,
    val pickPockets: Int
)
