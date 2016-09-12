package pl.rembol.jme3.copernicus.objects;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public abstract class KeepTranslationRelativeToCameraFocusControl<T extends SpaceObject> extends AbstractControl {

    protected static final float FAR_SIGHT_RANGE = 100f;

    protected final T spaceObject;

    private Boolean isInNearSpace;

    public KeepTranslationRelativeToCameraFocusControl(T spaceObject) {
        this.spaceObject = spaceObject;
    }

    @Override
    protected void controlUpdate(float tpf) {
        Vector3f worldPosition = spaceObject.getWorldPosition();
        if (isInNearSpace(worldPosition)) {
            if (isInNearSpace == null || !isInNearSpace) {
                isInNearSpace = true;
                transitToNearSpace();
            }

            translateInNearSpace(worldPosition);
        } else {
            if (isInNearSpace == null || isInNearSpace) {
                isInNearSpace = false;
                transitToFarSpace();
            }

            translateInFarSpace(worldPosition);
        }
    }

    abstract protected void transitToNearSpace();

    abstract protected void transitToFarSpace();

    protected boolean isInNearSpace(Vector3f worldPosition) {
        return worldPosition.length() < FAR_SIGHT_RANGE;
    }

    protected void translateInNearSpace(Vector3f worldPosition) {
        spaceObject.setLocalTranslation(worldPosition);
    }

    abstract protected void translateInFarSpace(Vector3f worldPosition);

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
