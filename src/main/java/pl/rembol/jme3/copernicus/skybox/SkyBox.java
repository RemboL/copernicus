package pl.rembol.jme3.copernicus.skybox;

import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Sphere;
import pl.rembol.jme3.copernicus.GameState;

public class SkyBox extends Node {

    private final GameState gameState;

    public SkyBox(GameState gameState, String textureName) {
        super("skybox");

        this.gameState = gameState;

        Sphere sphere = new Sphere(72, 72, -gameState.camera.getFrustumFar() * 0.9f);
//        sphere.setTextureMode(Sphere.TextureMode.Projected);
        Geometry skyboxGeometry = new Geometry("Box", sphere);
        Material skyboxMaterial = new Material(gameState.assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        skyboxMaterial.setTexture("DiffuseMap", gameState.assetManager.loadTexture(textureName));
        skyboxMaterial.setBoolean("UseMaterialColors", true);
        skyboxMaterial.setColor("Ambient", ColorRGBA.White);
        skyboxMaterial.setFloat("Shininess", 0f);
        skyboxGeometry.setMaterial(skyboxMaterial);
        attachChild(skyboxGeometry);
        skyboxGeometry.setLocalRotation(new Quaternion().fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_X));

        gameState.rootNode.attachChild(this);

        addControl(new FollowCameraControl());
    }

    private class FollowCameraControl extends AbstractControl {

        @Override
        protected void controlUpdate(float tpf) {
            this.getSpatial().setLocalTranslation(gameState.camera.getLocation());
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {
        }
    }

}
