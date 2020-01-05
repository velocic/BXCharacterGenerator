package charactersheet.abilityscores

class BasePrimeRequisite(var score: Int = 0) {

    val XPModifier
        get() = when (score) {
            in 3..5 -> .8
            in 6..8 -> .9
            in 9..12 -> 1.0
            in 13..15 -> 1.05
            in 16..18 -> 1.1
            else -> 0.0
        }
}