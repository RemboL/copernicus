package pl.rembol.jme3.copernicus.camera;

import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;
import com.jme3.scene.plugins.blender.math.Vector3d;
import pl.rembol.jme3.copernicus.objects.SpaceObject;

public class FocusCamera extends CameraNode {

    private SpaceObject focus;

    public FocusCamera(Camera camera) {
        super("Camera Node", camera);
        setControlDir(CameraControl.ControlDirection.SpatialToCamera);
    }

    public void setFocusAt(SpaceObject spaceObject) {
        this.focus = spaceObject;
        spaceObject.attachChild(this);
        this.setLocalTranslation(new Vector3f(0, .003f, -.01f));

        Node cameraFocusNode = new Node("Camera focus node");
        spaceObject.attachChild(cameraFocusNode);
        cameraFocusNode.setLocalTranslation(new Vector3f(0, 0, 1f));

        lookAt(cameraFocusNode.getWorldTranslation(), Vector3f.UNIT_Y);

        spaceObject.detachChild(cameraFocusNode);
    }

    public Vector3d getCameraPosition() {
        if (focus == null) {
            return new Vector3d(getCamera().getWorldCoordinates(Vector2f.UNIT_XY, 0));
        }

        return focus.getPrecisePosition();
    }

}
