package pl.rembol.jme3.copernicus.objects;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.plugins.blender.math.Vector3d;
import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.stellarobjects.AstralObject;

abstract public class SpaceObject extends Node {

    protected final GameState gameState;

    protected final Node innerNode = new Node();

    Vector3d velocity = new Vector3d(0, 0, 0);

    protected boolean isDestroyed = false;

    private KeepTranslationRelativeToCameraFocusControl control;

    public SpaceObject(GameState gameState, String name) {
        super(name);
        this.gameState = gameState;

        control = createTranslationControl();
        addControl(control);

        attachChild(innerNode);
        gameState.rootNode.attachChild(this);
    }

    public void setPrecisePosition(Vector3d vector3d) {
        gameState.gravityAppState.setPosition(this, vector3d);
        control.controlUpdate(0f);
    }

    public Vector3f getWorldPosition() {
        Vector3d cameraPosition = gameState.focusCamera.getCameraPosition();
        return getPrecisePosition().subtract(cameraPosition).toVector3f();
    }

    public Vector3d getPrecisePosition() {
        return gameState.gravityAppState.getPosition(this);
    }

    abstract protected KeepTranslationRelativeToCameraFocusControl createTranslationControl();

    public void setInvisible() {
        detachChild(innerNode);
    }

    public void setVisible() {
        attachChild(innerNode);
    }

    public void accelerate(Vector3d acceleration) {
        velocity.addLocal(acceleration);
    }

    public Vector3d getVelocity() {
        return velocity;
    }

    public void destroy() {
        isDestroyed = true;
        gameState.rootNode.detachChild(this);
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    protected AstralObject getGravityWellCenter() {
        GravityWellCenterControl control = getControl(GravityWellCenterControl.class);
        if (control == null) {
            control = new GravityWellCenterControl(gameState, this);
            addControl(control);
        }

        return control.getGravityWellCenter();
    }

    public void hit(double force, Vector3d direction) {
    }
}
