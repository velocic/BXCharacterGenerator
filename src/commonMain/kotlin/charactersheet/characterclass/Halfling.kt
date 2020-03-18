package charactersheet.characterclass

import charactersheet.Language
import charactersheet.abilityscores.*
import charactersheet.equipment.Weapon
import charactersheet.equipment.allBasicArmor
import charactersheet.equipment.allBasicWeapons
import kotlin.math.max

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
    allowedWeapons = allBasicWeapons.filter { it.type != Weapon.Types.LONGBOW && it.type != Weapon.Types.TWOHANDEDSWORD },
    baseLanguages = listOf(
        Language.ALIGNMENT,
        Language.COMMON,
        Language.HALFLING
    )
) {
    override fun calculateMaxHitPoints(classLevel: Int, constitution: Constitution): Int =
        (1..classLevel).fold(0) { result, current ->
            result + when (current) {
                in 1..8 -> max(hitDice.random() + constitution.hitPointsModifier, 1)
                else -> 0
            }
        }

    override fun calculateAscendingAttackBonusesForWeapon(
        classLevel: Int,
        weapon: Weapon,
        strength: Strength,
        dexterity: Dexterity
    ): AttackBonuses = super.calculateAscendingAttackBonusesForWeapon(
        classLevel,
        weapon,
        strength,
        dexterity
    ).let { (meleeBonus, missileBonus) ->
        val modifiedMissileBonus = if (missileBonus == 0) { 0 } else { missileBonus + 1}
        AttackBonuses(meleeBonus, modifiedMissileBonus)
    }

    override fun calculateClassBasedExperienceBonus(
        strength: Strength,
        intelligence: Intelligence,
        dexterity: Dexterity,
        charisma: Charisma,
        wisdom: Wisdom,
        constitution: Constitution
    ): Double = if (dexterity.score >= 16 && strength.score >= 16) {
        .1
    } else if (dexterity.score >= 13 || strength.score >= 13) {
        .05
    } else {
        0.0
    }
}