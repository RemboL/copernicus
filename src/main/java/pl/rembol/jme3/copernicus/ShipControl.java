package pl.rembol.jme3.copernicus;

import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import pl.rembol.jme3.copernicus.input.KeyInputManager;
import pl.rembol.jme3.copernicus.input.MouseInputManager;
import pl.rembol.jme3.copernicus.selection.window.SelectionWindow;
import pl.rembol.jme3.copernicus.ship.Ship;

public class ShipControl implements AnalogListener, ActionListener {

    private final GameState gameState;

    private Ship ship;

    public ShipControl(GameState gameState) {
        this.gameState = gameState;
    }

    public void control(Ship ship) {
        this.ship = ship;
    }

    private void yawLeft(float value) {
        if (ship != null) {
            ship.yawLeft(value);
        }
    }

    private void yawRight(float value) {
        if (ship != null) {
            ship.yawRight(value);
        }
    }

    private void pitchUp(float value) {
        if (ship != null) {
            ship.pitchUp(value);
        }
    }

    private void pitchDown(float value) {
        if (ship != null) {
            ship.pitchDown(value);
        }
    }

    private void rollLeft(float value) {
        if (ship != null) {
            ship.rollLeft(value);
        }
    }

    private void rollRight(float value) {
        if (ship != null) {
            ship.rollRight(value);
        }
    }

    private void throttleUp(float value) {
        if (ship != null) {
            ship.throttleUp(value);
        }
    }

    private void throttleDown(float value) {
        if (ship != null) {
            ship.throttleDown(value);
        }
    }

    private void fireMissile() {
        if (ship != null) {
            ship.fireMissile();
        }
    }

    private void matchVelocityToTarget(float value) {
        if (ship != null && gameState.selectionManager.getSelectedObject() != null) {
            ship.matchVelocity(gameState.selectionManager.getSelectedObject().getVelocity(), value);
        }
    }

    private void orientTowardsTarget(float value) {
        if (ship != null && gameState.selectionManager.getSelectedObject() != null) {
            ship.orientTowards(gameState.selectionManager.getSelectedObject().getPrecisePosition(), value);
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
            case KeyInputManager.M:
                matchVelocityToTarget(value);
                break;
            case KeyInputManager.O:
                orientTowardsTarget(value);
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
                case MouseInputManager.LEFT_CLICK:
                    fireMissile();
                    break;
            }
        }
    }

}
