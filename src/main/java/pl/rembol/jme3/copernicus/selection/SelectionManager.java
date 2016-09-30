package pl.rembol.jme3.copernicus.selection;

import com.jme3.app.state.AbstractAppState;
import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.objects.SpaceObject;

import java.text.DecimalFormat;
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
        status.add("Distance: " + format(gameState.focusCamera.getCameraPosition().distance(selectedObject.getPrecisePosition()) / 1000) + "m");
        status.add("Relative velocity: " + format(gameState.focusCamera.getVelocity().distance(selectedObject.getVelocity()) / 1000) + "m/s");

        return status;
    }

    private String format(double number) {
        String scale;

        if (number < Math.pow(10, 3)) {
            scale = "";
        } else if (number < Math.pow(10, 6)) {
            scale = "k";
            number /= Math.pow(10, 3);
        } else if (number < Math.pow(10, 9)) {
            scale = "M";
            number /= Math.pow(10, 6);
        } else {
            scale = "G";
            number /= Math.pow(10, 9);
        }

        return new DecimalFormat("0.000").format(number) + scale;
    }

    private class UpdateSelectionAppState extends AbstractAppState {

        @Override
        public void update(float tpf) {
            selectionText.updateText(printStatus());

        }

    }

}
