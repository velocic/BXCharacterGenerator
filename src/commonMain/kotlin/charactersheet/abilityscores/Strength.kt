package charactersheet.abilityscores

class Strength(var score: Int = 0) {

    val meleeModifier
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
    val openDoorsModifier
        get() = when (score) {
            in 3..8 -> 1..6
            in 9..12 -> 2..6
            in 13..15 -> 3..6
            in 16..17 -> 4..6
            18 -> 5..6
            else -> 0..0
        }
}
