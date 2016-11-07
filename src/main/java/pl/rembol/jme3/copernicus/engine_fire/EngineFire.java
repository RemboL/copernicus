package pl.rembol.jme3.copernicus.engine_fire;

import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.shadow.PointLightShadowRenderer;
import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.ship.Ship;
import pl.rembol.jme3.utils.Materials;

public class EngineFire extends Node {

    private final GameState gameState;
    private final PointLight pointLight;
    private final PointLightShadowRenderer pointLightShadowRenderer;

    private float power = 0f;

    private Node model;

    private Ship ship;

    public EngineFire(GameState gameState, float scale, Ship ship) {
        super("engineFire");
        this.gameState = gameState;
        this.ship = ship;

        model =(Node) gameState.assetManager
                .loadModel("engine_fire/engine_fire.mesh.xml");
        attachChild(model);
        setLocalScale(scale);
        model.setQueueBucket(RenderQueue.Bucket.Transparent);
        model.setShadowMode(RenderQueue.ShadowMode.Off);

        ship.addControl(new EngineControl());

        pointLight = new PointLight();
        pointLight.setColor(ColorRGBA.Yellow);
        pointLight.setRadius(scale * 10);
        gameState.rootNode.addLight(pointLight);
        pointLight.setPosition(getWorldTranslation());

        pointLightShadowRenderer = new PointLightShadowRenderer(gameState.assetManager, 1024);
        pointLightShadowRenderer.setLight(pointLight);
        pointLightShadowRenderer.setShadowIntensity(1f);
        gameState.simpleApplication.getViewPort().addProcessor(pointLightShadowRenderer);
    }

    private void setPower(float power) {
        this.power = Math.max(Math.min(power, 1) * (.9f + FastMath.nextRandomFloat() * .2f), 0f);
        updatePower();
    }

    private void updatePower() {
        Materials.setAlpha(model, "Diffuse", power * .5f);
        Materials.setAlpha(model, "Specular", power * .5f);
        Materials.setAlpha(model, "Ambient", power * .5f);
        model.setLocalScale(new Vector3f(1f, 1f, power));
        pointLight.setColor(new ColorRGBA(1f, .85f, .5f, 1f).mult(power * .5f));
    }

    private class EngineControl extends AbstractControl {

        @Override
        protected void controlUpdate(float tpf) {
            setPower(ship.getAcceleration());
            pointLight.setPosition(getWorldTranslation());
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {

        }

        @Override
        public void setSpatial(Spatial spatial) {
            if (spatial == null) {
                gameState.simpleApplication.getViewPort().removeProcessor(pointLightShadowRenderer);
                gameState.rootNode.removeLight(pointLight);
            }
        }
    }

}
