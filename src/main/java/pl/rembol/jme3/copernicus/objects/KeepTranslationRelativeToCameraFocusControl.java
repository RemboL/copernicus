package pl.rembol.jme3.copernicus.objects;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class KeepTranslationRelativeToCameraFocusControl extends AbstractControl {

    private final SpaceObject spaceObject;

    public KeepTranslationRelativeToCameraFocusControl(SpaceObject spaceObject) {
        this.spaceObject = spaceObject;
    }

    @Override
    protected void controlUpdate(float tpf) {
        spaceObject.setLocalTranslation(spaceObject.getWorldPosition());
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
