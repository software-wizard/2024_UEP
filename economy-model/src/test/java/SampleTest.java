import org.junit.jupiter.api.Test;
import pl.psi.EconomyHero;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SampleTest {
    @Test
    void Name() {
        EconomyHero economyHero = new EconomyHero("Player1");
        economyHero.getResources();
    }
}
