package pl.rembol.jme3.copernicus.missile;

import java.util.List;

import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.plugins.blender.math.Vector3d;
import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.effects.ExplosionEffect;
import pl.rembol.jme3.copernicus.objects.KeepTranslationRelativeToCameraFocusControl;
import pl.rembol.jme3.copernicus.objects.SpaceObject;
import pl.rembol.jme3.copernicus.ship.ShipTranslationControl;

public class Missile extends SpaceObject {

    private float ttl = 20f;

    private SpaceObject origin;

    private static final float ARMING_DISTANCE = 0.01f;

    private static final float PROXIMITY_DISTANCE = 0.002f;

    private boolean isArmed = false;

    public Missile(GameState gameState, SpaceObject origin) {
        super(gameState, "ship", .0005);
        this.origin = origin;

        Node model = (Node) gameState.assetManager
                .loadModel("missile/missile.blend");
        attachChild(model);
        model.setShadowMode(RenderQueue.ShadowMode.Receive);
        model.setLocalScale(.0003f);
        innerNode.attachChild(model);

        setPrecisePosition(origin.getPrecisePosition());
        accelerate(origin.getVelocity());
        setLocalRotation(origin.getWorldRotation());
        accelerate(new Vector3d(getWorldRotation().mult(Vector3f.UNIT_Z.mult(.1f))));

        addControl(new MissileControl());
    }

    private class MissileControl extends AbstractControl {

        @Override
        protected void controlUpdate(float tpf) {
            ttl -= tpf;

            if (ttl < 0) {
                destroy();
                return;
            }

            Vector3d originObjectPosition = origin.getPrecisePosition();
            if (!isArmed) {
                if (originObjectPosition == null || getPrecisePosition().distance(originObjectPosition) > ARMING_DISTANCE) {
                    isArmed = true;
                }
            }

            if (isArmed) {
                List<SpaceObject> objects = gameState.gravityAppState.getObjectsInProximity(getPrecisePosition(), PROXIMITY_DISTANCE);
                if (!objects.isEmpty()) {
                    objects.forEach(SpaceObject::destroy);
                    destroy();
                }
            }
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {

        }
    }

    @Override
    protected KeepTranslationRelativeToCameraFocusControl createTranslationControl() {
        return new ShipTranslationControl(this);
    }

    @Override
    public void destroy() {
        new ExplosionEffect(gameState, this, isArmed ? 0.002f : .00005f);
        super.destroy();
    }

    @Override
    protected boolean isCollidable() {
        return false;
    }
}
