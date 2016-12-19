package pl.rembol.jme3.copernicus.selection.window;

import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import pl.rembol.jme3.copernicus.GameState;

public class Shade extends Geometry {

    public Shade(GameState gameState, String name, float width, float height) {
        super(name, new Quad(width, height));
        Material material = new Material(gameState.assetManager, "Common/MatDefs/Gui/Gui.j3md");
        material.setColor("Color", new ColorRGBA(1f, 1f, 1f, .2f));
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        setQueueBucket(RenderQueue.Bucket.Gui);
        setCullHint(CullHint.Never);
        setMaterial(material);
    }

}
