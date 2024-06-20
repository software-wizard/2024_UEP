package pl.psi.effects;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.psi.GameEngine;
import pl.psi.Hero;
import pl.psi.PrimarySkill;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;
import pl.psi.creatures.DamageValueObject;
import pl.psi.effects.generic.EffectStatistic;
import pl.psi.enums.AttackTypeEnum;
import pl.psi.enums.CreatureTypeEnum;
import pl.psi.spells.Spellbook;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class EffectTest {
    @Test
    void creatureStatisticModifierEffectTest() {
        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .armor(10)
                        .build())
                .build();

        final Hero hero1 = new Hero(List.of(), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));
        final Hero hero2 = new Hero(List.of(defender), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));

        final GameEngine engine = new GameEngine(hero1, hero2);

        defender.applyEffect(EffectStatistic.DISRUPTING_RAY);

        assertThat(defender.getStats().getArmor()).isEqualTo(7);
    }

    @Test
    void creatureEffectShouldEndAfterXTurnsPassed() {
        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .armor(10)
                        .moveRange(10)
                        .build())
                .build();

        final Hero hero1 = new Hero(List.of(), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));
        final Hero hero2 = new Hero(List.of(defender), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));

        final GameEngine engine = new GameEngine(hero1, hero2);

        defender.applyEffect(EffectStatistic.HASTE);

        assertThat(defender.getStats().getMoveRange()).isEqualTo(13);
        engine.pass();
        assertThat(defender.getStats().getMoveRange()).isEqualTo(10);
    }

    @Test
    void damageApplierModifierEffectShouldWorkProperly() {
        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .armor(10)
                        .moveRange(10)
                        .build())
                .build();

        final Hero hero1 = new Hero(List.of(), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));
        final Hero hero2 = new Hero(List.of(defender), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));

        final GameEngine engine = new GameEngine(hero1, hero2);

        defender.applyEffect(EffectStatistic.ANTI_MAGIC);
        defender.getDamageApplier().applyDamage(new DamageValueObject(20, AttackTypeEnum.SPELL, CreatureTypeEnum.UNKNOWN), defender);
        assertThat(defender.getCurrentHp()).isEqualTo(100);
    }
}
