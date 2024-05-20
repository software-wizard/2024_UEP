package pl.psi.spells;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpellStats implements SpellStatisticIf {
    private final String name;
    private final String description;
    private final int cost;
    private final SpellType type;
    private final int level;
}
