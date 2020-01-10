package charactersheet.characterclass

import charactersheet.Language
import charactersheet.abilityscores.AbilityScores
import charactersheet.equipment.Armor
import charactersheet.equipment.Weapon
import charactersheet.equipment.allBasicArmor

abstract class CharacterClass(
    val primeRequisite: AbilityScores.Type,
    val hitDice: IntRange,
    val isShieldAllowed: Boolean,
    val classAbilities: List<SpecialAbility>,
    val progression: List<ProgressionRow>,
    val requirement: List<AbilityScoreRequirement> = listOf(),
    val allowedArmor: List<Armor> = allBasicArmor,
    val allowedWeapons: List<Weapon> = listOf(),
    val baseLanguages: List<Language> = listOf()
) {
    data class AbilityScoreRequirement(
        val abilityScoreType: AbilityScores.Type,
        val minimumAllowedScore: Int
    )

    data class ProgressionRow(
        val XP: Int,
        val HD: Pair<Int, IntRange>,
        val THAC0: Int,
        val ascendingACAttackBonus: Int,
        val savingThrows: SavingThrows,
        val spells: List<Int>?
    ) {
        data class SavingThrows(
            val deathOrPoison: Int,
            val wands: Int,
            val paralysisOrPetrify: Int,
            val breathAttacks: Int,
            val spellsRodsAndStaves: Int
        )
    }

    data class SpecialAbility(
        val minimumLevelRequired: Int,
        val name: String
    )
}

