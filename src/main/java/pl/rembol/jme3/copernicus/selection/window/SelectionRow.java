package pl.rembol.jme3.copernicus.selection.window;

import java.util.Arrays;
import java.util.List;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.Vector2f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
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

    SpaceObject object;

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
        Geometry rectangle = new Shade(gameState, name, WIDTH, HEIGHT);
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
    public void onRightClick() {
        gameState.windowManager.addWindow(
                new SelectionActionsWindow(gameState, "actions", parentWindow, createActions()),
                gameState.inputManager.getCursorPosition());
    }

    private List<SelectionAction> createActions() {
        return Arrays.asList(new MoveToAndMatchVelocityAction(gameState, this, 100_000d),
                new MoveToAndMatchVelocityAction(gameState, this, 200_000d));
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
