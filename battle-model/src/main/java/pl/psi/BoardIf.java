package pl.psi;

import pl.psi.creatures.Creature;
import java.util.Optional;
import java.util.Set;

public interface BoardIf {
    boolean isValidPoint(Point p);
    Optional<Location> getCreatureLocation(Creature c);
    Optional<Creature> getCreature(Location l);
    Set<Creature> getAllCreatures();
}
