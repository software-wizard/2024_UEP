package pl.psi;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class StartBattlePack {
    private final EconomyHero attacker;
    private final EconomyHero defender;
}
