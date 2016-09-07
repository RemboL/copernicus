package pl.rembol.jme3.copernicus.stellarobjects;

import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;

public class RotateControl extends AbstractControl {

    private float rotationSpeed;

    private Vector3f axis;

    public RotateControl(float rotationSpeed, Vector3f axis) {
        this.rotationSpeed = rotationSpeed;
        this.axis = axis;
    }

    @Override
    protected void controlUpdate(float tpf) {
        getSpatial().rotate(new Quaternion().fromAngleAxis(tpf * rotationSpeed, axis));
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
