package pl.rembol.jme3.copernicus.ship.maneuver;

import com.jme3.math.Vector3f;
import com.jme3.scene.plugins.blender.math.Vector3d;
import pl.rembol.jme3.copernicus.objects.SpaceObject;
import pl.rembol.jme3.copernicus.ship.Ship;

public class MatchSpeedManeuver extends Maneuver {

    private static final float ORIENTATION_PRECISION = .01f;

    private static final float CUT_ENGINES_ORIENTATION_ERROR = .5f;

    private final SpaceObject target;

    public MatchSpeedManeuver(SpaceObject target) {
        this.target = target;
    }

    @Override
    public void act(Ship ship, float tpf) {
        Vector3d deltaVelocity = target.getVelocity().subtract(ship.getVelocity());
        float orientationError = ship.getWorldRotation().mult(Vector3f.UNIT_Z).distance(deltaVelocity.toVector3f().normalize());
        if (orientationError > ORIENTATION_PRECISION) {
            ship.setThrottle((CUT_ENGINES_ORIENTATION_ERROR - orientationError) / CUT_ENGINES_ORIENTATION_ERROR);
            ship.orientTowards(ship.getPrecisePosition().add(deltaVelocity), tpf);
        } else {
            ship.fullThrottle();
        }
    }

    @Override
    public boolean isFinished(Ship ship) {
        return target.getVelocity().distance(ship.getVelocity()) < .001f;
    }
}
