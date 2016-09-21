package pl.rembol.jme3.copernicus.ship;

import com.jme3.math.Vector3f;
import pl.rembol.jme3.copernicus.objects.KeepTranslationRelativeToCameraFocusControl;
import pl.rembol.jme3.copernicus.objects.SpaceObject;

public class ShipTranslationControl extends KeepTranslationRelativeToCameraFocusControl<SpaceObject> {

    public ShipTranslationControl(SpaceObject spaceObject) {
        super(spaceObject);
    }

    @Override
    protected void transitToNearSpace() {
        spaceObject.setVisible();
    }

    @Override
    protected void transitToFarSpace() {
        spaceObject.setInvisible();
    }

    @Override
    protected void translateInFarSpace(Vector3f worldPosition) {
        translateInNearSpace(worldPosition);
    }
}
