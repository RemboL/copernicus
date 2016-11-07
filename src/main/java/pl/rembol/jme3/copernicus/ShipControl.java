package pl.rembol.jme3.copernicus;

import pl.rembol.jme3.copernicus.selection.window.SelectionWindow;
import pl.rembol.jme3.copernicus.ship.maneuver.MatchSpeedManeuver;
import pl.rembol.jme3.copernicus.ship.maneuver.OrientShipManeuver;
import pl.rembol.jme3.game.input.InputListener;
import pl.rembol.jme3.game.input.KeyInputManager;
import pl.rembol.jme3.game.input.MouseInputManager;

public class ShipControl extends InputListener<GameState> {

    public ShipControl(GameState gameState) {
        super(gameState);
    }

    private void yawLeft(float value) {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.yawLeft(value);
        }
    }

    private void yawRight(float value) {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.yawRight(value);
        }
    }

    private void pitchUp(float value) {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.pitchUp(value);
        }
    }

    private void pitchDown(float value) {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.pitchDown(value);
        }
    }

    private void rollLeft(float value) {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.rollLeft(value);
        }
    }

    private void rollRight(float value) {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.rollRight(value);
        }
    }

    private void throttleUp(float value) {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.throttleUp(value);
        }
    }

    private void throttleDown(float value) {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.throttleDown(value);
        }
    }

    private void fireMissile() {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.fireMissile();
        }
    }

    private void matchSpeedManeuver() {
        if (gameState.controlledShip != null && gameState.selectionManager.getSelectedObject() != null) {
            gameState.controlledShip.setManeuver(new MatchSpeedManeuver(gameState.selectionManager.getSelectedObject()));
        }
    }

    private void orientTowardsTargetManeuver() {
        if (gameState.controlledShip != null && gameState.selectionManager.getSelectedObject() != null) {
            gameState.controlledShip.setManeuver(new OrientShipManeuver(gameState.selectionManager.getSelectedObject()));
        }
    }

    private void openSelectionWindow() {
        gameState.windowManager.addWindowCentered(new SelectionWindow(gameState));
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        switch (name) {
            case KeyInputManager.A:
                yawLeft(value);
                break;
            case KeyInputManager.D:
                yawRight(value);
                break;
            case KeyInputManager.W:
                pitchUp(value);
                break;
            case KeyInputManager.S:
                pitchDown(value);
                break;
            case KeyInputManager.Q:
                rollLeft(value);
                break;
            case KeyInputManager.E:
                rollRight(value);
                break;
            case MouseInputManager.MOUSE_SCROLL_UP:
                throttleUp(value);
                break;
            case MouseInputManager.MOUSE_SCROLL_DOWN:
                throttleDown(value);
                break;
        }
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed) {
            switch (name) {
                case KeyInputManager.TAB:
                    openSelectionWindow();
                    break;
                case KeyInputManager.M:
                    matchSpeedManeuver();
                    break;
                case KeyInputManager.O:
                    orientTowardsTargetManeuver();
                    break;
                case MouseInputManager.LEFT_CLICK:
                    fireMissile();
                    break;
            }
        }
    }

}
