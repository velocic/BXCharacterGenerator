package charactersheet.abilityscores

class AbilityScores {
    var strength = Strength()
    var intelligence = Intelligence()
    var wisdom = Wisdom()
    var dexterity = Dexterity()
    var constitution = Constitution()
    var charisma = Charisma()

    init {
        roll3d6InOrder()
    }

    fun roll3d6InOrder() {
        strength.score = (1..6).random() + (1..6).random() + (1..6).random()
        intelligence.score = (1..6).random() + (1..6).random() + (1..6).random()
        wisdom.score = (1..6).random() + (1..6).random() + (1..6).random()
        dexterity.score = (1..6).random() + (1..6).random() + (1..6).random()
        constitution.score = (1..6).random() + (1..6).random() + (1..6).random()
        charisma.score = (1..6).random() + (1..6).random() + (1..6).random()
    }

    enum class Type {
        STR, INT, WIS, DEX, CON, CHA
    }
}
