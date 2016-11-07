package pl.rembol.jme3.copernicus.stellarobjects;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Sphere;
import pl.rembol.jme3.copernicus.GameState;

public class Planet extends AstralObject {

    private Spatial planet;

    private Spatial atmosphere;

    public Planet(GameState gameState, String textureName, String name, float radius, double mass, float rotation) {
        super(gameState, name, radius, mass);
        Geometry planet = new Geometry("sphere", new Sphere(36, 36, radius));
        Material material = new Material(gameState.assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        material.setTexture("DiffuseMap", gameState.assetManager.loadTexture(textureName));
        material.setBoolean("UseMaterialColors", true);
        material.setColor("Diffuse", ColorRGBA.White);
        material.setColor("Specular", ColorRGBA.White);
        planet.setMaterial(material);
        innerNode.attachChild(planet);
        planet.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));
        planet.setShadowMode(RenderQueue.ShadowMode.CastAndReceive);
        planet.addControl(new RotateControl(rotation, Vector3f.UNIT_Z));
    }

    public void addAtmosphere(String texture, float height, float rotation) {
        Geometry atmosphere = new Geometry("sphere", new Sphere(36, 36, Math.max((float) radius * 1.05f, (float) radius + height)));
        Material atmosphereMaterial = new Material(gameState.assetManager,
                "Common/MatDefs/Light/Lighting.j3md");
        atmosphereMaterial.setTexture("DiffuseMap", gameState.assetManager.loadTexture(texture));
        atmosphereMaterial.setBoolean("UseMaterialColors", true);
        atmosphereMaterial.setColor("Diffuse", ColorRGBA.White);
        atmosphereMaterial.setColor("Specular", ColorRGBA.White);
        atmosphere.setMaterial(atmosphereMaterial);
        innerNode.attachChild(atmosphere);
        atmosphere.setLocalRotation(new Quaternion().fromAngleAxis(-FastMath.HALF_PI, Vector3f.UNIT_X));

        atmosphereMaterial.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        atmosphere.setQueueBucket(RenderQueue.Bucket.Transparent);
        atmosphere.setShadowMode(RenderQueue.ShadowMode.Receive);

        atmosphere.addControl(new RotateControl(rotation, Vector3f.UNIT_Z));
    }
}
