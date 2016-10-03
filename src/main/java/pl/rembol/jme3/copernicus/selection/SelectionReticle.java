package pl.rembol.jme3.copernicus.selection;

import com.jme3.bounding.BoundingVolume;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Line;
import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.objects.SpaceObject;

public class SelectionReticle extends Node {

    private final GameState gameState;

    private Node reticle;

    public SelectionReticle(GameState gameState) {
        this.gameState = gameState;

        initReticle();

        setLocalScale(10);

        gameState.guiNode.attachChild(this);

        addControl(new UpdateSelectionReticleControl());
    }

    private void initReticle() {
        reticle = new Node("reticle");
        reticle.attachChild(new Geometry(name, new Line(new Vector3f(-1, -1, 0), new Vector3f(-1, 1, 0))));
        reticle.attachChild(new Geometry(name, new Line(new Vector3f(-1, -1, 0), new Vector3f(1, -1, 0))));
        reticle.attachChild(new Geometry(name, new Line(new Vector3f(1, -1, 0), new Vector3f(1, 1, 0))));
        reticle.attachChild(new Geometry(name, new Line(new Vector3f(-1, 1, 0), new Vector3f(1, 1, 0))));
        Material material = new Material(gameState.assetManager, "Common/MatDefs/Gui/Gui.j3md");
        material.setColor("Color", new ColorRGBA(0f, 1f, 0f, .5f));
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        reticle.setQueueBucket(RenderQueue.Bucket.Gui);
        reticle.setCullHint(CullHint.Never);
        reticle.setMaterial(material);
    }

    private void updateReticle() {
        SpaceObject object = gameState.selectionManager.getSelectedObject();
        if (object == null) {
            if (reticle.getParent() == this) {
                detachChild(reticle);
            }
            return;
        }

        Vector3f position = object.getWorldTranslation();
        if (position == null) {
            if (reticle.getParent() == this) {
                detachChild(reticle);
            }
            return;
        }

        BoundingVolume boundingVolume = object.getWorldBound();
        if (boundingVolume == null) {
            if (reticle.getParent() == this) {
                detachChild(reticle);
            }
            return;
        }

        if (Math.abs(gameState.camera.getDirection().angleBetween(position)) > FastMath.HALF_PI) {
            if (reticle.getParent() == this) {
                detachChild(reticle);
            }
            return;
        }

        if (reticle.getParent() == null) {
            attachChild(reticle);
        }


        Vector3f screenCoordinates = gameState.camera.getScreenCoordinates(position);
        screenCoordinates.z = -1;

        setLocalTranslation(screenCoordinates);
        setLocalScale(Math.max(FastMath.pow(boundingVolume.getVolume(), 0.3333f) / 2, 10));
    }

    private class UpdateSelectionReticleControl extends AbstractControl {

        @Override
        protected void controlUpdate(float tpf) {
            updateReticle();
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {

        }
    }
}
