package pl.rembol.jme3.copernicus.camera;

import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.CameraNode;
import com.jme3.scene.Node;
import com.jme3.scene.control.CameraControl;

public class FocusCamera extends CameraNode {

    public FocusCamera(Camera camera) {
        super("Camera Node", camera);
        setControlDir(CameraControl.ControlDirection.SpatialToCamera);
    }

    public void setFocusAt(Node node) {
        node.attachChild(this);
        this.setLocalTranslation(new Vector3f(0, .01f, -.01f));

        Node cameraFocusNode = new Node("Camera focus node");
        node.attachChild(cameraFocusNode);
        cameraFocusNode.setLocalTranslation(new Vector3f(0, 0, .01f));

        lookAt(cameraFocusNode.getWorldTranslation(), Vector3f.UNIT_Y);

        node.detachChild(cameraFocusNode);
    }

}
