package pl.rembol.jme3.copernicus.stellarobjects;

import com.jme3.scene.plugins.blender.math.Vector3d;

import java.util.HashSet;
import java.util.Set;

public class StellarSystem {

    private static final double GRAVITATIONAL_CONSTANT = 0.0000000000000000000667408; // km^3 kg^-1 s^-2

    private Set<AstralObject> astralObjects = new HashSet<>();

    public void register(AstralObject astralObject) {
        astralObjects.add(astralObject);
    }

    public void unregister(AstralObject astralObject) {
        astralObjects.remove(astralObject);
    }

    public Vector3d getGravitationalAccelerationAtPoint(Vector3d point) {
        Vector3d acceleration = new Vector3d(0, 0, 0);

        astralObjects.forEach(astralObject -> {

            double distance = point.distance(astralObject.getPrecisePosition());

            if (distance < astralObject.getRadius() / 2) {
                return;
            }

            Vector3d direction = astralObject.getPrecisePosition().subtract(point).normalize();
            acceleration.addLocal(
                    direction.mult(GRAVITATIONAL_CONSTANT * astralObject.getMass() / (distance * distance))
            );
        });

        return acceleration;
    }

}
