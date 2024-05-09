package pl.psi.spells;

import lombok.Getter;

@Getter
public enum SpellStatistic implements SpellStatisticIf {
    DAMAGING_SPELL("Damaging Spell", "Damages a creature", 0, SpellType.COMBAT, SpellSchool.ALL, 0, 0, 0, 5, 1),
    SAMPLE_SPELL("Sample", "Sample spell", 0, SpellType.UNKNOWN, SpellSchool.ALL, 0, 0, 0, 1, 1),
    MAGIC_ARROW("Magic Arrow", "Causes a bolt of magical energy to strike the selected enemy unit, dealing (10 + (power x 10)) damage to it.", 5, SpellType.COMBAT, SpellSchool.ALL, 1,0,1, 10, 10),
    LIGHTNING_BOLT("Lightning Bolt", "Causes a bolt of lightning to strike the selected unit, dealing (10 + (power x 25)) damage to it.", 10, SpellType.COMBAT, SpellSchool.AIR, 2, 0, 1, 10, 25),
    DESTROY_UNDEAD("Destroy Undead", "All undead creature troops receive ((power x 10) + 10) damage.", 15, SpellType.COMBAT, SpellSchool.AIR, 3, 0, 100, 10, 10),
    CHAIN_LIGHTNING("Chain Lightning", "Lightning bolt strikes target troop for (25 + (power x 40)) damage. Bolt strikes four troops.", 24, SpellType.COMBAT, SpellSchool.AIR, 4, 0, 100, 25,10),
    TITANS_LIGHTNING_BOLT("Titan's Lightning Bolt", "Does 600 damage. Requires Titan's Thunder.", 0, SpellType.COMBAT, SpellSchool.AIR, 5, 0, 1, 600, 0),
    DEATH_RIPPLE("Death Ripple", "Sends a wave of death across the battlefield which damages all non-undead units, dealing (10 + (power x 5)) damage to them.", 10, SpellType.COMBAT, SpellSchool.EARTH, 2, 0, 100, 10, 5),
    METEOR_SHOWER("Meteor Shower", "Troops in target hex and adjacent hexes take (25 + (power x 25)) damage.", 16, SpellType.COMBAT, SpellSchool.EARTH, 4, 0, 3, 25, 25),
    IMPLOSION("Implosion", "Target, enemy troop receives (100 + (power × 75)) damage.", 25, SpellType.COMBAT, SpellSchool.EARTH, 5, 0, 1, 100, 75),
    FIREBALL("Fireball", "Troops within target hex and adjacent hexes take (15 + (power x 10)) damage.", 15, SpellType.COMBAT, SpellSchool.FIRE, 3, 0, 3, 15, 10),
    //LAND_MINE("Land Mine")
    INFERNO("Inferno", "Strikes target hex, and all hexes within two hexes for (20 + (power x 10)) damage.", 16, SpellType.COMBAT, SpellSchool.FIRE, 4, 0, 5, 20, 10),
    ICE_BOLT("Ice Bolt", "Drains the body heat from the selected enemy unit, dealing (10 + (power x 20)) damage to it.", 8, SpellType.COMBAT, SpellSchool.WATER, 2, 0, 1, 10, 20),
    FROST_RING("Frost Ring", "Troops in hexes surrounding target hex receive (15 + (power x 10)) in damage. Target hex is unaffected.", 12, SpellType.COMBAT, SpellSchool.WATER, 3, 0, 3, 15, 10);

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

    SpellStatistic(String name, String description, int cost, SpellType type, SpellSchool school, int level, int duration, int size, int baseDmg, int powerMultiplier) {
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
