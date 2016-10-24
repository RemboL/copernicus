package pl.rembol.jme3.copernicus.ship.maneuver;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.ship.Ship;

public class ManeuveringControl extends AbstractControl {

    private final GameState gameState;

    private final Ship ship;

    private Maneuver maneuver = null;

    public ManeuveringControl(GameState gameState, Ship ship) {
        this.gameState = gameState;
        this.ship = ship;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (maneuver != null) {
            if (maneuver.isFinished(ship)) {
                stopManeuver();
            } else {
                maneuver.act(ship, tpf);
            }

        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
    }

    private void stopManeuver() {
        if (maneuver != null) {
            maneuver.stopManeuver(ship);
        }
        maneuver = null;
    }

    public boolean isAutoPiloting() {
        return maneuver != null;
    }

    public void disableAutoPilot() {
        stopManeuver();
    }

    public void setManeuver(Maneuver maneuver) {
        stopManeuver();
        this.maneuver = maneuver;
    }
}
