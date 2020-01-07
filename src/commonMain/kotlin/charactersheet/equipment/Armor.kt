package charactersheet.equipment

val leatherArmor = Armor("Leather", 7, 12, 20, 200)
val chainMailArmor = Armor("Chainmail", 5, 14, 40, 400)
val plateMailArmor = Armor("Plate Mail", 3, 16, 60, 500)

val basicAvailableArmor = listOf(leatherArmor, chainMailArmor, plateMailArmor)
val basicAvailableShield = Shield(1, 10, 100)

data class Armor(
    val type: String,
    val armorClassDescending: Int,
    val armorClassAscending: Int,
    val cost: Int,
    val coinWeight: Int
)

data class Shield(
    val armorClassBonusAscending: Int,
    val cost: Int,
    val coinWeight: Int
) {
    val armorClassBonusDescending
        get() = armorClassBonusAscending * -1
}
