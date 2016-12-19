package pl.rembol.jme3.copernicus.selection.window;

import java.util.List;
import java.util.Optional;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.Vector2f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.objects.SpaceObject;
import pl.rembol.jme3.game.gui.Clickable;
import pl.rembol.jme3.geom.Rectangle2f;

abstract public class SelectionAction extends Node implements Clickable {

    protected static final int WIDTH = 300;

    protected static final int LINE_HEIGHT = 18;

    protected static final int OFFSET_HEIGHT = 2;

    private final int totalHeight;

    protected final GameState gameState;

    protected final SpaceObject spaceObject;

    public SelectionAction(GameState gameState,
                           SelectionRow selectionRow,
                           List<String> text) {
        this.gameState = gameState;
        this.spaceObject = selectionRow.object;

        totalHeight = OFFSET_HEIGHT + text.size() * LINE_HEIGHT;

        createShade();

        BitmapFont guiFont = gameState.assetManager.loadFont("Interface/Fonts/Default.fnt");
        for (int i = 0; i < text.size(); ++i) {
            BitmapText bitmapText = new BitmapText(guiFont);
            bitmapText.setSize(guiFont.getCharSet().getRenderedSize());
            attachChild(bitmapText);
            bitmapText.setText(text.get(i));
            bitmapText.setLocalTranslation(5, (text.size() - i) * LINE_HEIGHT + OFFSET_HEIGHT, 1);
        }
    }

    private void createShade() {
        Geometry rectangle = new Shade(gameState, name, WIDTH, totalHeight);

        attachChild(rectangle);
    }

    abstract protected void invokeAction();
    
    @Override
    public void onClick() {
        invokeAction();
        Optional.ofNullable(getParent())
                .filter(SelectionActionsWindow.class::isInstance)
                .map(SelectionActionsWindow.class::cast)
                .ifPresent(SelectionActionsWindow::closeSelectionWindow);
    }

    @Override
    public boolean isClicked(Vector2f cursorPosition) {
        return new Rectangle2f(
                new Vector2f(
                        getWorldTranslation().x,
                        getWorldTranslation().y
                ),
                new Vector2f(
                        getWorldTranslation().x + +WIDTH,
                        getWorldTranslation().y + totalHeight
                )
        ).isInside(cursorPosition);
    }

    public int getTotalHeight() {
        return totalHeight;
    }
}
