package pl.rembol.jme3.copernicus.selection;

import com.jme3.app.state.AbstractAppState;
import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.objects.SpaceObject;

import java.util.ArrayList;
import java.util.List;

public class SelectionManager {

    private final GameState gameState;

    private SpaceObject selectedObject;

    private SelectionText selectionText;

    public SelectionManager(GameState gameState) {
        this.gameState = gameState;

        selectionText = new SelectionText(gameState);

        gameState.simpleApplication.getStateManager().attach(new UpdateSelectionAppState());
    }

    public void select(SpaceObject selectedObject) {
        this.selectedObject = selectedObject;
    }

    private List<String> printStatus() {
        if (selectedObject == null) {
            return new ArrayList<>();
        }

        List<String> status = new ArrayList<>();
        status.add("Name: " + selectedObject.getName());
        status.add("Distance: " + SelectionTextUtils.distance(gameState, selectedObject) + "m");
        status.add("Relative velocity: " + SelectionTextUtils.relativeVelocity(gameState, selectedObject) + "m/s");

        return status;
    }

    private class UpdateSelectionAppState extends AbstractAppState {

        @Override
        public void update(float tpf) {
            selectionText.updateText(printStatus());

        }

    }

}
