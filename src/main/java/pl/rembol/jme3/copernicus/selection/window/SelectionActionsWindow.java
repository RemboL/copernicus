package pl.rembol.jme3.copernicus.selection.window;

import java.util.List;

import com.jme3.math.Vector2f;
import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.game.gui.MouseButton;
import pl.rembol.jme3.game.gui.window.Window;

public class SelectionActionsWindow extends Window<GameState> {

    private static final int OFFSET = 5;
    
    private final SelectionWindow parent;

    public SelectionActionsWindow(GameState gameState, String name, SelectionWindow parent,
                                  List<SelectionAction> actions) {
        super(gameState, "select", calculateSize(actions));
        
        int offset = OFFSET;
        
        for (SelectionAction action : actions) {
            action.setLocalTranslation(OFFSET, offset, 1);
            offset += action.getTotalHeight() + OFFSET;
            attachChild(action);
        }
        actions.forEach(this::attachChild);
        
        this.parent = parent;
    }

    public void closeSelectionWindow() {
        close();
        parent.close();
    }

    public void closeActions() {
        close();
    }
    
    private static Vector2f calculateSize(List<SelectionAction> actions) {
        int height = 5;
        for (SelectionAction action : actions) {
            height += action.getTotalHeight() + OFFSET;
        }
        return new Vector2f(SelectionAction.WIDTH + 2 * OFFSET, height);
    }

    @Override
    protected void onClickOutside(MouseButton button) {
        this.close();
        parent.click(gameState.inputManager.getCursorPosition().clone(), button);
    }

}
