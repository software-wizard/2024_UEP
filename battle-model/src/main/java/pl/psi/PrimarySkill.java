package pl.psi;

import lombok.Getter;

@Getter
public class PrimarySkill {
    private final int attack;
    private final int defense;
    private final int spellPower;
    private final int knowledge;
    public PrimarySkill(int aAttack, int aDefense, int aSpellPower, int aKnowledge) {
        this.attack = aAttack;
        this.defense = aDefense;
        this.spellPower = aSpellPower;
        this.knowledge = aKnowledge;
    }
}
