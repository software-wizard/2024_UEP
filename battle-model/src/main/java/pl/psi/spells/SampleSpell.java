package pl.psi.spells;

public class SampleSpell extends Spell {
    public SampleSpell() {
        super(SpellStatistic.SAMPLE_SPELL);
    }

    public void cast() {
        System.out.println("hello");
    }
}
