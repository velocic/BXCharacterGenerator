package charactersheet.equipment

val allBasicWeapons = listOf(
    Weapon(Weapon.Types.BATTLEAXE, 1..8, listOf(Weapon.Qualities.MELEE, Weapon.Qualities.SLOW, Weapon.Qualities.TWOHANDED)),
    Weapon(Weapon.Types.CLUB, 1..4, listOf(Weapon.Qualities.BLUNT, Weapon.Qualities.MELEE)),
    Weapon(Weapon.Types.CROSSBOW, 1..6, listOf(Weapon.Qualities.MISSILE, Weapon.Qualities.RELOAD, Weapon.Qualities.SLOW, Weapon.Qualities.TWOHANDED)),
    Weapon(Weapon.Types.DAGGER, 1..4, listOf(Weapon.Qualities.MELEE, Weapon.Qualities.MISSILE)),
    Weapon(Weapon.Types.HANDAXE, 1..6, listOf(Weapon.Qualities.MELEE, Weapon.Qualities.MISSILE)),
    Weapon(Weapon.Types.HOLYWATERVIAL, 1..8, listOf(Weapon.Qualities.MISSILE, Weapon.Qualities.SPLASH)),
    Weapon(Weapon.Types.JAVELIN, 1..4, listOf(Weapon.Qualities.MISSILE)),
    Weapon(Weapon.Types.LANCE, 1..6, listOf(Weapon.Qualities.CHARGE, Weapon.Qualities.MELEE)),
    Weapon(Weapon.Types.LONGBOW, 1..6, listOf(Weapon.Qualities.MISSILE, Weapon.Qualities.TWOHANDED)),
    Weapon(Weapon.Types.MACE, 1..6, listOf(Weapon.Qualities.BLUNT, Weapon.Qualities.MELEE)),
    Weapon(Weapon.Types.BURNINGOILFLASK, 1..8, listOf(Weapon.Qualities.MISSILE, Weapon.Qualities.SPLASH)),
    Weapon(Weapon.Types.POLEARM, 1..10, listOf(Weapon.Qualities.BRACE, Weapon.Qualities.SLOW, Weapon.Qualities.TWOHANDED)),
    Weapon(Weapon.Types.SHORTBOW, 1..6, listOf(Weapon.Qualities.MISSILE, Weapon.Qualities.TWOHANDED)),
    Weapon(Weapon.Types.SHORTSWORD, 1..6, listOf(Weapon.Qualities.MELEE)),
    Weapon(Weapon.Types.SILVERDAGGER, 1..4, listOf(Weapon.Qualities.MELEE, Weapon.Qualities.MISSILE)),
    Weapon(Weapon.Types.SLING, 1..4, listOf(Weapon.Qualities.BLUNT, Weapon.Qualities.MISSILE)),
    Weapon(Weapon.Types.SPEAR, 1..6, listOf(Weapon.Qualities.BRACE, Weapon.Qualities.MELEE, Weapon.Qualities.MISSILE)),
    Weapon(Weapon.Types.STAFF, 1..4, listOf(Weapon.Qualities.BLUNT, Weapon.Qualities.MELEE, Weapon.Qualities.SLOW, Weapon.Qualities.TWOHANDED)),
    Weapon(Weapon.Types.SWORD, 1..8, listOf(Weapon.Qualities.MELEE)),
    Weapon(Weapon.Types.TORCH, 1..4, listOf(Weapon.Qualities.MELEE)),
    Weapon(Weapon.Types.TWOHANDEDSWORD, 1..10, listOf(Weapon.Qualities.MELEE, Weapon.Qualities.SLOW, Weapon.Qualities.TWOHANDED)),
    Weapon(Weapon.Types.WARHAMMER, 1..6, listOf(Weapon.Qualities.BLUNT, Weapon.Qualities.MELEE))
)

class Weapon(
    val type: Types,
    val nonVariableBaseDamage: IntRange,
    val qualities: List<Qualities>
) {
    val name
        get() = type.asString
    val qualitiesTextBlock: String
        get() = qualities.fold(" ") { result, current ->
            "$result ${if (current == Qualities.MISSILE) getMissileTypeText() else current.asString} "
        }

    private fun getMissileTypeText(): String =
        if (qualities.contains(Qualities.MISSILE)) {
            when (type) {
                Types.CROSSBOW -> "Missile (5'-80'/81'-160'/161'-240')"
                Types.DAGGER -> "Missile (5'-10'/11'-20'/21'-30')"
                Types.HANDAXE -> "Missile (5'-10'/11'-20'/21'-30')"
                Types.HOLYWATERVIAL -> "Missile (5'-10'/11'-30'/31'-50')"
                Types.JAVELIN -> "Missile (5'-30'/31'-60'/61'-90')"
                Types.LONGBOW -> "Missile (5'-70'/71'-140'/141'-210')"
                Types.BURNINGOILFLASK -> "Missile (5'-10'/11'-30'/31'-50')"
                Types.SHORTBOW -> "Missile (5'-50'/51'-100'/101'-150')"
                Types.SILVERDAGGER -> "Missile (5'-10'/11'-20'/21'-30')"
                Types.SLING -> "Missile (5'-40'/41'-80'/81'-160')"
                Types.SPEAR -> "Missile (5'-20'/21'-40'/41'-60')"
                else -> ""
            }
        } else {
            ""
        }

    enum class Types(val asString: String) {
        BATTLEAXE("Battle axe"),
        CLUB("Club"),
        CROSSBOW("Crossbow"),
        DAGGER("Dagger"),
        HANDAXE("Hand axe"),
        HOLYWATERVIAL("Holy water vial"),
        JAVELIN("Javelin"),
        LANCE("Lance"),
        LONGBOW("Long bow"),
        MACE("Mace"),
        BURNINGOILFLASK("Oil flask, burning"),
        POLEARM("Polearm"),
        SHORTBOW("Short bow"),
        SHORTSWORD("Short sword"),
        SILVERDAGGER("Silver dagger"),
        SLING("Sling"),
        SPEAR("Spear"),
        STAFF("Staff"),
        SWORD("Sword"),
        TORCH("Torch"),
        TWOHANDEDSWORD("Two-handed sword"),
        WARHAMMER("Warhammer")
    }

    enum class Qualities(val asString: String) {
        BLUNT("Blunt"),
        BRACE("Brace"),
        CHARGE("Charge"),
        MELEE("Melee"),
        RELOAD("Reload"),
        MISSILE("Missile"),
        SLOW("Slow"),
        SPLASH("Splash"),
        TWOHANDED("Two-handed")
    }
}