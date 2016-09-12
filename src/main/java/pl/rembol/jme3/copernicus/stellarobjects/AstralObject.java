package pl.rembol.jme3.copernicus.stellarobjects;

import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.objects.KeepTranslationRelativeToCameraFocusControl;
import pl.rembol.jme3.copernicus.objects.SpaceObject;

public class AstralObject extends SpaceObject {

    protected float radius;

    public AstralObject(GameState gameState, String name, float radius) {
        super(gameState, name);
        this.radius = radius;
    }

    public float getRadius() {
        return radius;
    }


    @Override
    protected KeepTranslationRelativeToCameraFocusControl createTranslationControl() {
        return new AstralObjectTranslactionControl(this);
    }
}
