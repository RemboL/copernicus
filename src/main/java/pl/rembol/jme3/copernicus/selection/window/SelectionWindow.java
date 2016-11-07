package pl.rembol.jme3.copernicus.selection.window;

import com.jme3.math.Vector2f;
import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.stellarobjects.AstralObject;
import pl.rembol.jme3.game.gui.window.Window;

import java.util.ArrayList;
import java.util.List;

public class SelectionWindow extends Window<GameState> {

    public SelectionWindow(GameState gameState) {
        super(gameState, "select", new Vector2f(510, 300));

        List<AstralObject> spaceObjects = new ArrayList<>();
        spaceObjects.addAll(gameState.stellarSystem.getAstralObjects());
        
        resize(new Vector2f(510, 50 * spaceObjects.size() + 5));
        
        for (int i = 0; i < spaceObjects.size(); i++) {
            addRow(spaceObjects.get(i), i);
        }
    }

    private void addRow(AstralObject astralObject, int index) {
        SelectionRow row = new SelectionRow(gameState, this, astralObject);
        attachChild(row);
        row.setLocalTranslation(5, size.y - 50 - index * 50, 1);
    }

}
