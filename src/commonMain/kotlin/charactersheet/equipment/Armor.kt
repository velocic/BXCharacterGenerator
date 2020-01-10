package charactersheet.equipment

val leatherArmor = Armor(Armor.Types.LEATHER, 7, 12, 20, 200)
val chainMailArmor = Armor(Armor.Types.CHAINMAIL, 5, 14, 40, 400)
val plateMailArmor = Armor(Armor.Types.PLATEMAIL, 3, 16, 60, 500)

val allBasicArmor = listOf(leatherArmor, chainMailArmor, plateMailArmor)
val basicShield = Shield(1, 10, 100)

data class Armor(
    val type: Types,
    val armorClassDescending: Int,
    val armorClassAscending: Int,
    val cost: Int,
    val coinWeight: Int
) {
    val name
        get() = type.asString

    enum class Types(val asString: String) {
        LEATHER("Leather"),
        CHAINMAIL("Chainmail"),
        PLATEMAIL("Plate Mail")
    }
}

data class Shield(
    val armorClassBonusAscending: Int,
    val cost: Int,
    val coinWeight: Int
) {
    val armorClassBonusDescending
        get() = armorClassBonusAscending * -1
}
