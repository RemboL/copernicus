package pl.rembol.jme3.copernicus.selection.window;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector2f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.shape.Quad;
import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.objects.SpaceObject;
import pl.rembol.jme3.copernicus.selection.SelectionTextUtils;
import pl.rembol.jme3.game.gui.Clickable;
import pl.rembol.jme3.geom.Rectangle2f;

class SelectionRow extends Node implements Clickable {
    
    private static final int WIDTH = 500;

    private static final int HEIGHT = 45;

    private final GameState gameState;
    
    private final SelectionWindow parentWindow;
    
    private BitmapText nameText;

    private BitmapText distanceText;

    private BitmapText velocityText;
    
    private SpaceObject object;

    SelectionRow(GameState gameState, SelectionWindow parentWindow, SpaceObject spaceObject) {
        this.gameState = gameState;
        this.parentWindow = parentWindow;
        
        createShade();

        nameText = initText(5);
        distanceText = initText(205);
        velocityText = initText(355);
        
        object = spaceObject;
        
        nameText.setText(object.getName());
        addControl(new UpdateTextControl());
    }

    private void createShade() {
        Geometry rectangle = new Geometry(name, new Quad(WIDTH, HEIGHT));
        Material material = new Material(gameState.assetManager, "Common/MatDefs/Gui/Gui.j3md");
        material.setColor("Color", new ColorRGBA(1f, 1f, 1f, .2f));
        material.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);

        rectangle.setQueueBucket(RenderQueue.Bucket.Gui);
        rectangle.setCullHint(CullHint.Never);
        rectangle.setMaterial(material);
        
        attachChild(rectangle);
    }
    
    private BitmapText initText(int x) {
        BitmapFont guiFont = gameState.assetManager.loadFont("Interface/Fonts/Default.fnt");
        BitmapText text = new BitmapText(guiFont);
        text.setSize(guiFont.getCharSet().getRenderedSize());
        attachChild(text);
        text.setLocalTranslation(x, 40, 1);
        return text;
    }
    
    @Override
    public void onClick() {
        gameState.selectionManager.select(object);
        parentWindow.close();
    }

    @Override
    public boolean isClicked(Vector2f cursorPosition) {
        return new Rectangle2f(
                new Vector2f(
                        getWorldTranslation().x,
                        getWorldTranslation().y
                ),
                new Vector2f(
                        getWorldTranslation().x + + WIDTH,
                        getWorldTranslation().y + HEIGHT
                )
        ).isInside(cursorPosition);
    }
    
    private class UpdateTextControl extends AbstractControl {

        @Override
        protected void controlUpdate(float tpf) {
            distanceText.setText(SelectionTextUtils.distance(gameState, object) + "m");
            velocityText.setText(SelectionTextUtils.relativeVelocity(gameState, object) + "m/s");
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {

        }
    }
}
