package pl.rembol.jme3.copernicus.objects;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.plugins.blender.math.Vector3d;
import pl.rembol.jme3.copernicus.GameState;

abstract public class SpaceObject extends Node {

    private Vector3d precisePosition = new Vector3d(0, 0, 0);
    
    protected final GameState gameState;

    private KeepTranslationRelativeToCameraFocusControl control;

    public SpaceObject(GameState gameState, String name) {
        super(name);
        this.gameState = gameState;

        control = new KeepTranslationRelativeToCameraFocusControl(this);
        addControl(control);
    }

    public void setPrecisePosition(Vector3f vector3f) {
        precisePosition = new Vector3d(vector3f);
        control.controlUpdate(0f);
    }

    public Vector3f getWorldPosition() {
        return getPrecisePosition().subtract(gameState.focusCamera.getCameraPosition()).toVector3f();
    }

    public Vector3d getPrecisePosition() {
        return precisePosition;
    }

    public void preciseMove(Vector3f delta) {
        precisePosition.addLocal(new Vector3d(delta));
    }


}
