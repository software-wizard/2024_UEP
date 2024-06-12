package pl.psi.spells.object.enums;

import lombok.Getter;
import pl.psi.spells.object.interfaces.SpellStatisticIf;

@Getter
public enum SpellStatistic implements SpellStatisticIf {
    DAMAGING_SPELL(0,"Damaging Spell", "Test spell", 10, SpellType.COMBAT, SpellSchool.ALL, 1, 0, 2, 5, 1),
    MAGIC_ARROW(1,"Magic Arrow", "Causes a bolt of magical energy to strike the selected enemy unit, dealing (10 + (power x 10)) damage to it.", 5, SpellType.COMBAT, SpellSchool.ALL, 1,0,1, 10, 10),
    LIGHTNING_BOLT(2,"Lightning Bolt", "Causes a bolt of lightning to strike the selected unit, dealing (10 + (power x 25)) damage to it.", 10, SpellType.COMBAT, SpellSchool.AIR, 2, 0, 1, 10, 25),
    DESTROY_UNDEAD(3,"Destroy Undead", "All undead creature troops receive ((power x 10) + 10) damage.", 15, SpellType.COMBAT, SpellSchool.AIR, 3, 0, 100, 10, 10),
    CHAIN_LIGHTNING(4,"Chain Lightning", "Lightning bolt strikes target troop for (25 + (power x 40)) damage. Bolt strikes four troops.", 24, SpellType.COMBAT, SpellSchool.AIR, 4, 0, 100, 25,10),
    TITANS_LIGHTNING_BOLT(5,"Titan's Lightning Bolt", "Does 600 damage. Requires Titan's Thunder.", 0, SpellType.COMBAT, SpellSchool.AIR, 5, 0, 1, 600, 0),
    DEATH_RIPPLE(6,"Death Ripple", "Sends a wave of death across the battlefield which damages all non-undead units, dealing (10 + (power x 5)) damage to them.", 10, SpellType.COMBAT, SpellSchool.EARTH, 2, 0, 100, 10, 5),
    METEOR_SHOWER(7,"Meteor Shower", "Troops in target hex and adjacent hexes take (25 + (power x 25)) damage.", 16, SpellType.COMBAT, SpellSchool.EARTH, 4, 0, 3, 25, 25),
    IMPLOSION(8, "Implosion", "Target, enemy troop receives (100 + (power × 75)) damage.", 25, SpellType.COMBAT, SpellSchool.EARTH, 5, 0, 1, 100, 75),
    FIREBALL(9,"Fireball", "Troops within target hex and adjacent hexes take (15 + (power x 10)) damage.", 15, SpellType.COMBAT, SpellSchool.FIRE, 3, 0, 3, 15, 10),
    //LAND_MINE("Land Mine")
    INFERNO(10, "Inferno", "Strikes target hex, and all hexes within two hexes for (20 + (power x 10)) damage.", 16, SpellType.COMBAT, SpellSchool.FIRE, 4, 0, 5, 20, 10),
    ICE_BOLT(11, "Ice Bolt", "Drains the body heat from the selected enemy unit, dealing (10 + (power x 20)) damage to it.", 8, SpellType.COMBAT, SpellSchool.WATER, 2, 0, 1, 10, 20),
    FROST_RING(12, "Frost Ring", "Troops in hexes surrounding target hex receive (15 + (power x 10)) in damage. Target hex is unaffected.", 12, SpellType.COMBAT, SpellSchool.WATER, 3, 0, 3, 15, 10),
    AIR_MAGIC(13, "Air Magic", "Allows your hero to cast air spells at reduced cost.", 0, SpellType.PASSIVE, SpellSchool.AIR, 0, 0, 0, 0, 0),
    WATER_MAGIC(14, "Water Magic", "Allows your hero to cast water spells at reduced cost.", 0, SpellType.PASSIVE, SpellSchool.WATER, 0, 0, 0, 0, 0),
    EARTH_MAGIC(15, "Earth Magic", "Allows your hero to cast earth spells at reduced cost.", 0, SpellType.PASSIVE, SpellSchool.EARTH, 0, 0, 0, 0, 0),
    FIRE_MAGIC(16, "Fire Magic", "Allows your hero to cast fire spells at reduced cost.", 0, SpellType.PASSIVE, SpellSchool.FIRE, 0, 0, 0, 0, 0),

    HASTE(17, "Haste", "Increases the speed of the selected/all friendly unit(s) by 3/5.", 6, SpellType.COMBAT, SpellSchool.ALL, 1, 1, 0, 0, 0),
    DISRUPTING_RAY(18, "Disrupting Ray", "Reduces the defense of the selected unit by 3/4/5.", 10, SpellType.COMBAT, SpellSchool.ALL, 2, 1, 0, 0, 0),
    FORTUNE(19, "Fortune", "Increases the luck of the selected/all friendly unit(s) by 1/2.", 7, SpellType.COMBAT, SpellSchool.ALL, 2, 1, 0, 0, 0),
    PRECISION(20, "Precision", "Increases the ranged attack of the selected/all friendly unit(s) by 3/6.", 8, SpellType.COMBAT, SpellSchool.ALL, 2, 1, 0, 0, 0),
    PROTECTION_FROM_AIR(21, "Protection from Air", "Reduces the damage received from Air Spells by the selected/all friendly unit(s) by 30%/50%.", 7, SpellType.COMBAT, SpellSchool.ALL, 2, 1, 0, 0, 0),
    AIR_SHIELD(22, "Air Shield", "Reduces the damage received from ranged attacks by the selected/all friendly unit(s) by 25%/50%.", 12, SpellType.COMBAT, SpellSchool.ALL, 3, 1, 0, 0, 0),
    HYPNOTIZE(23, "Hypnotize", "Target enemy troop of less than (10/20/50 + (power × 25)) health is put under your control.", 18, SpellType.COMBAT, SpellSchool.ALL, 3, 1, 0, 0, 0),
    COUNTERSTRIKE(24, "Counterstrike", "Target/All allied troop(s) can retaliate against 1/2 additional attacks per round.", 24, SpellType.COMBAT, SpellSchool.ALL, 4, 1, 0, 0, 0),
    MAGIC_MIRROR(25, "Magic Mirror", "Target allied troop has a 20%/30%/40% chance of reflecting enemy spells to a random enemy troop.", 25, SpellType.COMBAT, SpellSchool.ALL, 5, 1, 0, 0, 0),
    SUMMON_AIR_ELEMENTAL(26, "Summon Air Elemental", "Summons a troop of (power × 2/3/4) Air Elementals.", 25, SpellType.COMBAT, SpellSchool.ALL, 5, 1, 0, 0, 0),
    SHIELD(27, "Shield", "Reduces the damage received from hand-to-hand attacks by the selected/all friendly unit(s) by 15%/30%.", 5, SpellType.COMBAT, SpellSchool.ALL, 1, 1, 0, 0, 0),
    SLOW(28, "Slow", "Reduces the speed of the selected/all enemy unit(s) to 75%/50%.", 6, SpellType.COMBAT, SpellSchool.ALL, 1, 1, 0, 0, 0),
    STONE_SKIN(29, "Stone Skin", "Increases the defense of the selected/all friendly unit(s) by 3/6.", 5, SpellType.COMBAT, SpellSchool.ALL, 1, 1, 0, 0, 0),
    QUICKSAND(30, "Quicksand", "Quicksand pits are placed in 4/6/8 random hexes.", 8, SpellType.COMBAT, SpellSchool.ALL, 2, 1, 0, 0, 0),
    ANIMATE_DEAD(31, "Animate Dead", "Reanimates (30/60/160 + (power × 50)) HP of killed friendly undead creatures permanently.", 15, SpellType.COMBAT, SpellSchool.ALL, 3, 1, 0, 0, 0),
    ANTI_MAGIC(32, "Anti-Magic", "Target allied troop becomes immune to spells with max level 3/4/5.", 15, SpellType.COMBAT, SpellSchool.ALL, 3, 1, 0, 0, 0),
    EARTHQUAKE(33, "Earthquake", "Does one point of damage to 2/3/4 random castle walls during siege combat.", 20, SpellType.COMBAT, SpellSchool.ALL, 3, 1, 0, 0, 0),
    FORCE_FIELD(34, "Force Field", "A 2/3-hex wide force wall is created at target hex. Movement through these hexes is blocked.", 12, SpellType.COMBAT, SpellSchool.ALL, 3, 1, 0, 0, 0),
    PROTECTION_FROM_EARTH(35, "Protection from Earth", "Reduces the damage received from Earth Spells by the selected/all friendly unit(s) by 30%/50%.", 12, SpellType.COMBAT, SpellSchool.ALL, 3, 1, 0, 0, 0),
    RESURRECTION(36, "Resurrection", "Reanimates (40/80/160 + (power × 50)) HP of killed friendly living creatures for the current battle/permanently.", 20, SpellType.COMBAT, SpellSchool.ALL, 4, 1, 0, 0, 0),
    SORROW(37, "Sorrow", "Reduces the morale of the selected/all enemy unit(s) by 1/2.", 16, SpellType.COMBAT, SpellSchool.ALL, 4, 1, 0, 0, 0),
    SUMMON_EARTH_ELEMENTAL(38, "Summon Earth Elemental", "Summons a troop of (power × 2/3/4) Earth Elementals.", 25, SpellType.COMBAT, SpellSchool.ALL, 5, 1, 0, 0, 0),
    BLOODLUST(39, "Bloodlust", "Increases the attack strength for hand-to-hand attacks of the selected/all friendly unit(s) by 3/6.", 5, SpellType.COMBAT, SpellSchool.ALL, 1, 1, 0, 0, 0),
    CURSE(40, "Curse", "Causes the selected/all enemy unit(s) to inflict minimum damage/(minimum damage - 1) when attacking.", 6, SpellType.COMBAT, SpellSchool.ALL, 1, 1, 0, 0, 0),
    PROTECTION_FROM_FIRE(41, "Protection from Fire", "Reduces the damage received from Fire Spells by the selected/all friendly unit(s) by 30%/50%.", 5, SpellType.COMBAT, SpellSchool.ALL, 1, 1, 0, 0, 0),
    BLIND(42, "Blind", "Selected enemy unit cannot act until attacked, dispelled or effect wears off. Retaliates at 50%/25%/0% strength.", 10, SpellType.COMBAT, SpellSchool.ALL, 2, 1, 0, 0, 0),
    MISFORTUNE(43, "Misfortune", "Reduces the luck of the selected/all friendly unit(s) by 1/2.", 12, SpellType.COMBAT, SpellSchool.ALL, 3, 1, 0, 0, 0),
    BERSERK(44, "Berserk", "Target attacks nearest troop. All creatures in a 1/2/3 hex radius affected.", 20, SpellType.COMBAT, SpellSchool.ALL, 4, 1, 0, 0, 0),
    FIRE_SHIELD(45, "Fire Shield", "20%/25%/30% of hand-to-hand damage inflicted on target, allied troop is counter-inflicted on attackers of the troop.", 16, SpellType.COMBAT, SpellSchool.ALL, 4, 1, 0, 0, 0),
    FRENZY(46, "Frenzy", "Friendly troop's attack is increased by 100%/150%/200% of its defense and its defense is reduced to 0.", 16, SpellType.COMBAT, SpellSchool.ALL, 4, 1, 0, 0, 0),
    SLAYER(47, "Slayer", "Target allied troop's attack is increased by 8 against behemoths, dragons, hydras, firebirds, and sea serpents/haspids.", 16, SpellType.COMBAT, SpellSchool.ALL, 4, 1, 0, 0, 0),
    SACRIFICE(48, "Sacrifice", "Target, non-undead friendly troop is sacrificed. Reanimates (power + sac'd troop's base HP + 3/6/10) × # sac'd) HP of non-undead friendly creatures permanently.", 25, SpellType.COMBAT, SpellSchool.ALL, 5, 1, 0, 0, 0),
    SUMMON_FIRE_ELEMENTAL(49, "Summon Fire Elemental", "Summons a troop of (power × 2/3/4) Fire Elementals.", 25, SpellType.COMBAT, SpellSchool.ALL, 5, 1, 0, 0, 0),
    BLESS(50, "Bless", "Causes the selected/all friendly unit(s) to inflict maximum damage/(maximum damage + 1) when attacking.", 5, SpellType.COMBAT, SpellSchool.ALL, 1, 1, 0, 0, 0),
    CURE(51, "Cure", "Removes all negative spell effects from the selected/all friendly unit(s) and heals for (10/20/30 + (power x 5)) HP.", 6, SpellType.COMBAT, SpellSchool.ALL, 1, 1, 0, 0, 0),
    DISPEL(52, "Dispel", "Removes all spell effects from the selected friendly/selected enemy/all unit(s).", 5, SpellType.COMBAT, SpellSchool.ALL, 1, 1, 0, 0, 0),
    PROTECTION_FROM_WATER(53, "Protection from Water", "Reduces the damage received from Water Spells by the selected/all friendly unit(s) by 30%/50%.", 5, SpellType.COMBAT, SpellSchool.ALL, 1, 1, 0, 0, 0),
    REMOVE_OBSTACLE(54, "Remove Obstacle", "Removes a non-magic obstacle/Fire Wall/magical obstacle from the battlefield. Integrated obstacles (such as cliffs) are not affected.", 7, SpellType.COMBAT, SpellSchool.ALL, 2, 1, 0, 0, 0),
    WEAKNESS(55, "Weakness", "Reduces the attack of the selected/all enemy unit(s) by 3/6.", 8, SpellType.COMBAT, SpellSchool.ALL, 2, 1, 0, 0, 0),
    FORGETFULNESS(56, "Forgetfulness", "Targets a selected/all enemy ranged troop(s). Half/all the creatures cannot shoot. Half the creatures cannot melee attack.", 12, SpellType.COMBAT, SpellSchool.ALL, 3, 1, 0, 0, 0),
    MIRTH(57, "Mirth", "Increases the morale of the selected/all friendly unit(s) by 1/2.", 12, SpellType.COMBAT, SpellSchool.ALL, 3, 1, 0, 0, 0),
    TELEPORT(58, "Teleport", "Target allied troop instantly moves to an unoccupied target hex. Except over walls or moats/except over moats/no restrictions.", 15, SpellType.COMBAT, SpellSchool.ALL, 3, 1, 0, 0, 0),
    CLONE(59, "Clone", "Creates a duplicate of target, friendly troop with max level 5/6/7. The duplicate is dispelled if it receives any damage.", 24, SpellType.COMBAT, SpellSchool.ALL, 4, 1, 0, 0, 0),
    PRAYER(60, "Prayer", "Increases the attack, defense, and speed of the selected/all friendly unit(s) by 2/4.", 16, SpellType.COMBAT, SpellSchool.ALL, 4, 1, 0, 0, 0),
    SUMMON_WATER_ELEMENTAL(61, "Summon Water Elemental", "Summons a troop of (power × 2/3/4) Water Elementals.", 25, SpellType.COMBAT, SpellSchool.ALL, 5, 1, 0, 0, 0);

    private final int spellId;
    private final String name;
    private final String description;
    private final int cost; // cost decreases by spell's level depending on hero's knowledge, e.g. if hero learned fire from school of fire magic, then all fire spells have decreased cost by level of said spell
    private final SpellType type;
    private final SpellSchool school;
    private final int level;
    private final int duration; // duration in non-damage spells is sometimes equal the hero's power (in wiki it's 1 rnd/sp => 1 round per spell power, not spell point)
    private final int size;
    private final int baseDmg;
    private final int powerMultiplier;

    SpellStatistic(int spellId, String name, String description, int cost, SpellType type, SpellSchool school, int level, int duration, int size, int baseDmg, int powerMultiplier) {
        this.spellId = spellId;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.type = type;
        this.school = school;
        this.level = level;
        this.duration = duration;
        this.size = size;
        this.baseDmg = baseDmg;
        this.powerMultiplier = powerMultiplier;
    }
}
