package pl.psi.spells;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.psi.GameEngine;
import pl.psi.Hero;
import pl.psi.Point;
import pl.psi.PrimarySkill;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;
import pl.psi.spells.calculator.EmpoweredSpellDamageCalculator;
import pl.psi.spells.calculator.ReducedSpellCostCalculator;
import pl.psi.spells.object.SpellFactory;
import pl.psi.spells.spellbook.Spellbook;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.SpellStatistic;
import pl.psi.spells.spell.AreaOfEffectAttackSpellDecorator;
import pl.psi.spells.spell.SingleUnitAttackSpell;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SpellTest {

    @Test
    void damagingSpellShouldDamageDefenderTest() {
        final Creature attacker = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();

        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();

        final Hero hero1 = new Hero(List.of(attacker), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));
        final Hero hero2 = new Hero(List.of(defender), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));

        final GameEngine engine = new GameEngine(hero1, hero2);

        final SingleUnitAttackSpell attackSpell = new SingleUnitAttackSpell(SpellStatistic.DAMAGING_SPELL);
        attackSpell.cast(hero1, engine.getCreaturePosition(defender));
        assertThat(defender.getCurrentHp()).isEqualTo(95);
    }

    @Test
    void empoweredSpellDmgCalcShouldReturnCorrectValues() {
        assertThat(new EmpoweredSpellDamageCalculator(10, 10, 2).calculateDamage(null, null)).isEqualTo(30);
    }

    @Test
    void areaOfEffectSpellShouldDamageDefendersTest() {
        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();

        final Creature defender2 = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();

        final Creature defender3 = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();

        final Hero hero1 = new Hero(List.of(), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));
        final Hero hero2 = new Hero(List.of(defender, defender2, defender3), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));

        final GameEngine engine = new GameEngine(hero1, hero2);

        final Spell aoeSpell = new AreaOfEffectAttackSpellDecorator(new SingleUnitAttackSpell(SpellStatistic.DAMAGING_SPELL));

        final Point c2Position = engine.getCreaturePosition(defender2);
        final Point center = new Point(c2Position.getX() - 1, c2Position.getY());

        aoeSpell.cast(hero1, center);
        assertThat(defender2.getCurrentHp()).isEqualTo(95);
    }

    @Test
    void spellFactoryShouldCreateValidSpells() {
        final Spell s = SpellFactory.fromStatistic(SpellStatistic.METEOR_SHOWER);
        assertThat(s.getStats()).isEqualTo(SpellStatistic.METEOR_SHOWER);
    }

    @Test
    void spellFactoryAndSpellPowerScalingTest() {
        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();

        final Hero hero1 = new Hero(List.of(), new PrimarySkill(0, 0, 2, 0), new Spellbook(List.of()));
        final Hero hero2 = new Hero(List.of(defender), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));

        final GameEngine engine = new GameEngine(hero1, hero2);

        final Spell aoeSpell = SpellFactory.fromStatistic(SpellStatistic.MAGIC_ARROW);
        final Point cPosition = engine.getCreaturePosition(defender);

        aoeSpell.cast(hero1, cPosition);
        assertThat(defender.getCurrentHp()).isEqualTo(70);
    }

    @Test
    void spellbookMethodsShouldWorkProperly() {
        final Spell spellA = SpellFactory.fromStatistic(SpellStatistic.ICE_BOLT);
        final Spell spellB = SpellFactory.fromStatistic(SpellStatistic.FIREBALL);
        final Spell spellC = SpellFactory.fromStatistic(SpellStatistic.DAMAGING_SPELL);

        final Spellbook spellbook = new Spellbook(List.of(
                spellA, spellB
        ));

        assertThat(spellbook.hasSpell(spellA)).isTrue();
        assertThat(spellbook.getSpell(spellA.getName())).isEqualTo(spellA);
        assertThat(spellbook.hasSpell(spellC.getName())).isFalse();
    }

    @Test
    void reducedSpellCostCalculatorShouldReturnCorrectValues() {
        final ReducedSpellCostCalculator reducedSpellCostCalculator = new ReducedSpellCostCalculator(SpellStatistic.DAMAGING_SPELL);
        final Hero hero = new Hero(List.of(), new PrimarySkill(0, 0, 0, 0), new Spellbook(SpellFactory.all()));


        assertThat(reducedSpellCostCalculator.getCost(hero)).isEqualTo(9);
    }

    @Test
    void spellCostsShouldWorkProperly() {
        final Creature defender = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();

        final Creature defender2 = new Creature.Builder().statistic(CreatureStats.builder()
                        .maxHp(100)
                        .damage(Range.closed(10, 10))
                        .build())
                .build();

        final Hero hero1 = new Hero(List.of(defender2), new PrimarySkill(0, 0, 2, 1), new Spellbook(List.of(
                SpellFactory.fromStatistic(SpellStatistic.DAMAGING_SPELL)
        )));
        final Hero hero2 = new Hero(List.of(defender), new PrimarySkill(0, 0, 0, 1), new Spellbook(SpellFactory.all()));

        final GameEngine engine = new GameEngine(hero1, hero2);

        final Point cPosition = engine.getCreaturePosition(defender);
        final Point c2Position = engine.getCreaturePosition(defender2);

        hero2.getSpellbook().castSpell("Damaging Spell", hero2, cPosition);
        hero1.getSpellbook().castSpell("Damaging Spell", hero1, c2Position);

        // Hero2 ma spelle Air Magic i innych szkol magii wiec jego koszt obniza sie w zaleznosci od levela castowanego spella.
        assertThat(hero2.getMana()).isEqualTo(1);

        // Hero1 ich nie ma wiec jego koszt sie nie obniza.
        assertThat(hero1.getMana()).isEqualTo(0);
    }
}
