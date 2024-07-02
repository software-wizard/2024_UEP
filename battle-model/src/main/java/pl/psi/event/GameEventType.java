package pl.psi.event;

public enum GameEventType {
    CREATURE_DAMAGED("CREATURE_DAMAGED"),
    CREATURE_EFFECT_APPLIED("CREATURE_EFFECT_APPLIED"),
    CREATURE_MOVED("CREATURE_MOVED"),
    BOARD_EFFECT_APPLIED("BOARD_EFFECT_APPLIED"),
    SPELL_CASTED("SPELL_CASTED"),
    BATTLE_ENDED("BATTLE_ENDED")

    ;
    private final String name;

    GameEventType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
