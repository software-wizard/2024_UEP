package pl.psi.spells.object.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;


@Getter
public enum SpellExpertise {
    NONE(0),
    BASIC(1),
    ADVANCED(2),
    EXPERT(3);

    private final int masteryLevel;

    SpellExpertise(int masteryLevel) {
        this.masteryLevel = masteryLevel;
    }

    public static SpellExpertise valueOf(int value) {
        Optional<SpellExpertise> opt = Arrays.stream(values())
                .filter(e -> e.masteryLevel == value)
                .findFirst();
        if (opt.isEmpty()) throw new IllegalArgumentException("invalid school mastery level");

        return opt.get();
    }
}
