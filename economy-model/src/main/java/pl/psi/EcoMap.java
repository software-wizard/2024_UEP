package pl.psi;

import java.beans.PropertyChangeSupport;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import pl.psi.objects.ResourcesField;

/**
 * TODO: Describe this class (The first line - until the first dot - will interpret as the brief description).
 */
public class EcoMap
{
    private final BiMap< Point, EconomyHero > map = HashBiMap.create();
    private final BiMap<Point, MapObject> mapObjects = HashBiMap.create();

    private final BiMap<Point, ResourcesField> fieldObjects = HashBiMap.create();

    private final BiMap< Point, Opponent> mapOpponents = HashBiMap.create();

    FieldObjects fieldObject = new FieldObjects();



    Castle castle = new Castle();

    public EcoMap(final EconomyHero aHero1, final EconomyHero aHero2, PropertyChangeSupport aObserverSupport)
    {

        Opponent opponent = new Opponent(Resources.builder().gold(10).build(), Collections.emptyList());
        mapOpponents.put(new Point(10,10),opponent);


        map.put( new Point( 5, 5 ), aHero1 );
        map.put( new Point( EconomyEngine.BOARD_WEIGHT - 5, EconomyEngine.BOARD_HEIGHT - 5 ), aHero2 );
        mapObjects.put(new Point(1,1), castle);
        for (Map.Entry<Point, ResourcesField> entry : fieldObject.fieldMap.entrySet()) {
            Point point = entry.getKey();
            fieldObjects.put(point,entry.getValue());
        }
    }

    Optional< EconomyHero > getHero( final Point aPoint )
    {
        return Optional.ofNullable( map.get( aPoint ) );
    }

    void move( final EconomyHero aHero, final Point aPoint )
    {
        if( canMove( aHero, aPoint ) )
        {
            map.inverse()
                .remove( aHero );
            map.put( aPoint, aHero );
            aHero.retrieveMovePoints( getPosition( aHero ).distance( aPoint ) );
        }
    }

    boolean canMove( final EconomyHero aHero, final Point aPoint )
    {
        if( map.containsKey( aPoint ) )
        {
            return false;
        }
        final Point oldPosition = getPosition( aHero );
        return aPoint.distance( oldPosition.getX(), oldPosition.getY() ) < aHero.getCurrentMovePoints();
    }

    Point getPosition( EconomyHero aHero )
    {
        return map.inverse()
            .get( aHero );
    }

    public boolean isBattlePoint(Point aPoint) {
        return map.containsKey(aPoint);
    }

    public boolean isOpponentPoint(Point aPoint){
        return mapOpponents.containsKey(aPoint);
    }

    public boolean isCastlePoint(Point aPoint) {
        return mapObjects.containsKey(aPoint);
    }

    public boolean isFieldPoint(Point aPoint){
        return fieldObjects.containsKey(aPoint);
    }

    public ResourcesField getField(Point aPoint) {

        return fieldObjects.get(aPoint);
    }

    public boolean isGoldField(Point aPoint){
        if(fieldObjects.get(aPoint).getGold() != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean isWoodField(Point aPoint){
        if(fieldObjects.get(aPoint).getWood() != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean isOreField(Point aPoint){
        if(fieldObjects.get(aPoint).getOre() != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean isGemsField(Point aPoint){
        if(fieldObjects.get(aPoint).getGems() != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean isSulfurField(Point aPoint){
        if(fieldObjects.get(aPoint).getSulfur() != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean isMercuryField(Point aPoint){
        if(fieldObjects.get(aPoint).getMercury() != 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean isCristalsField(Point aPoint){
        if(fieldObjects.get(aPoint).getCristals() != 0){
            return true;
        }else{
            return false;
        }
    }

}
