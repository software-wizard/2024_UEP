package pl.psi.spells;

import pl.psi.Hero;

public class SpellCost {
    public int reducedCost(Hero aHero, SpellStatistic aSpell){
        if ((aHero.getSpellbook().hasSpell("Air Magic") && aSpell.getSchool().equals(SpellSchool.AIR)) ||
                (aHero.getSpellbook().hasSpell("Earth Magic") && aSpell.getSchool().equals(SpellSchool.EARTH)) ||
                (aHero.getSpellbook().hasSpell("Fire Magic") && aSpell.getSchool().equals(SpellSchool.FIRE)) ||
                (aHero.getSpellbook().hasSpell("Water Magic") && aSpell.getSchool().equals(SpellSchool.WATER)) ||
                ((aHero.getSpellbook().hasSpell("Air Magic") || aHero.getSpellbook().hasSpell("Earth Magic") || aHero.getSpellbook().hasSpell("Fire Magic") || aHero.getSpellbook().hasSpell("Water Magic")) && aSpell.getSchool().equals(SpellSchool.ALL))){
            return aSpell.getCost()-aSpell.getLevel();
        }
    }
}
