package pl.psi.spells;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pl.psi.*;
import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;
import pl.psi.spells.aoe.AOELinearSymmetricalPointSelection;
import pl.psi.spells.aoe.AOERectangularPointSelection;
import pl.psi.spells.aoe.AOERingPointSelection;
import pl.psi.spells.calculator.EmpoweredSpellDamageCalculator;
import pl.psi.spells.calculator.ReducedSpellCostCalculator;
import pl.psi.spells.object.SpellFactory;
import pl.psi.spells.object.Spell;
import pl.psi.spells.object.enums.SpellExpertise;
import pl.psi.spells.object.enums.SpellSchool;
import pl.psi.spells.object.enums.SpellStatistic;
import pl.psi.spells.object.decorators.AOESpellDecorator;
import pl.psi.spells.spell.UnitAttackSpell;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
@Disabled
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

        final Map<SpellSchool, SpellExpertise> spellSchoolMasteries = new EnumMap<>(Map.ofEntries(
                Map.entry(SpellSchool.AIR, SpellExpertise.valueOf(2))
        ));

        final Hero hero1 = new Hero(List.of(attacker), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));
        final Hero hero2 = new Hero(List.of(defender), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));

        final GameEngine engine = new GameEngine(hero1, hero2);

        final UnitAttackSpell attackSpell = new UnitAttackSpell(SpellStatistic.DAMAGING_SPELL);
        attackSpell.cast(hero1, new Location(engine.getCreatureLocation(defender), engine.getBoard()));
        assertThat(defender.getCurrentHp()).isEqualTo(95);
    }

    @Test
    void empoweredSpellDmgCalcShouldReturnCorrectValues() {
        // Implementation of the test case

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

        final Map<SpellSchool, SpellExpertise> spellSchoolMasteries = new EnumMap<>(Map.ofEntries(
                Map.entry(SpellSchool.AIR, SpellExpertise.valueOf(2))
        ));

        final Hero hero1 = new Hero(List.of(defender2), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));
        final Hero hero2 = new Hero(List.of(defender), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));

        final GameEngine engine = new GameEngine(hero1, hero2);

        final Spell aoeSpell = new AOESpellDecorator(new AOERectangularPointSelection(), new UnitAttackSpell(SpellStatistic.DAMAGING_SPELL));

        final Location c2Position = new Location(engine.getCreatureLocation(defender2), engine.getBoard());
        final Location center = new Location(c2Position.getX() - 1, c2Position.getY(), engine.getBoard());

        aoeSpell.cast(hero1, center);
        assertThat(defender2.getCurrentHp()).isEqualTo(95);
    }

    @Test
    void linearPointSelectionTests() {
        final Map<SpellSchool, SpellExpertise> spellSchoolMasteries = new EnumMap<>(Map.ofEntries(
                Map.entry(SpellSchool.AIR, SpellExpertise.valueOf(2))
        ));

        final Hero hero1 = new Hero(List.of(), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));
        final Hero hero2 = new Hero(List.of(), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));


        final GameEngine engine = new GameEngine(hero1, hero2);

        final AOELinearSymmetricalPointSelection horizontalPointSelection = new AOELinearSymmetricalPointSelection(AOELinearSymmetricalPointSelection.Axis.HORIZONTAL);
        final AOELinearSymmetricalPointSelection verticalPointSelection = new AOELinearSymmetricalPointSelection(AOELinearSymmetricalPointSelection.Axis.VERTICAL);

        final Location origin = new Location(2, 2, engine.getBoard());

        // Test horizontal point selection with size 2 (should expand more to the right)
        List<Location> horizontalPointsSize2 = horizontalPointSelection.getTargetPoints(origin, 2);
        List<Location> expectedHorizontalPointsSize2 = List.of(new Location(2, 2, engine.getBoard()), new Location(3, 2, engine.getBoard()));
        assertThat(horizontalPointsSize2).containsExactlyInAnyOrderElementsOf(expectedHorizontalPointsSize2);

        // Test horizontal point selection with size 3 (should expand -1 to the left and +1 to the right)
        List<Location> horizontalPointsSize3 = horizontalPointSelection.getTargetPoints(origin, 3);
        List<Location> expectedHorizontalPointsSize3 = List.of(new Location(1, 2, engine.getBoard()), new Location(2, 2, engine.getBoard()), new Location(3, 2, engine.getBoard()));
        assertThat(horizontalPointsSize3).containsExactlyInAnyOrderElementsOf(expectedHorizontalPointsSize3);

        // Test vertical point selection with size 2 (should expand more downward)
        List<Location> verticalPointsSize2 = verticalPointSelection.getTargetPoints(origin, 2);
        List<Location> expectedVerticalPointsSize2 = List.of(new Location(2, 2, engine.getBoard()), new Location(2, 3, engine.getBoard()));
        assertThat(verticalPointsSize2).containsExactlyInAnyOrderElementsOf(expectedVerticalPointsSize2);

        // Test vertical point selection with size 3 (should expand -1 up and +1 down)
        List<Location> verticalPointsSize3 = verticalPointSelection.getTargetPoints(origin, 3);
        List<Location> expectedVerticalPointsSize3 = List.of(new Location(2, 1, engine.getBoard()), new Location(2, 2, engine.getBoard()), new Location(2, 3, engine.getBoard()));
        assertThat(verticalPointsSize3).containsExactlyInAnyOrderElementsOf(expectedVerticalPointsSize3);
    }

    @Test
    void ringPointSelectionTest() {
        final Map<SpellSchool, SpellExpertise> spellSchoolMasteries = new EnumMap<>(Map.ofEntries(
                Map.entry(SpellSchool.AIR, SpellExpertise.valueOf(2))
        ));

        final Hero hero1 = new Hero(List.of(), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));
        final Hero hero2 = new Hero(List.of(), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));


        final GameEngine engine = new GameEngine(hero1, hero2);

        final AOERingPointSelection ringPointSelection = new AOERingPointSelection();

        final Location origin = new Location(5, 5, engine.getBoard());

        List<Location> expectedPoints = List.of(
                new Location(4, 4, engine.getBoard()), new Location(5, 4, engine.getBoard()), new Location(6, 4, engine.getBoard()),  // top row
                new Location(4, 6, engine.getBoard()), new Location(5, 6, engine.getBoard()), new Location(6, 6, engine.getBoard()),  // bottom row
                new Location(4, 5, engine.getBoard()), new Location(6, 5, engine.getBoard())                   // left and right columns
        );

        List<Location> actualPoints = ringPointSelection.getTargetPoints(origin, 1);
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

        final Map<SpellSchool, SpellExpertise> spellSchoolMasteries = new EnumMap<>(Map.ofEntries(
                Map.entry(SpellSchool.AIR, SpellExpertise.ADVANCED)
        ));

        final Hero hero1 = new Hero(List.of(), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));
        final Hero hero2 = new Hero(List.of(defender), new PrimarySkill(0, 0, 0, 0), new Spellbook(List.of()));


        final GameEngine engine = new GameEngine(hero1, hero2);

        final Spell aoeSpell = SpellFactory.fromStatistic(SpellStatistic.MAGIC_ARROW);
        final Location cPosition = new Location(engine.getCreatureLocation(defender), engine.getBoard());

        aoeSpell.cast(hero1, cPosition);
        assertThat(defender.getCurrentHp()).isEqualTo(80);
    }

    @Test
    void spellbookMethodsShouldWorkProperly() {
        final Spell spellA = SpellFactory.fromStatistic(SpellStatistic.ICE_BOLT);
        final Spell spellB = SpellFactory.fromStatistic(SpellStatistic.FIREBALL);
        final Spell spellC = SpellFactory.fromStatistic(SpellStatistic.DAMAGING_SPELL);

        final Map<SpellSchool, SpellExpertise> spellSchoolMasteries = new EnumMap<>(Map.ofEntries(
                Map.entry(SpellSchool.AIR, SpellExpertise.valueOf(2))
        ));

        final Spellbook spellbook = new Spellbook(List.of(
                spellA, spellB
        ));

        assertThat(spellbook.hasSpell(spellA)).isTrue();
        assertThat(spellbook.hasSpell(spellC)).isFalse();
    }

    @Test
    void reducedSpellCostCalculatorShouldReturnCorrectValues() {
        final Map<SpellSchool, SpellExpertise> spellSchoolMasteries = new EnumMap<>(Map.ofEntries(
                Map.entry(SpellSchool.AIR, SpellExpertise.valueOf(2))
        ));

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

        final Map<SpellSchool, SpellExpertise> spellSchoolMasteries = new EnumMap<>(Map.ofEntries(
                Map.entry(SpellSchool.AIR, SpellExpertise.valueOf(2))
        ));

        final Hero hero1 = new Hero(List.of(defender2), new PrimarySkill(0, 0, 2, 1), new Spellbook(List.of(
                SpellFactory.fromStatistic(SpellStatistic.DAMAGING_SPELL)
        )));
        final Hero hero2 = new Hero(List.of(defender), new PrimarySkill(0, 0, 1, 1), new Spellbook(SpellFactory.all()));

        final GameEngine engine = new GameEngine(hero1, hero2);

        final Location cPosition = new Location(engine.getCreatureLocation(defender), engine.getBoard());
        final Location c2Position = new Location(engine.getCreatureLocation(defender2), engine.getBoard());

        hero2.getSpellbook().castSpell(hero2.getSpellbook().getSpell(SpellStatistic.DAMAGING_SPELL.getSpellId()), hero2, cPosition);
        hero1.getSpellbook().castSpell(hero1.getSpellbook().getSpell(SpellStatistic.DAMAGING_SPELL.getSpellId()), hero1, c2Position);

        // Hero2 ma spelle Air Magic i innych szkol magii wiec jego koszt obniza sie w zaleznosci od levela castowanego spella.
        assertThat(hero2.getMana()).isEqualTo(1);

        // Hero1 ich nie ma wiec jego koszt sie nie obniza.
        assertThat(hero1.getMana()).isEqualTo(0);
    }
}
