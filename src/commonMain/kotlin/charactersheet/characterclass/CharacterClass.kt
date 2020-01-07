package charactersheet.characterclass

import charactersheet.abilityscores.AbilityScores

abstract class CharacterClass(
    val primeRequisite: AbilityScores.Type,
    val hitDice: IntRange,
    val requirement: List<AbilityScoreRequirement> = listOf()
) {
}

data class AbilityScoreRequirement(
    val abilityScoreType: AbilityScores.Type,
    val minimumAllowedScore: Int
)
