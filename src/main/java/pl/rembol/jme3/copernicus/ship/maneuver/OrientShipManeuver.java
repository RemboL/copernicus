package pl.rembol.jme3.copernicus.ship.maneuver;

import pl.rembol.jme3.copernicus.objects.SpaceObject;
import pl.rembol.jme3.copernicus.ship.Ship;

public class OrientShipManeuver extends Maneuver {

    private final SpaceObject target;

    public OrientShipManeuver(SpaceObject target) {
        this.target = target;
    }

    @Override
    public void act(Ship ship, float tpf) {
        ship.orientTowards(target.getPrecisePosition(), tpf);
    }

    @Override
    public boolean isFinished(Ship ship) {
        return target.isDestroyed() || ship.isLookingAt(target.getPrecisePosition(), .01f);
    }
}
