package pl.rembol.jme3.copernicus.ship;

import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.plugins.blender.math.Vector3d;
import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.effects.ExplosionEffect;
import pl.rembol.jme3.copernicus.engine_fire.EngineFire;
import pl.rembol.jme3.copernicus.missile.Missile;
import pl.rembol.jme3.copernicus.objects.KeepTranslationRelativeToCameraFocusControl;
import pl.rembol.jme3.copernicus.objects.SpaceObject;

public class Ship extends SpaceObject {

    private float acceleration = 0f;

    public Ship(GameState gameState, String modelName) {
        super(gameState, "ship");

        Node model =(Node) gameState.assetManager
                .loadModel(modelName);
        attachChild(model);
        model.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        model.setLocalScale(.001f);
        innerNode.attachChild(model);

        addControl(new AlwaysMoveForwardControl());

        EngineFire engineFire = new EngineFire(gameState, .00035f, this);
        engineFire.setLocalTranslation(new Vector3f(0f, 0.0001f, -.00065f));
        engineFire.rotate(0, FastMath.PI, 0);
        innerNode.attachChild(engineFire);
    }

    public float getAcceleration() {
        return acceleration;
    }

    private class AlwaysMoveForwardControl extends AbstractControl {

        @Override
        protected void controlUpdate(float tpf) {
            throttle(tpf);
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {

        }
    }

    void throttle(float value) {
        accelerate(new Vector3d(getWorldRotation().mult(Vector3f.UNIT_Z).mult(value * acceleration)));
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

    public void throttleUp(float value) {
        if (acceleration >= .001f) {
            acceleration *= 1.1;
        } else if (acceleration <= -.001f) {
            acceleration /= 1.1;
        } else if (acceleration < 0) {
            acceleration = 0;
        } else {
            acceleration = .001f;
        }
    }

    public void throttleDown(float value) {
        if (acceleration >= .001f) {
            acceleration /= 1.1;
        } else if (acceleration <= -.001f) {
            acceleration *= 1.1;
        } else if (acceleration > 0) {
            acceleration = 0;
        } else {
            acceleration = -.001f;
        }
    }

    public void orientTowards(Vector3d target, float value) {
        // please don't ask how this works...
        Vector3f targetDirection = getWorldRotation().inverse().mult(target.subtract(getPrecisePosition()).toVector3f()).normalize();
        if (targetDirection.distance(Vector3f.UNIT_Z) > .01f) {
            rotate(new Quaternion().fromAngleAxis(value, Vector3f.UNIT_Z.cross(targetDirection)));
        } else {
            rotate(new Quaternion().fromAngleAxis(targetDirection.distance(Vector3f.UNIT_Z), Vector3f.UNIT_Z.cross(targetDirection)));
        }
    }

    public void matchVelocity(Vector3d targetVelocity, float value) {
        acceleration = 0;
        Vector3d deltaVelocity = targetVelocity.subtract(getVelocity());

//        if (deltaVelocity.length() < .001) {
            accelerate(deltaVelocity);
//        } else {
//            accelerate(deltaVelocity.divide(deltaVelocity.length()).mult(value));
//        }
    }

    @Override
    protected KeepTranslationRelativeToCameraFocusControl createTranslationControl() {
        return new ShipTranslationControl(this);
    }

    @Override
    public void destroy() {
        ExplosionEffect explosionEffect = new ExplosionEffect(gameState, this, .01f);
        if (gameState.focusCamera.isFocusedOn(this)) {
            gameState.focusCamera.setFocusAt(explosionEffect);
        }
        super.destroy();

    }

    public Missile fireMissile() {
        return new Missile(gameState, this);
    }
}
