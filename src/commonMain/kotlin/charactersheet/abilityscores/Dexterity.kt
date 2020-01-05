package charactersheet.abilityscores

class Dexterity(var score: Int = 0) {

    val armorClassModifier
        get() = when (score) {
            3 -> -3
            in 4..5 -> -2
            in 6..8 -> -1
            in 9..12 -> 0
            in 13..15 -> 1
            in 16..17 -> 2
            18 -> 3
            else -> 0
        }
    val missileModifier
        get() = when (score) {
            3 -> -3
            in 4..5 -> -2
            in 6..8 -> -1
            in 9..12 -> 0
            in 13..15 -> 1
            in 16..17 -> 2
            18 -> 3
            else -> 0
        }
    val initiativeModifier
        get() = when (score) {
            3 -> -2
            in 4..8 -> -1
            in 9..12 -> 0
            in 13..17 -> 1
            18 -> 2
            else -> 0
        }
}