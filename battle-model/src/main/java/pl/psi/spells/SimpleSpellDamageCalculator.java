package pl.psi.spells;

import pl.psi.Hero;
import pl.psi.creatures.Creature;
import pl.psi.creatures.DamageCalculatorIf;

public class SimpleSpellDamageCalculator implements DamageCalculatorIf {
    final int damage;

    public SimpleSpellDamageCalculator(final int aDamage) {
        this.damage = aDamage;
    }

    @Override
    public int calculateDamage(Creature aAttacker, Creature aDefender) {
        return this.damage;
    }

    public int spellDamage(Hero aHero, SpellStatistic aSpell){
        return aSpell.getBaseDmg()+aSpell.getPowerMultiplier()*aHero.getKnowledge();
    }
}
