package pl.rembol.jme3.copernicus.stellarobjects;

import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.objects.KeepTranslationRelativeToCameraFocusControl;
import pl.rembol.jme3.copernicus.objects.SpaceObject;

public class AstralObject extends SpaceObject {

    protected double mass;

    public AstralObject(GameState gameState, String name, double radius, double mass) {
        super(gameState, name, radius);
        this.mass = mass;

        gameState.stellarSystem.register(this);
    }

    public double getMass() {
        return mass;
    }

    @Override
    protected KeepTranslationRelativeToCameraFocusControl createTranslationControl() {
        return new AstralObjectTranslactionControl(this);
    }

    public boolean collides(SpaceObject spaceObject) {
        if (spaceObject == this) {
            return false;
        }
        return getPrecisePosition().distance(spaceObject.getPrecisePosition()) < getRadius();
    }

    @Override
    protected boolean isCollidable() {
        return false; // collisions with astral objects are calculated manually
    }
}
