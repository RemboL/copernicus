package pl.rembol.jme3.copernicus.stellarobjects;

import com.jme3.scene.plugins.blender.math.Vector3d;
import pl.rembol.jme3.copernicus.objects.SpaceObject;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
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

        astralObjects.forEach(astralObject -> acceleration.addLocal(getGravitationalAcceleration(astralObject, point)));

        return acceleration;
    }

    public boolean collidesWithAstralObject(SpaceObject spaceObject) {
        return astralObjects.stream().anyMatch(astralObject -> astralObject.collides(spaceObject));
    }

    private Vector3d getGravitationalAcceleration(AstralObject astralObject, Vector3d point) {
        double distance = point.distance(astralObject.getPrecisePosition());

        if (distance < astralObject.getRadius() / 2) {
            return Vector3d.ZERO;
        }

        Vector3d direction = astralObject.getPrecisePosition().subtract(point).normalize();
        return direction.mult(GRAVITATIONAL_CONSTANT * astralObject.getMass() / (distance * distance));
    }

    public AstralObject getObjectOfStrongestGravitationalAcceleration(Vector3d position) {
        return astralObjects.stream()
                .map(astralObject -> new AbstractMap.SimpleEntry<>(astralObject, getGravitationalAcceleration(astralObject, position).lengthSquared()))
                .max((e1, e2) -> Double.compare(e1.getValue(), e2.getValue()))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public AstralObject getObjectForName(String name) {
        return astralObjects.stream()
                .filter(astralObject -> astralObject.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
    
    public Set<AstralObject> getAstralObjects() {
        return astralObjects;
    } 
}
