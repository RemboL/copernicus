package pl.rembol.jme3.copernicus.ship.maneuver;

import com.jme3.math.Vector3f;
import com.jme3.scene.plugins.blender.math.Vector3d;
import pl.rembol.jme3.copernicus.objects.SpaceObject;
import pl.rembol.jme3.copernicus.ship.Ship;

public class MoveToAndMatchVelocityManeuver extends Maneuver {

    private static final float ORIENTATION_PRECISION = .01f;

    private static final float CUT_ENGINES_ORIENTATION_ERROR = .5f;

    private final SpaceObject spaceObject;

    private final double targetDistance;

    public MoveToAndMatchVelocityManeuver(SpaceObject spaceObject, double distance) {
        this.spaceObject = spaceObject;
        this.targetDistance = distance;
    }

    @Override
    public void act(Ship ship, float tpf) {

        Vector3d targetPoint = ship.getPrecisePosition()
                .subtract(spaceObject.getPrecisePosition())
                .normalize()
                .mult(targetDistance)
                .add(spaceObject.getPrecisePosition());
        Vector3d targetRelativePosition = targetPoint.subtract(ship.getPrecisePosition());
        Vector3d relativeVelocity = spaceObject.getVelocity().subtract(ship.getVelocity());
        
        Vector3d breakingPosition = breakingPosition(relativeVelocity, ship.getMaxAcceleration() * 0.8);
        double precisionError = targetRelativePosition.length() / ship.getMaxAcceleration() * 0.001;
        
        if (breakingPosition.distance(targetRelativePosition) > precisionError) {
            accelerateTowards(targetRelativePosition.subtract(breakingPosition).normalize(), ship, tpf);
        } else {
            ship.stopEngines();
        }

    }

    private void accelerateTowards(Vector3d accelerationDirection, Ship ship, float tpf) {
        float orientationError = ship.getWorldRotation().mult(Vector3f.UNIT_Z).distance(
                accelerationDirection.toVector3f().normalize());
        if (orientationError > ORIENTATION_PRECISION) {
            ship.setThrottle((CUT_ENGINES_ORIENTATION_ERROR - orientationError) / CUT_ENGINES_ORIENTATION_ERROR);
            ship.orientTowards(ship.getPrecisePosition().add(accelerationDirection), tpf);
        } else {
            ship.fullThrottle();
        }
    }

    private Vector3d breakingPosition(Vector3d velocityDistance, double acceleration) {
        double time = velocityDistance.length() / acceleration;
        return velocityDistance.normalize().mult(-acceleration * time * time / 2);
    }

    @Override
    public boolean isFinished(Ship ship) {
        return isCloseEnough(ship) && isVelocityMatched(ship);
    }

    private boolean isVelocityMatched(Ship ship) {
        return spaceObject.getVelocity().distance(ship.getVelocity()) < 0.001f;
    }

    private boolean isCloseEnough(Ship ship) {
        return Math.abs(
                spaceObject.getPrecisePosition().distance(
                        ship.getPrecisePosition()) - targetDistance) < targetDistance / 1000;
    }
}
