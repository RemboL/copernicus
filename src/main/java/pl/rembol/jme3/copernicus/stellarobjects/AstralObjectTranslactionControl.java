package pl.rembol.jme3.copernicus.stellarobjects;

import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import pl.rembol.jme3.copernicus.objects.KeepTranslationRelativeToCameraFocusControl;

public class AstralObjectTranslactionControl extends KeepTranslationRelativeToCameraFocusControl<AstralObject> {

    public AstralObjectTranslactionControl(AstralObject spaceObject) {
        super(spaceObject);
    }

    @Override
    protected boolean isInNearSpace(Vector3f worldPosition) {
        return worldPosition.length() < FAR_SIGHT_RANGE + spaceObject.getRadius();
    }

    @Override
    protected void transitToNearSpace() {
        spaceObject.setLocalScale(1f);
    }

    @Override
    protected void transitToFarSpace() {
    }

    @Override
    protected void translateInFarSpace(Vector3f worldPosition) {
        float distance = worldPosition.length() - (float) spaceObject.getRadius();
        float newDistance = FastMath.log(distance - FAR_SIGHT_RANGE + 1, 1.01f) + FAR_SIGHT_RANGE - 1;
        float newScale = newDistance / distance;
        Vector3f newWorldPosition = worldPosition.mult(newScale);
        spaceObject.setLocalTranslation(newWorldPosition);
        spaceObject.setLocalScale(newScale);
    }
}
