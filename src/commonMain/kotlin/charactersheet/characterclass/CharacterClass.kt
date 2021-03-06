package charactersheet.characterclass

import charactersheet.Language
import charactersheet.abilityscores.*
import charactersheet.equipment.Armor
import charactersheet.equipment.Weapon
import charactersheet.equipment.allBasicArmor
import kotlin.math.max

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
    abstract fun calculateMaxHitPoints(classLevel: Int, constitution: Constitution): Int

    open fun baseSavingThrows(classLevel: Int): BasicProgressionRow.SavingThrows =
        progression[classLevel - 1].savingThrows

    open fun magicSavingThrows(classLevel: Int, wisdom: Wisdom): BasicProgressionRow.SavingThrows {
        val baseSaves = baseSavingThrows(classLevel)

        return BasicProgressionRow.SavingThrows(
            max(baseSaves.deathOrPoison - wisdom.magicSavesModifier, 1),
            max(baseSaves.wands - wisdom.magicSavesModifier, 1),
            max(baseSaves.paralysisOrPetrify - wisdom.magicSavesModifier, 1),
            baseSaves.breathAttacks,
            max(baseSaves.spellsRodsAndStaves - wisdom.magicSavesModifier, 1)
        )
    }

    open fun calculateAscendingAttackBonusesForWeapon(classLevel: Int, weapon: Weapon, strength: Strength, dexterity: Dexterity): AttackBonuses {
        val baseAttackBonus = progression[classLevel - 1].ascendingACAttackBonus
        val meleeAttackBonus = if (Weapon.Qualities.MELEE in weapon.qualities) baseAttackBonus + strength.meleeModifier else 0
        val missileAttackBonus = if (Weapon.Qualities.MISSILE in weapon.qualities) baseAttackBonus + dexterity.missileModifier else 0

        return AttackBonuses(meleeAttackBonus, missileAttackBonus)
    }

    open fun calculateKnownLanguages(intelligence: Intelligence): List<Language> {
        val numAdditionalLanguages = intelligence.spokenLanguagesModifier
        val extraLanguages = mutableListOf<Language>()

        if (numAdditionalLanguages == 0) {
            return baseLanguages
        }

        val languagePool = Language.values()
        while (extraLanguages.size < numAdditionalLanguages) {
            val randomLanguage = languagePool.random()
            if (randomLanguage in extraLanguages) {
                continue
            }
            extraLanguages.add(randomLanguage)
        }

        return baseLanguages + extraLanguages
    }

    open fun calculateIndividualInitiativeBonus(dexterity: Dexterity): Int =
        dexterity.initiativeModifier

    abstract fun calculateClassBasedExperienceBonus(strength: Strength, intelligence: Intelligence, dexterity: Dexterity, charisma: Charisma, wisdom: Wisdom, constitution: Constitution): Double
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
    internal data class SavingThrows(
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

internal data class AttackBonuses(
    val melee: Int,
    val missile: Int
)
