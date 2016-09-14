package pl.rembol.jme3.copernicus.objects;

import com.jme3.app.state.AbstractAppState;
import com.jme3.scene.plugins.blender.math.Vector3d;
import pl.rembol.jme3.copernicus.GameState;

import java.util.HashMap;
import java.util.Map;

public class GravityAppState extends AbstractAppState {

    private Map<SpaceObject, Vector3d> currentPositions = new HashMap<>();

    private Map<SpaceObject, Vector3d> nextPositions = new HashMap<>();

    private final GameState gameState;

    public GravityAppState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void update(float tpf) {

        currentPositions.keySet().forEach(this::updateVelocity);
        currentPositions.keySet().forEach(spaceObject -> spaceObject.preciseMove(spaceObject.velocity.mult(tpf /* times scale */)));

        currentPositions.putAll(nextPositions);
    }

    private void updateVelocity(SpaceObject spaceObject) {
        spaceObject.velocity.addLocal(gameState.stellarSystem.getGravitationalAccelerationAtPoint(getPosition(spaceObject)));
    }

    public void setPosition(SpaceObject spaceObject, Vector3d position) {
        nextPositions.put(spaceObject, position);
        currentPositions.putIfAbsent(spaceObject, position);
    }

    public Vector3d getPosition(SpaceObject spaceObject) {
        return currentPositions.get(spaceObject);
    }

    public void move(SpaceObject spaceObject, Vector3d delta) {
        nextPositions.get(spaceObject).addLocal(delta);
    }
}
