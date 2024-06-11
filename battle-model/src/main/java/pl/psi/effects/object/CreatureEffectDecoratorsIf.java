package pl.psi.effects.object;

import pl.psi.creatures.CreatureStatisticIf;
import pl.psi.creatures.DamageApplier;

public interface CreatureEffectDecoratorsIf {
    CreatureStatisticIf getStatisticDecorator();
    DamageApplier getDamageApplierDecorator();
}
