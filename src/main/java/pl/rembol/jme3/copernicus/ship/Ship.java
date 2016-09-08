package pl.rembol.jme3.copernicus.ship;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.objects.SpaceObject;

public class Ship extends SpaceObject {

    private float speed = 0f;

    public Ship(GameState gameState, String modelName) {
        super(gameState, "ship");

        Node model =(Node) gameState.assetManager
                .loadModel(modelName);
        attachChild(model);
        model.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        model.setLocalScale(.001f);
        gameState.rootNode.attachChild(this);

        addControl(new AlwaysMoveForwardControl());
    }

    private class AlwaysMoveForwardControl extends AbstractControl {

        @Override
        protected void controlUpdate(float tpf) {
            moveForward(tpf);
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {

        }
    }

    void moveForward(float value) {
        preciseMove(getWorldRotation().mult(Vector3f.UNIT_Z).mult(value * speed));
    }

    public void yawLeft(float value) {
        rotate(0, value, 0);
    }

    public void yawRight(float value) {
        rotate(0, -value, 0);
    }

    public void pitchUp(float value) {
        rotate(value, 0, 0);
    }

    public void pitchDown(float value) {
        rotate(-value, 0, 0);
    }

    public void rollLeft(float value) {
        rotate(0, 0, -value);
    }

    public void rollRight(float value) {
        rotate(0, 0, value);
    }

    public void accelerate(float value) {
        speed += value;
    }

    public void decelerate(float value) {
        speed -= value;
    }


}
