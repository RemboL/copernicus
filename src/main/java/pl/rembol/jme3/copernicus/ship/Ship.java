package pl.rembol.jme3.copernicus.ship;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import pl.rembol.jme3.copernicus.GameState;

public class Ship extends Node {

    private Node forwardNode;

    public Ship(GameState gameState, String modelName) {
        Node model =(Node) gameState.assetManager
                .loadModel(modelName);
        attachChild(model);
        model.setLocalScale(.001f);
        gameState.rootNode.attachChild(this);

        forwardNode = new Node("forward node");
        forwardNode.setLocalTranslation(0, 0, 1);
        attachChild(forwardNode);

        addControl(new AlwaysMoveForwardControl());
    }

    static class AlwaysMoveForwardControl extends AbstractControl {

        @Override
        protected void controlUpdate(float tpf) {
            ((Ship) getSpatial()).moveForward(tpf);
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {

        }
    }

    void moveForward(float value) {
        move(forwardNode.getWorldTranslation().subtract(getWorldTranslation()).normalize().mult(value));
    }

    public void steerLeft(float value) {
        rotate(0, value, 0);
    }

    public void steerRight(float value) {
        rotate(0, -value, 0);
    }

    public void pitchUp(float value) {
        rotate(value, 0, 0);
    }

    public void pitchDown(float value) {
        rotate(-value, 0, 0);
    }

    public void yawLeft(float value) {
        rotate(0, 0, -value);
    }

    public void yawRight(float value) {
        rotate(0, 0, value);
    }
    
    


}
