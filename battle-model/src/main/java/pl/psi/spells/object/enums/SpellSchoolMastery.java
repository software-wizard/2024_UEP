package pl.psi.spells.object.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;


@Getter
public enum SpellSchoolMastery {
    NONE(0),
    BASIC(1),
    ADVANCED(2),
    EXPERT(3);

    private final int masteryLevel;

    SpellSchoolMastery(int masteryLevel) {
        this.masteryLevel = masteryLevel;
    }

    public static SpellSchoolMastery valueOf(int value) {
        Optional<SpellSchoolMastery> opt = Arrays.stream(values())
                .filter(e -> e.masteryLevel == value)
                .findFirst();
        if (opt.isEmpty()) throw new IllegalArgumentException("invalid school mastery level");

        return opt.get();
    }
}
