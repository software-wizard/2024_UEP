package pl.psi.creatures;

import com.google.common.collect.Range;

import lombok.Getter;

@Getter
public enum     CreatureStatistic implements CreatureStatisticIf
{

    // NECROPILIS FRACTION

    SKELETON( "Skeleton", 5, 4, 6, 4, Range.closed( 1, 3 ), 1,
        "Average lvl1 foot soldier, but always in huge numbers thanks to necromancy skill and skeleton transformer.",
        false ), //
    WALKING_DEAD( "Walking Dead", 5, 5, 15, 3, Range.closed( 2, 3 ), 2,
        "Basically its the same skeleton with more hit points. I prefer buying 2 skeletons instead.", false ), //
    WIGHT( "Wight", 7, 7, 18, 5, Range.closed( 3, 5 ), 3,
        "Regenerating ability is really good when fighting weak enemies, especially shooters.\nSpecial: top wight of the stack regenerates all lost damage in the beginning of each round",
        false ), //
    VAMPIRE( "Vampire", 10, 9, 30, 6, Range.closed( 5, 8 ), 4,
        "NOTHING compared to their upgraded brothers. Keep the population growing and recruit after the upgrade.\nSpecial: no enemy retaliation.",
        false ), //
    LICH( "Lich", 13, 10, 30, 6, Range.closed( 11, 15 ), 5,
        "Now they last longer and are able to do more damage! A must for good necropolis army.\nSpecial: death cloud range attack - damages living creatures on adjacent hexes to target.\n",
        false ), //
    BLACK_KNIGHT( "Black Knight", 16, 16, 120, 7, Range.closed( 15, 30 ), 6,
        "Awesome ground unit. As any undead it cannot be blinded, so your enemies will have to look out.\nSpecial: 20% chance to curse enemy.\n",
        false ), //
    BONE_DRAGON( "Bone Dragon", 17, 15, 150, 9, Range.closed( 25, 50 ), 7,
        "They are truly fearsome for enemies with low morale. Simply keeping them on battlefield scares enemies.\nSpecial: -1 to enemy morale.\n",
        false ), //
    SKELETON_WARRIOR( "Skeleton Warrior", 6, 6, 6, 5, Range.closed( 1, 3 ), 1,
        "Numerous skeletons become even better, but running back to town and upgrading is a problem... If there is no room in your army for ordinary skeletons, necromancy skill will resurrect skeleton warriors, but there will be less of them than normal skeletons, so it might be a good idea not to upgrade cursed temple at all.",
        true ), //
    ZOMBIE( "Zombie", 5, 5, 20, 4, Range.closed( 2, 3 ), 2,
        "Attack ratings are way too low... In my opinion, necropolis has the worst lvl2 creature.\nSpecial: 20% chance to disease enemies (-2Att -2Def for 3 rounds)\n",
        true ), //
    WRAITH( "Wraith", 7, 7, 18, 5, Range.closed( 3, 5 ), 3,
        "Regenerating ability is really good when fighting weak enemies, especially shooters.\nSpecial: top wight of the stack regenerates all lost damage in the beginning of each round\n",
        true ), //
    VAMPIRE_LORD( "Vampire Lord", 10, 10, 40, 9, Range.closed( 5, 8 ), 4,
        "My favorite necropolis unit. Use them as main striking unit and you might end up with no losses!\nSpecial: no enemy retaliation ; resurrects members of their own stack by restoring health equal to the amount of damage they do to living enemies.\n",
        true ), //
    POWER_LICH( "Power Lich", 13, 10, 40, 7, Range.closed( 11, 15 ), 5,
        "Now they last longer and are able to do more damage! A must for good necropolis army.\nSpecial: death cloud range attack - damages living creatures on adjacent hexes to target.\n",
        true ), //
    DREAD_KNIGHT( "Dread Knight", 18, 18, 120, 9, Range.closed( 15, 30 ), 6,
        "I think it's the best lvl6 unit in the game! Double damage ability puts Dread Knights above Naga Queens.\nSpecial: 20% chance to curse enemy ; 20% chance to do double damage.\n",
        true ), //
    GHOST_DRAGON( "Ghost Dragon", 19, 17, 200, 14, Range.closed( 25, 50 ), 7,
        "When situation seems hopeless, take a chance on the best enemy stack! If you'll get lucky, half their hit points will be gone instantly!! Ageing ability makes ghost dragons as dangerous as other lvl7 creatures.\nSpecial: -1 to enemy morale ; 20% chance to age enemy (halve hit points of all stack members).\n",
        true ),//

    //WAR MACHINES
    FIRST_AID_TENT("First Aid Tent",0,0,75,0,Range.closed(0,0),0,
            "Heals a small amount of hit points to a top creature in one of your stacks every round. The effect is very weak. First Aid skill gives you control over the tent and makes it heal more hit points, but the tent is extremely easy to destroy, rendering your First Aid skill useless for the rest of the battle - see note on Artillery above. The tent, however, can save you losing a few high-level creatures by topping up their hit points every round.",
            true),//
    BALLISTA("Ballista",10,10,250,0,Range.closed(8,12),0,
            "Ballista is affected by hero's attack still, and unless your hero has a strong attack AND Artillery secondary skill, ballista will not do much damage, buy 2500 gold worth of creatures instead. If you are going for Artillery secondary skill, keep in mind that ballista only has 250 hit points, and once destroyed, the skill will remain useless for the rest of the battle. That is why heroes should choose Offense, Armourer, etc over Artillery. Note: without Artillery secondary skill, ballista will shoot without your control.",
            true),//
    CATAPULT("Catapult",10,10,1000,0,Range.closed(100,100),0,
            "Every hero has one all the time. Catapult is affected by Balistics skill, which unlike other war machine skills is very important to learn. Catapult is durable and comes under your control as you deveop Ballistics. If you are under seige and have powerful spells, destroy the enemy catapult so you can keep your walls and archer towers, turning the tides of battle in your favour. Ballistics secondary skills is essential for towns like Fortress who lack strong flyers and shooters, they must break through the drawbridge before enemy shooters have a good go at them.",
            true);//





    private final String name;
    private final int attack;
    private final int armor;
    private final int maxHp;
    private final int moveRange;
    private final Range< Integer > damage;
    private final int tier;
    private final String description;
    private final boolean isUpgraded;
    private final int moraleValue;

    CreatureStatistic( final String aName, final int aAttack, final int aArmor, final int aMaxHp,
        final int aMoveRange, final Range< Integer > aDamage, final int aTier, final String aDescription,
        final boolean aIsUpgraded )

    {
        name = aName;
        attack = aAttack;
        armor = aArmor;
        maxHp = aMaxHp;
        moveRange = aMoveRange;
        damage = aDamage;
        tier = aTier;
        description = aDescription;
        isUpgraded = aIsUpgraded;
        moraleValue = 0;
    }

    String getTranslatedName()
    {
        return name;
    }
}
