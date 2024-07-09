package pl.psi;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import pl.psi.objects.Field;
import pl.psi.objects.ResourcesField;

import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.function.ToIntFunction;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class EcoMap {
    private final BiMap<Point, EconomyHero> map = HashBiMap.create();
    private final BiMap<Point, MapObject> mapObjects = HashBiMap.create();

    private final BiMap<Point, Field> fieldObjects = HashBiMap.create();

    private final BiMap<Point, Opponent> mapOpponents = HashBiMap.create();

    FieldObjects fieldObject = new FieldObjects();


    Castle castle = new Castle();

    public EcoMap(final EconomyHero aHero1, final EconomyHero aHero2, PropertyChangeSupport aObserverSupport) {

        Opponent opponent = new Opponent(Resources.builder().gold(10).build(), Collections.emptyList());


        map.put(new Point(5, 5), aHero1);
        map.put(new Point(EconomyEngine.BOARD_WEIGHT - 5, EconomyEngine.BOARD_HEIGHT - 5), aHero2);
        mapObjects.put(new Point(1, 1), castle);
        for (Map.Entry<Point, Field> entry : fieldObject.fieldMap.entrySet()) {
            Point point = entry.getKey();
            fieldObjects.put(point, entry.getValue());
        }
    }

    Optional<EconomyHero> getHero(final Point aPoint) {
        return Optional.ofNullable(map.get(aPoint));
    }

    void move(final EconomyHero aHero, final Point aPoint) {
        if (canMove(aHero, aPoint)) {
            map.inverse()
                    .remove(aHero);
            map.put(aPoint, aHero);
            aHero.retrieveMovePoints(getPosition(aHero).distance(aPoint));
        }
    }

    boolean canMove(final EconomyHero aHero, final Point aPoint) {
        if (map.containsKey(aPoint)) {
            return false;
        }
        final Point oldPosition = getPosition(aHero);
        return aPoint.distance(oldPosition.getX(), oldPosition.getY()) < aHero.getCurrentMovePoints();
    }

    Point getPosition(EconomyHero aHero) {
        return map.inverse()
                .get(aHero);
    }

    public boolean isBattlePoint(Point aPoint) {
        return map.containsKey(aPoint);
    }

    public boolean isOpponentPoint(Point aPoint) {
        return mapOpponents.containsKey(aPoint);
    }

    public boolean isCastlePoint(Point aPoint) {
        return mapObjects.containsKey(aPoint);
    }

    public boolean isFieldPoint(Point aPoint) {
        return fieldObjects.containsKey(aPoint);
    }
    public Field getField(Point aPoint) {
        return fieldObjects.get(aPoint);
    }

    private boolean isResource(Point aPoint, ToIntFunction<? super ResourcesField> resourceSupplier) {
        return Optional.ofNullable(fieldObjects.get(aPoint)).filter(ResourcesField.class::isInstance).map(ResourcesField.class::cast).stream().mapToInt(resourceSupplier).anyMatch(resource -> resource > 0);
    }

    public boolean isGoldField(Point aPoint) {
        return isResource(aPoint, ResourcesField::getGold);
    }

    public boolean isWoodField(Point aPoint) {
        return isResource(aPoint, ResourcesField::getWood);
    }

    public boolean isOreField(Point aPoint) {
        return isResource(aPoint, ResourcesField::getOre);
    }

    public boolean isGemsField(Point aPoint) {
        return isResource(aPoint, ResourcesField::getGems);
    }

    public boolean isSulfurField(Point aPoint) {
        return isResource(aPoint, ResourcesField::getSulfur);
    }

    public boolean isMercuryField(Point aPoint) {
        return isResource(aPoint, ResourcesField::getMercury);
    }

    public boolean isCristalsField(Point aPoint) {
        return isResource(aPoint, ResourcesField::getCristals);
    }

    public void removeField(Field aField) {
        Point fieldPoint = fieldObjects.inverse().get(aField);
        fieldObjects.remove(fieldPoint);
    }
}
