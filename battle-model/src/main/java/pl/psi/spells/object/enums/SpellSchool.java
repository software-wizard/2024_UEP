package pl.psi.spells.object.enums;

public enum SpellSchool {
    ALL("All"),
    AIR("Air Magic"),
    EARTH("Earth Magic"),
    FIRE("Fire Magic"),
    WATER("Water Magic");


    private final String school;
    SpellSchool(final String school) {
        this.school = school;
    }

    @Override
    public String toString() {
        return this.school;
    }
}
