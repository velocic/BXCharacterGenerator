package charactersheet.abilityscores

class Wisdom(var score: Int = 0) {

    val magicSavesModifier
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
}