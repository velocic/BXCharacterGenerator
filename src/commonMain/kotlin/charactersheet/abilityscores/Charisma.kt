package charactersheet.abilityscores

class Charisma(var score: Int = 0) {

    val NPCReactionsModifier
        get() = when (score) {
            3 -> -2
            in 4..8 -> -1
            in 9..12 -> 0
            in 13..17 -> 1
            18 -> 2
            else -> 0
        }
    val maxRetainersModifier
        get() = when (score) {
            3 -> 1
            in 4..5 -> 2
            in 6..8 -> 3
            in 9..12 -> 4
            in 13..15 -> 5
            in 16..17 -> 6
            18 -> 7
            else -> 0
        }
    val retainersLoyaltyModifier
        get() = when (score) {
            3 -> 4
            in 4..5 -> 5
            in 6..8 -> 6
            in 9..12 -> 7
            in 13..15 -> 8
            in 16..16 -> 9
            18 -> 10
            else -> 0
        }
}