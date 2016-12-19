package pl.rembol.jme3.copernicus.selection.window;

import java.util.Arrays;
import java.util.List;

import pl.rembol.jme3.copernicus.GameState;

public class MoveToAndMatchVelocityAction extends SelectionAction {

    private final double distance;
    
    public MoveToAndMatchVelocityAction(GameState gameState,
                                        SelectionRow selectionRow,
                                        double distance) {
        super(gameState, selectionRow, getText(distance, selectionRow.object.getName()));
        this.distance = distance;
    }

    protected static List<String> getText(double distance, String name) {
        return Arrays.asList("Move to distance of "+distance+"km", "from "+name+ " and match velocity");
    }

    @Override
    protected void invokeAction() {
        gameState.shipControl.moveToAndMatchVelocityManeuver(spaceObject, distance);
    }
}
