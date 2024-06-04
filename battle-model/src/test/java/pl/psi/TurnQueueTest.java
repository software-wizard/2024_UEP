package pl.psi;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import pl.psi.creatures.Creature;
import pl.psi.creatures.CreatureStats;
import pl.psi.creatures.Morale;

class TurnQueueTest
{
    @Test
    void shouldAddPawnsCorrectly()
    {
        final Creature creature1 = new Creature.Builder().statistic( CreatureStats.builder()
            .build() )
            .build();
        final Creature creature2 = new Creature.Builder().statistic( CreatureStats.builder()
            .build() )
            .build();
        final Creature creature3 = new Creature.Builder().statistic( CreatureStats.builder()
            .build() )
            .build();
        final TurnQueue turnQueue = new TurnQueue( List.of( creature1, creature2 ), List.of( creature3 ) );

        assertEquals( turnQueue.getCurrentCreature(), creature1 );
        turnQueue.next();
        assertEquals( turnQueue.getCurrentCreature(), creature2 );
        turnQueue.next();
        assertEquals( turnQueue.getCurrentCreature(), creature3 );
        turnQueue.next();
        assertEquals( turnQueue.getCurrentCreature(), creature1 );
    }

    @Test
    void creatureShouldActAgainAfterReceivingGoodMorale() {
        //always return 0.0 random.
        QuarterRandom quarterRandom = new QuarterRandom();
        final Creature shouldActTwice = new Creature.Builder().statistic( CreatureStats.builder()
                        .build() )
                .morale(new Morale(3, quarterRandom))
                .build();

        final Creature shouldntActTwice = new Creature.Builder().statistic( CreatureStats.builder()
                        .build() )
                .morale(new Morale(2, quarterRandom))
                .build();
        final Creature enemyCreature = new Creature.Builder().statistic( CreatureStats.builder()
                        .build() )
                .build();
        final TurnQueue turnQueue = new TurnQueue( List.of( shouldActTwice, shouldntActTwice), List.of( enemyCreature ) );

        assertEquals( turnQueue.getCurrentCreature(), shouldActTwice );
        turnQueue.next();
        assertEquals( turnQueue.getCurrentCreature(), shouldActTwice );
        turnQueue.next();
        assertEquals( turnQueue.getCurrentCreature(), shouldntActTwice);
        turnQueue.next();
        assertEquals( turnQueue.getCurrentCreature(), enemyCreature );
    }

    private class QuarterRandom extends Random {
        @Override
        public double nextDouble() {
            return 0.25;
        }
    }
}