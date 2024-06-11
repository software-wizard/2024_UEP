package pl.psi.effects.object;

import pl.psi.creatures.DamageApplier;

public class AbstractDamageApplierEffectDecorator extends DamageApplier {
    private DamageApplier baseApplier;
    private DamageApplier decorated;

    AbstractDamageApplierEffectDecorator(DamageApplier baseApplier, DamageApplier decorated) {
        this.baseApplier = baseApplier;
        this.decorated = decorated;
    }

}
