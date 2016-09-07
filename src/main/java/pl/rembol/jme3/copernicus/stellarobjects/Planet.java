package pl.rembol.jme3.copernicus.stellarobjects;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import pl.rembol.jme3.copernicus.GameState;

public class Planet  extends Node {

    public Planet(GameState gameState, String textureName, String name) {
        super(name);
        Geometry geometry = new Geometry("sphere", new Sphere(36, 36, 1));
        Material material = new Material(gameState.assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        material.setTexture("DiffuseMap", gameState.assetManager.loadTexture(textureName));
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse", ColorRGBA.White);
        material.setColor("Specular", ColorRGBA.White);
        geometry.setMaterial(material);
        attachChild(geometry);
        geometry.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
        geometry.addControl(new RotateControl(.2f, Vector3f.UNIT_Z));

        Geometry atmosphereGeometry = new Geometry("sphere", new Sphere(36, 36, 1.05f));
        Material atmosphereMaterial = new Material(gameState.assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        atmosphereMaterial.setTexture("DiffuseMap", gameState.assetManager.loadTexture("earth_atmosphere.png"));
        atmosphereMaterial.setBoolean("UseMaterialColors", true);
        atmosphereMaterial.setColor("Diffuse", ColorRGBA.White);
        atmosphereMaterial.setColor("Specular", ColorRGBA.White);
        atmosphereGeometry.setMaterial(atmosphereMaterial);
        attachChild(atmosphereGeometry);
        atmosphereGeometry.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));

        atmosphereMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        atmosphereGeometry.setQueueBucket(RenderQueue.Bucket.Transparent);
        atmosphereGeometry.setShadowMode(RenderQueue.ShadowMode.Receive);

        atmosphereGeometry.addControl(new RotateControl(.19f, Vector3f.UNIT_Z));

        gameState.rootNode.attachChild(this);
    }
}
