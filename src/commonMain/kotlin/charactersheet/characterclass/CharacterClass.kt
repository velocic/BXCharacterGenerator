package charactersheet.characterclass

import charactersheet.Language
import charactersheet.abilityscores.AbilityScores
import charactersheet.equipment.Armor
import charactersheet.equipment.Weapon
import charactersheet.equipment.allBasicArmor

internal abstract class CharacterClass(
    val primeRequisites: List<AbilityScores.Type>,
    val hitDice: IntRange,
    val isShieldAllowed: Boolean,
    val classAbilities: List<SpecialAbility>,
    val progression: List<BasicProgressionRow>,
    val requirement: List<AbilityScoreRequirement> = listOf(),
    val allowedArmor: List<Armor> = allBasicArmor,
    val allowedWeapons: List<Weapon> = listOf(),
    val baseLanguages: List<Language> = listOf()
) {
    abstract fun calculateHitPoints(): Int

}

internal data class AbilityScoreRequirement(
    val abilityScoreType: AbilityScores.Type,
    val minimumAllowedScore: Int
)

internal open class BasicProgressionRow(
    val XP: Int,
    val HD: Pair<Int, IntRange>,
    val THAC0: Int,
    val ascendingACAttackBonus: Int,
    val savingThrows: SavingThrows
) {
    internal class SavingThrows(
        val deathOrPoison: Int,
        val wands: Int,
        val paralysisOrPetrify: Int,
        val breathAttacks: Int,
        val spellsRodsAndStaves: Int
    )
}

internal class SpellcasterProgressionRow(
    XP: Int,
    HD: Pair<Int, IntRange>,
    THAC0: Int,
    ascendingACAttackBonus: Int,
    savingThrows: SavingThrows,
    spells: List<Int>
) : BasicProgressionRow(XP, HD, THAC0, ascendingACAttackBonus, savingThrows)

internal data class SpecialAbility(
    val minimumLevelRequired: Int,
    val name: String
)

