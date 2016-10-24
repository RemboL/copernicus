package pl.rembol.jme3.copernicus.ship.maneuver;

import com.jme3.scene.Spatial;

public interface WithManeuveringControl {

    default ManeuveringControl getManeuveringControl() {
        return ((Spatial) this).getControl(ManeuveringControl.class);
    }

    default boolean isAutoPiloting() {
        return getManeuveringControl().isAutoPiloting();
    }

    default void disableAutoPilot() {
        getManeuveringControl().disableAutoPilot();
    }

    default void setManeuver(Maneuver maneuver) {
        getManeuveringControl().setManeuver(maneuver);
    }
}
