package pl.rembol.jme3.copernicus.effects;

import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Sphere;
import com.jme3.shadow.PointLightShadowRenderer;
import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.objects.KeepTranslationRelativeToCameraFocusControl;
import pl.rembol.jme3.copernicus.objects.SpaceObject;
import pl.rembol.jme3.copernicus.ship.ShipTranslationControl;

public class ExplosionEffect extends SpaceObject {

    private final PointLight pointLight;
    private final PointLightShadowRenderer pointLightShadowRenderer;
    private final Material explosionMaterial;
    private float strength = 1f; // determines radius of light effect and fire ball size

    public ExplosionEffect(GameState gameState, SpaceObject origin, float strength) {
        super(gameState, "Explosion");
        this.strength = strength;

        Geometry geometry = new Geometry("sphere", new Sphere(36, 36, 1f));
        explosionMaterial = new Material(gameState.assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        explosionMaterial.setTexture("DiffuseMap", gameState.assetManager.loadTexture("sun.jpg"));
        explosionMaterial.setBoolean("UseMaterialColors", true);
        explosionMaterial.setColor("Diffuse", ColorRGBA.White);
        explosionMaterial.setColor("Ambient", ColorRGBA.White);
        explosionMaterial.setColor("Specular", ColorRGBA.White);
        geometry.setMaterial(explosionMaterial);
        innerNode.attachChild(geometry);
        innerNode.setLocalScale(0f);
        geometry.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));

        explosionMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        geometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        geometry.setShadowMode(RenderQueue.ShadowMode.Off);


        setPrecisePosition(origin.getPrecisePosition());
        accelerate(origin.getVelocity());

        pointLight = new PointLight();
        pointLight.setColor(ColorRGBA.White);
        pointLight.setRadius(strength);
        gameState.rootNode.addLight(pointLight);
        pointLight.setPosition(getWorldTranslation());

        pointLightShadowRenderer = new PointLightShadowRenderer(gameState.assetManager, 1024);
        pointLightShadowRenderer.setLight(pointLight);
        pointLightShadowRenderer.setShadowIntensity(1f);
        gameState.simpleApplication.getViewPort().addProcessor(pointLightShadowRenderer);
        addControl(new ExplosionControl());

    }

    private class ExplosionControl extends AbstractControl {

        private float maxTtl = 1f;

        private float ttl = maxTtl;

        @Override
        protected void controlUpdate(float tpf) {
            ttl -= tpf;

            if (ttl <= 0) {
                gameState.simpleApplication.getViewPort().removeProcessor(pointLightShadowRenderer);
                pointLight.setEnabled(false);
                destroy();
            } else {
                float scale = ttl / maxTtl;
                pointLight.setRadius(scale * strength);
                pointLight.setColor(ColorRGBA.Yellow.mult(scale));
                ColorRGBA newColor = ColorRGBA.Yellow.clone();
                newColor.a = scale;
                explosionMaterial.setColor("Ambient", newColor);
                explosionMaterial.setColor("Diffuse", newColor);
                explosionMaterial.setColor("Specular", newColor);
                innerNode.setLocalScale((1 - scale) * strength);
//                System.out.println("@#" +innerNode.getWorldTranslation());
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
    protected boolean isCollidable() {
        return false;
    }
}
