package pl.psi.spells.spell;

import pl.psi.GameEngine;
import pl.psi.Hero;
import pl.psi.Point;
import pl.psi.creatures.Creature;
import pl.psi.creatures.DamageValueObject;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;
import pl.psi.spells.calculator.ReducedSpellCostCalculator;
import pl.psi.spells.calculator.SpellCostCalculatorIf;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.SpellStatisticIf;
import pl.psi.spells.calculator.EmpoweredSpellDamageCalculator;

import java.util.Optional;

public class SingleUnitAttackSpell extends Spell {
    public SingleUnitAttackSpell(final SpellStatisticIf stats) {
        super(stats, new ReducedSpellCostCalculator(stats));
    }

    @Override
    public boolean canCast(Hero caster, Point targetPoint) {
        Optional<Creature> optionalCreature =  GameEngine.getInstance().getCreature(targetPoint);
        return optionalCreature.isPresent();
    }

    @Override
    public void cast(Hero caster, Point targetPoint) {
        EmpoweredSpellDamageCalculator damageCalc = new EmpoweredSpellDamageCalculator(
                this.getStats().getBaseDmg(), this.getStats().getPowerMultiplier(),
                caster.getPrimarySkills().getSpellPower()
        );

        Creature creature =  GameEngine.getInstance().getCreature(targetPoint).get();

        int damage = damageCalc.calculateDamage(null, creature);
        creature.getDamageApplier().applyDamage(new DamageValueObject(damage, AttackTypeEnum.SPELL, CreatureTypeEnum.UNKNOWN));
    }
}
