package charactersheet.abilityscores

class BasePrimeRequisite(var score: Int = 0) {

    val XPModifier
        get() = when (score) {
            in 3..5 -> -.2
            in 6..8 -> -.1
            in 9..12 -> 0.0
            in 13..15 -> .05
            in 16..18 -> .1
            else -> 0.0
        }
}