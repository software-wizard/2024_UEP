package pl.psi.spells.spell;

import pl.psi.Hero;
import pl.psi.Point;
import pl.psi.creatures.Creature;
import pl.psi.creatures.DamageValueObject;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;
import pl.psi.spells.calculator.ReducedSpellCostCalculator;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.interfaces.SpellStatisticIf;
import pl.psi.spells.calculator.EmpoweredSpellDamageCalculator;

import java.util.Optional;

public class UnitAttackSpell extends Spell {
    private final EmpoweredSpellDamageCalculator damageCalculator;
    public UnitAttackSpell(final SpellStatisticIf stats) {
        super(stats, new ReducedSpellCostCalculator(stats));
        this.damageCalculator = new EmpoweredSpellDamageCalculator(stats);
    }

    @Override
    public boolean canCast(Hero caster, Point targetPoint) {
        Optional<Creature> optionalCreature = caster.getParentEngine().getCreature(targetPoint);
        return optionalCreature.isPresent();
    }

    @Override
    public void cast(Hero caster, Point targetPoint) {
        Optional<Creature> optCreature =  caster.getParentEngine().getCreature(targetPoint);
        if (optCreature.isEmpty()) return;

        Creature creature = optCreature.get();

        int damage = damageCalculator.calculateDamage(caster, creature);
        creature.getDamageApplier().applyDamage(new DamageValueObject(damage, AttackTypeEnum.SPELL, CreatureTypeEnum.UNKNOWN), creature);
    }
}
