package pl.psi.skills;

import com.google.common.collect.Range;
import org.junit.jupiter.api.Test;
import pl.psi.creatures.*;
import pl.psi.enums.AttackTypeEnum;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SkillsTest {

    @Test
    void basicArmoredCreatureTest() {
        final int MAX_HP = 200;

        //given
        Creature creatureWithArmorer = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(20)
                        .attack(20)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(50, 50))
                        .build())
                .build();
        creatureWithArmorer.decorateDamageApplier(new ArmoredDamageApplierDecorator(creatureWithArmorer.getDamageApplier(), 1));

        Creature creatureWithoutArmorer = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(20)
                        .attack(20)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(50, 50))
                        .build())
                .build();


        //when
        creatureWithoutArmorer.attack(creatureWithArmorer);

        //then
        assertThat(creatureWithArmorer.getCurrentHp()).isEqualTo(MAX_HP - 47);
        assertThat(creatureWithoutArmorer.getCurrentHp()).isEqualTo(MAX_HP - 50);
    }

    @Test
    void advancedArmoredCreatureTest() {
        final int MAX_HP = 200;

        Creature creatureWithArmorer = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(20)
                        .attack(20)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(50, 50))
                        .build())
                .build();
        creatureWithArmorer.decorateDamageApplier(new ArmoredDamageApplierDecorator(creatureWithArmorer.getDamageApplier(), 2));

        Creature creatureWithoutArmorer = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(20)
                        .attack(20)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(50, 50))
                        .build())
                .build();


        //when
        creatureWithoutArmorer.attack(creatureWithArmorer);

        //then
        assertThat(creatureWithArmorer.getCurrentHp()).isEqualTo(MAX_HP - 45);
        assertThat(creatureWithoutArmorer.getCurrentHp()).isEqualTo(MAX_HP - 50);
    }

    @Test
    void expertArmoredCreatureTest() {
        final int MAX_HP = 200;

        Creature creatureWithArmorer = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(20)
                        .attack(20)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(50, 50))
                        .build())
                .build();
        creatureWithArmorer.decorateDamageApplier(new ArmoredDamageApplierDecorator(creatureWithArmorer.getDamageApplier(), 3));

        Creature creatureWithoutArmorer = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(20)
                        .attack(20)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(50, 50))
                        .build())
                .build();


        //when
        creatureWithoutArmorer.attack(creatureWithArmorer);

        //then
        assertThat(creatureWithArmorer.getCurrentHp()).isEqualTo(MAX_HP - 42);
        assertThat(creatureWithoutArmorer.getCurrentHp()).isEqualTo(MAX_HP - 50);
    }

    @Test
    void basicOffenseCreatureTest() {
        final int MAX_HP = 200;
        //given
        Creature creatureWithOffense = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(20)
                        .attack(20)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(50, 50))
                        .build())
                .build();
        creatureWithOffense.decorateCalculator(new OffenseCalculatorDecorator(creatureWithOffense.getCalculator(), 1));

        Creature creatureWithoutOffense = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(20)
                        .attack(20)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(50, 50))
                        .build())
                .build();


        //when
        creatureWithOffense.attack(creatureWithoutOffense);

        //then
        assertThat(creatureWithoutOffense.getCurrentHp()).isEqualTo(MAX_HP - 55);
        assertThat(creatureWithOffense.getCurrentHp()).isEqualTo(MAX_HP - 50);
    }

    @Test
    void advanceOffenseCreatureTest() {
        final int MAX_HP = 200;
        //given
        Creature creatureWithOffense = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(20)
                        .attack(20)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(50, 50))
                        .build())
                .build();
        creatureWithOffense.decorateCalculator(new OffenseCalculatorDecorator(creatureWithOffense.getCalculator(), 2));

        Creature creatureWithoutOffense = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(20)
                        .attack(20)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(50, 50))
                        .build())
                .build();


        //when
        creatureWithOffense.attack(creatureWithoutOffense);

        //then
        assertThat(creatureWithoutOffense.getCurrentHp()).isEqualTo(MAX_HP - 60);
        assertThat(creatureWithOffense.getCurrentHp()).isEqualTo(MAX_HP - 50);
    }

    @Test
    void expertOffenseCreatureTest() {
        final int MAX_HP = 200;
        //given
        Creature creatureWithOffense = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(20)
                        .attack(20)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(50, 50))
                        .build())
                .build();
        creatureWithOffense.decorateCalculator(new OffenseCalculatorDecorator(creatureWithOffense.getCalculator(), 3));

        Creature creatureWithoutOffense = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(20)
                        .attack(20)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(50, 50))
                        .build())
                .build();


        //when
        creatureWithOffense.attack(creatureWithoutOffense);

        //then
        assertThat(creatureWithoutOffense.getCurrentHp()).isEqualTo(MAX_HP - 65);
        assertThat(creatureWithOffense.getCurrentHp()).isEqualTo(MAX_HP - 50);
    }

    @Test
    void basicArcheryCreatureTest() {
        final int MAX_HP = 30;
        //given
        Creature lichWithArchery = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(10)
                        .attack(13)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(11, 11))
                        .build())
                .attackType(AttackTypeEnum.RANGE)
                .build();
        lichWithArchery.decorateCalculator(new ArcheryCalculatorDecorator(lichWithArchery.getCalculator(), 1));

        Creature lichWithoutArchery = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(10)
                        .attack(13)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(11, 11))
                        .build())
                .build();


        //when
        lichWithArchery.attack(lichWithoutArchery);

        //then
        assertThat(lichWithoutArchery.getCurrentHp()).isEqualTo(MAX_HP - 13);
        assertThat(lichWithArchery.getCurrentHp()).isEqualTo(MAX_HP - 12);
    }

    @Test
    void advancedArcheryCreatureTest() {
        final int MAX_HP = 30;
        //given
        Creature lichWithArchery = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(10)
                        .attack(13)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(11, 11))
                        .build())
                .attackType(AttackTypeEnum.RANGE)
                .build();
        lichWithArchery.decorateCalculator(new ArcheryCalculatorDecorator(lichWithArchery.getCalculator(), 2));

        Creature lichWithoutArchery = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(10)
                        .attack(13)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(11, 11))
                        .build())
                .build();


        //when
        lichWithArchery.attack(lichWithoutArchery);

        //then
        assertThat(lichWithoutArchery.getCurrentHp()).isEqualTo(MAX_HP - 15);
        assertThat(lichWithArchery.getCurrentHp()).isEqualTo(MAX_HP - 12);
    }
    @Test
    void expertArcheryCreatureTest() {
        final int MAX_HP = 30;
        //given
        Creature lichWithArchery = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(10)
                        .attack(13)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(11, 11))
                        .build())
                .attackType(AttackTypeEnum.RANGE)
                .build();
        lichWithArchery.decorateCalculator(new ArcheryCalculatorDecorator(lichWithArchery.getCalculator(), 3));

        Creature lichWithoutArchery = new Creature.Builder().statistic(CreatureStats.builder()
                        .armor(10)
                        .attack(13)
                        .maxHp(MAX_HP)
                        .damage(Range.closed(11, 11))
                        .build())
                .build();


        //when
        lichWithArchery.attack(lichWithoutArchery);

        //then
        assertThat(lichWithoutArchery.getCurrentHp()).isEqualTo(MAX_HP - 18);
        assertThat(lichWithArchery.getCurrentHp()).isEqualTo(MAX_HP - 12);
    }
}
