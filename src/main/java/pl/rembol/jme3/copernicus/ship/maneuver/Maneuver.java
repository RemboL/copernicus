package pl.rembol.jme3.copernicus.ship.maneuver;

import pl.rembol.jme3.copernicus.ship.Ship;

abstract public class Maneuver {
    abstract public void act(Ship ship, float tpf);

    abstract public boolean isFinished(Ship ship);

    public void stopManeuver(Ship ship) {
        ship.stopEngines();
    }
}
