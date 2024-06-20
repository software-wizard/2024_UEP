package pl.psi.spells.calculator;

import pl.psi.Hero;
import pl.psi.spells.Spellbook;

public interface SpellCostCalculatorIf {
    int getCost(Spellbook spellbook);
}
