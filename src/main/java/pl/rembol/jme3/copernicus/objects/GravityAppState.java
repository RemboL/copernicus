package pl.rembol.jme3.copernicus.objects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.jme3.app.state.AbstractAppState;
import com.jme3.scene.plugins.blender.math.Vector3d;
import pl.rembol.jme3.copernicus.GameState;

public class GravityAppState extends AbstractAppState {

    /**
     * property used to speed up gravitational changes - to be able to observe orbital movement and stuff
     */
    private static final double SCALE = 1;

    private Map<SpaceObject, Vector3d> currentPositions = new HashMap<>();

    private Map<SpaceObject, Vector3d> nextPositions = new HashMap<>();

    private final GameState gameState;

    public GravityAppState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void update(float tpf) {

        currentPositions.keySet().stream().filter(SpaceObject::isCollidable).filter(this::collidesWithAstralObject).collect(Collectors.toList()).stream().forEach(SpaceObject::destroy);

        checkCollisions();

        currentPositions.keySet().stream().filter(SpaceObject::isDestroyed).collect(Collectors.toList()).stream().forEach(this::remove);

        currentPositions.keySet().forEach(spaceObject -> updateVelocity(spaceObject, tpf));
        currentPositions.keySet().forEach(spaceObject -> move(spaceObject, spaceObject.velocity.mult(tpf * SCALE)));

        currentPositions.putAll(nextPositions);
    }

    private void checkCollisions() {
        List<SpaceObject> collisionCandidates = currentPositions.keySet()
                .stream()
                .filter(SpaceObject::isCollidable)
                .filter(spaceObject -> !spaceObject.isDestroyed())
                .filter(spaceObject -> spaceObject.getPrecisePosition().distance(
                        gameState.controlledShip.getPrecisePosition()) < 1000)
                .collect(Collectors.toList());

        for (int i = 0; i < collisionCandidates.size(); ++i) {
            for (int j = i + 1; j < collisionCandidates.size(); ++j) {
                SpaceObject object1 = collisionCandidates.get(i);
                SpaceObject object2 = collisionCandidates.get(j);

                if (object1.getPrecisePosition().distance(
                        object2.getPrecisePosition()) <= object1.getRadius() + object2.getRadius()) {
                    // handle collision between object1 and object2
                }
            }
        }
    }

    private boolean collidesWithAstralObject(SpaceObject spaceObject) {
        return gameState.stellarSystem.collidesWithAstralObject(spaceObject);
    }

    private void remove(SpaceObject spaceObject) {
        currentPositions.remove(spaceObject);
        nextPositions.remove(spaceObject);
    }

    private void updateVelocity(SpaceObject spaceObject, float tpf) {
        spaceObject.accelerate(gameState.stellarSystem.getGravitationalAccelerationAtPoint(getPosition(spaceObject)).mult(tpf * SCALE));
    }

    public void setPosition(SpaceObject spaceObject, Vector3d position) {
        nextPositions.put(spaceObject, position);
        currentPositions.putIfAbsent(spaceObject, position);
    }

    public Vector3d getPosition(SpaceObject spaceObject) {
        if (currentPositions.containsKey(spaceObject)) {
            return currentPositions.get(spaceObject).clone();
        } else {
            return null;
        }
    }

    public void move(SpaceObject spaceObject, Vector3d delta) {
        nextPositions.get(spaceObject).addLocal(delta);
    }

    public List<SpaceObject> getObjectsInProximity(Vector3d position, float proximityDistance) {
        return currentPositions.keySet().stream().filter(SpaceObject::isCollidable).filter(spaceObject -> position.distance(spaceObject.getPrecisePosition()) <= proximityDistance).collect(Collectors.toList());
    }
}
