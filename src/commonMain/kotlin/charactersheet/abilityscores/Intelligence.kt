package charactersheet.abilityscores

class Intelligence(var score: Int = 0) {

    val spokenLanguagesModifier
        get() = when (score) {
            in 3..12 -> 0
            in 13..15 -> 1
            in 16..17 -> 2
            18 -> 3
            else -> 0
        }
    val literacy
        get() = when (score) {
            in 3..5 -> Literacy.ILLITERATE
            in 6..8 -> Literacy.BASIC
            in 9..18 -> Literacy.LITERATE
            else -> Literacy.ILLITERATE
        }
}

enum class Literacy {
    ILLITERATE,
    BASIC,
    LITERATE
}
