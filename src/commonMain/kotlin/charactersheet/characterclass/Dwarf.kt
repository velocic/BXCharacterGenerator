package charactersheet.characterclass

import charactersheet.Language
import charactersheet.abilityscores.AbilityScores
import charactersheet.equipment.Weapon
import charactersheet.equipment.allBasicArmor
import charactersheet.equipment.allBasicWeapons

class Dwarf : CharacterClass(
    AbilityScores.Type.STR,
    1..8,
    true,
    listOf(
        SpecialAbility(1, "Detect Construction Tricks: 2-in-6"),
        SpecialAbility(1, "Detect Room Traps: 2-in-6"),
        SpecialAbility(1, "Infravision: 60'"),
        SpecialAbility(1, "Listening at Doors: 2-in-6"),
        SpecialAbility(9, "Create an Underground Stronghold")
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

}