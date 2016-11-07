package pl.rembol.jme3.copernicus.objects;

import pl.rembol.jme3.copernicus.GameState;

public abstract class CollidableSpaceObject extends SpaceObject {

    protected double radius;

    protected double mass;

    public CollidableSpaceObject(GameState gameState, String name, double radius, double mass) {
        super(gameState, name);
        this.radius = radius;
        this.mass = mass;
    }

    public double getRadius() {
        return radius;
    }

    public double getMass() {
        return mass;
    }
}
