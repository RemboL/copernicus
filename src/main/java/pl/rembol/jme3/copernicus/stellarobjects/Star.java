package pl.rembol.jme3.copernicus.stellarobjects;

import com.jme3.light.PointLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Sphere;
import com.jme3.shadow.PointLightShadowRenderer;
import pl.rembol.jme3.copernicus.GameState;

public class Star extends Node {

    private PointLight pointLight;

    public Star(GameState gameState, String textureName, String name) {
        super(name);
        Geometry geometry = new Geometry("sphere", new Sphere(36, 36, 1));
        Material material = new Material(gameState.assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        material.setTexture("DiffuseMap", gameState.assetManager.loadTexture(textureName));
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Ambient", ColorRGBA.White.mult(3));
        geometry.setMaterial(material);
        attachChild(geometry);
        geometry.setShadowMode(RenderQueue.ShadowMode.Off);
        geometry.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));


        pointLight = new PointLight();
        pointLight.setColor(ColorRGBA.White);
        gameState.rootNode.addLight(pointLight);
        pointLight.setPosition(getWorldTranslation());

        PointLightShadowRenderer pointLightShadowRenderer = new PointLightShadowRenderer(gameState.assetManager, 1024);
        pointLightShadowRenderer.setLight(pointLight);
        gameState.simpleApplication.getViewPort().addProcessor(pointLightShadowRenderer);

        gameState.rootNode.attachChild(this);
        addControl(new UpdateLightPositionControl());

    }

    private class UpdateLightPositionControl extends AbstractControl {

        @Override
        protected void controlUpdate(float tpf) {
            pointLight.setPosition(getWorldTranslation());
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {

        }
    }
}
