package pl.psi.spells;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.psi.GameEngine;
import pl.psi.Hero;
import pl.psi.Point;
import pl.psi.PrimarySkill;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;
import pl.psi.spells.aoe.AOELinearSymmetricalPointSelection;
import pl.psi.spells.aoe.AOERectangularPointSelection;
import pl.psi.spells.aoe.AOERingPointSelection;
import pl.psi.spells.calculator.ReducedSpellCostCalculator;
import pl.psi.spells.object.SpellFactory;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.enums.SpellStatistic;
import pl.psi.spells.object.decorators.AOESpellDecorator;
import pl.psi.spells.spell.UnitAttackSpell;

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

        final UnitAttackSpell attackSpell = new UnitAttackSpell(SpellStatistic.DAMAGING_SPELL);
        attackSpell.cast(hero1, engine.getCreaturePosition(defender));
        assertThat(defender.getCurrentHp()).isEqualTo(95);
    }

    @Test
    void empoweredSpellDmgCalcShouldReturnCorrectValues() {

    }

    @Test
    void rectangularAreaOfEffectSpellShouldDamageDefendersTest() {
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

        final Spell aoeSpell = new AOESpellDecorator(new AOERectangularPointSelection(), new UnitAttackSpell(SpellStatistic.DAMAGING_SPELL));

        final Point c2Position = engine.getCreaturePosition(defender2);
        final Point center = new Point(c2Position.getX() - 1, c2Position.getY());

        aoeSpell.cast(hero1, center);
        assertThat(defender2.getCurrentHp()).isEqualTo(95);
    }

    @Test
    void linearPointSelectionTests() {
        final Hero hero1 = new Hero(List.of(), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));
        final Hero hero2 = new Hero(List.of(), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));

        final GameEngine engine = new GameEngine(hero1, hero2);

        final AOELinearSymmetricalPointSelection horizontalPointSelection = new AOELinearSymmetricalPointSelection(AOELinearSymmetricalPointSelection.Axis.HORIZONTAL);
        final AOELinearSymmetricalPointSelection verticalPointSelection = new AOELinearSymmetricalPointSelection(AOELinearSymmetricalPointSelection.Axis.VERTICAL);

        final Point origin = new Point(2, 2);

        // Test horizontal point selection with size 2 (should expand more to the right)
        List<Point> horizontalPointsSize2 = horizontalPointSelection.getTargetPoints(engine, origin, 2);
        List<Point> expectedHorizontalPointsSize2 = List.of(new Point(2, 2), new Point(3, 2));
        assertThat(horizontalPointsSize2).containsExactlyInAnyOrderElementsOf(expectedHorizontalPointsSize2);

        // Test horizontal point selection with size 3 (should expand -1 to the left and +1 to the right)
        List<Point> horizontalPointsSize3 = horizontalPointSelection.getTargetPoints(engine, origin, 3);
        List<Point> expectedHorizontalPointsSize3 = List.of(new Point(1, 2), new Point(2, 2), new Point(3, 2));
        assertThat(horizontalPointsSize3).containsExactlyInAnyOrderElementsOf(expectedHorizontalPointsSize3);

        // Test vertical point selection with size 2 (should expand more downward)
        List<Point> verticalPointsSize2 = verticalPointSelection.getTargetPoints(engine, origin, 2);
        List<Point> expectedVerticalPointsSize2 = List.of(new Point(2, 2), new Point(2, 3));
        assertThat(verticalPointsSize2).containsExactlyInAnyOrderElementsOf(expectedVerticalPointsSize2);

        // Test vertical point selection with size 3 (should expand -1 up and +1 down)
        List<Point> verticalPointsSize3 = verticalPointSelection.getTargetPoints(engine, origin, 3);
        List<Point> expectedVerticalPointsSize3 = List.of(new Point(2, 1), new Point(2, 2), new Point(2, 3));
        assertThat(verticalPointsSize3).containsExactlyInAnyOrderElementsOf(expectedVerticalPointsSize3);
    }

    @Test
    void ringPointSelectionTest() {
        final Hero hero1 = new Hero(List.of(), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));
        final Hero hero2 = new Hero(List.of(), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));

        final GameEngine engine = new GameEngine(hero1, hero2);

        final AOERingPointSelection ringPointSelection = new AOERingPointSelection();

        final Point origin = new Point(5, 5);

        List<Point> expectedPoints = List.of(
                new Point(4, 4), new Point(5, 4), new Point(6, 4),  // top row
                new Point(4, 6), new Point(5, 6), new Point(6, 6),  // bottom row
                new Point(4, 5), new Point(6, 5)                   // left and right columns
        );

        List<Point> actualPoints = ringPointSelection.getTargetPoints(engine, origin, 1);
        assertThat(actualPoints).containsExactlyInAnyOrderElementsOf(expectedPoints);
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
        assertThat(spellbook.hasSpell(spellC)).isFalse();
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

        hero2.getSpellbook().castSpell(hero2.getSpellbook().getSpell(SpellStatistic.DAMAGING_SPELL.getSpellId()), hero2, cPosition);
        hero1.getSpellbook().castSpell(hero1.getSpellbook().getSpell(SpellStatistic.DAMAGING_SPELL.getSpellId()), hero1, c2Position);

        // Hero2 ma spelle Air Magic i innych szkol magii wiec jego koszt obniza sie w zaleznosci od levela castowanego spella.
        assertThat(hero2.getMana()).isEqualTo(1);

        // Hero1 ich nie ma wiec jego koszt sie nie obniza.
        assertThat(hero1.getMana()).isEqualTo(0);
    }


}
