package pl.rembol.jme3.copernicus.objects;

import com.jme3.app.state.AbstractAppState;
import com.jme3.scene.plugins.blender.math.Vector3d;
import pl.rembol.jme3.copernicus.GameState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        currentPositions.keySet().stream().filter(CollidableSpaceObject.class::isInstance).filter(this::collidesWithAstralObject).collect(Collectors.toList()).stream().forEach(SpaceObject::destroy);
        currentPositions.keySet().stream().filter(SpaceObject::isDestroyed).collect(Collectors.toList()).stream().forEach(this::remove);

        currentPositions.keySet().forEach(spaceObject -> updateVelocity(spaceObject, tpf));
        currentPositions.keySet().forEach(spaceObject -> move(spaceObject, spaceObject.velocity.mult(tpf * SCALE)));

        checkCollisions(gameState.controlledShip.getPrecisePosition());

        currentPositions.putAll(nextPositions);
    }

    protected void checkCollisions(Vector3d controlledShipPosition) {
        List<CollidableSpaceObject> collisionCandidates = currentPositions.keySet()
                .stream()
                .filter(CollidableSpaceObject.class::isInstance)
                .map(CollidableSpaceObject.class::cast)
                .filter(spaceObject -> spaceObject.getPrecisePosition().distance(
                        controlledShipPosition) < 1000)
                .collect(Collectors.toList());


        for (int i = 0; i < collisionCandidates.size(); ++i) {
            for (int j = i + 1; j < collisionCandidates.size(); ++j) {
                CollidableSpaceObject object1 = collisionCandidates.get(i);
                CollidableSpaceObject object2 = collisionCandidates.get(j);

                if (object1.getVelocity().length() > object1.getRadius() ||
                        object2.getVelocity().length() > object2.getRadius()) {
                    checkFastMovingCollision(object1, object2);
                } else {

                    checkSphericalCollision(object1, object2);
                }
            }
        }
    }

    private void checkFastMovingCollision(CollidableSpaceObject object1, CollidableSpaceObject object2) {
        if (currentPositions.get(object1).distance(currentPositions.get(object2))
                > object1.getVelocity().distance(object2.getVelocity()) + object1.getRadius() + object2.getRadius()) {
            // no chance of collision
            return;
        }

        Vector3d x0 = currentPositions.get(object2);
        Vector3d x1 = currentPositions.get(object1);
        Vector3d x2 = nextPositions.get(object1);

        x2 = x2.subtract(x1).subtract(object2.getVelocity());
        x0 = x0.subtract(x1);

        Double r = object1.getRadius() + object2.getRadius();

        Double a = x2.lengthSquared();
        Double b = -2 * x0.dot(x2);
        Double c = x0.lengthSquared() - r * r;

        Double delta = b * b - 4 * a * c;

        if (delta < 0) {
            // no collision
            return;
        }
        if (delta == 0 && a == 0 && b == 0) {
            // no collision
            return;
        }
        Double t = (-b - Math.sqrt(delta)) / (2 * a);
        System.out.println("t "+t);

        if (t < 0 || t > 1) {
            // no collission
            return;
        }

        Vector3d object1PositionOnCollision = currentPositions.get(object1).mult(1 - t)
                .add(nextPositions.get(object1).mult(t));
        Vector3d object2PositionOnCollision = currentPositions.get(object2).mult(1 - t)
                .add(nextPositions.get(object2).mult(t));

        Vector3d relativeVelocity = object2.getVelocity().subtract(object1.getVelocity());
        double angleFactor = Math.abs(Math.cos(relativeVelocity.normalize().angleBetween(
                object1PositionOnCollision.subtract(object2PositionOnCollision).normalize())));
        double relativeVelocityLength = relativeVelocity.length() * angleFactor;

        Vector3d collisionDirection = object1PositionOnCollision.subtract(object2PositionOnCollision).normalizeLocal();

        object1.accelerate(collisionDirection.mult(relativeVelocityLength * (-1) * 2 * object2.getMass() / (object1.getMass() + object2.getMass())));
        object2.accelerate(collisionDirection.mult(relativeVelocityLength * 2 * object1.getMass() / (object1.getMass() + object2.getMass())));

        nextPositions.put(object1, object1PositionOnCollision);
        nextPositions.put(object2, object2PositionOnCollision);

        System.out.println("!@#!@#");
        object1.hit(relativeVelocityLength * (object1.getMass() + object2.getMass()), nextPositions.get(object2));
        object2.hit(relativeVelocityLength * (object1.getMass() + object2.getMass()), nextPositions.get(object1));

    }

    private void checkSphericalCollision(CollidableSpaceObject object1, CollidableSpaceObject object2) {
        double collisionDistance = object1.getRadius() + object2.getRadius() -
                nextPositions.get(object1).distance(nextPositions.get(object2));

        if (collisionDistance >= 0) {
            Vector3d relativeVelocity = object2.getVelocity().subtract(object1.getVelocity());
            double angleFactor = Math.abs(Math.cos(relativeVelocity.normalize().angleBetween(
                    nextPositions.get(object1).subtract(nextPositions.get(object2)).normalize())));
            double relativeVelocityLength = relativeVelocity.length() * angleFactor;

            Vector3d collisionDirection = nextPositions.get(object2).subtract(nextPositions.get(object1)).normalizeLocal();

            object1.accelerate(collisionDirection.mult(relativeVelocityLength * (-1) * 2 * object2.getMass() / (object1.getMass() + object2.getMass())));
            object2.accelerate(collisionDirection.mult(relativeVelocityLength * 2 * object1.getMass() / (object1.getMass() + object2.getMass())));

            nextPositions.get(object1).addLocal(collisionDirection.mult(collisionDistance * (-1)));
            nextPositions.get(object2).addLocal(collisionDirection.mult(collisionDistance));

            object1.hit(relativeVelocityLength * (object1.getMass() + object2.getMass()), nextPositions.get(object2));
            object2.hit(relativeVelocityLength * (object1.getMass() + object2.getMass()), nextPositions.get(object1));
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
        nextPositions.put(spaceObject, position.clone());
        currentPositions.putIfAbsent(spaceObject, position.clone());
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
        return currentPositions.keySet().stream().filter(CollidableSpaceObject.class::isInstance).filter(spaceObject -> position.distance(spaceObject.getPrecisePosition()) <= proximityDistance).collect(Collectors.toList());
    }
}
