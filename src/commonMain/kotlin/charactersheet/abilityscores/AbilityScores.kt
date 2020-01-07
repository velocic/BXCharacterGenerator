package charactersheet.abilityscores

class AbilityScores {
    var strength = 0
    var intelligence = 0
    var wisdom = 0
    var dexterity = 0
    var constitution = 0
    var charisma = 0

    init {
        roll3d6InOrder()
    }

    fun roll3d6InOrder() {
        strength = (1..6).random() + (1..6).random() + (1..6).random()
        intelligence = (1..6).random() + (1..6).random() + (1..6).random()
        wisdom = (1..6).random() + (1..6).random() + (1..6).random()
        dexterity = (1..6).random() + (1..6).random() + (1..6).random()
        constitution = (1..6).random() + (1..6).random() + (1..6).random()
        charisma = (1..6).random() + (1..6).random() + (1..6).random()
    }

    enum class Type {
        STR, INT, WIS, DEX, CON, CHA
    }
}
