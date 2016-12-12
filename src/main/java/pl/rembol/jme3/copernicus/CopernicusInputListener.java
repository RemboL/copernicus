package pl.rembol.jme3.copernicus;

import pl.rembol.jme3.game.input.InputListener;
import pl.rembol.jme3.game.input.KeyInputManager;
import pl.rembol.jme3.game.input.MouseInputManager;

public class CopernicusInputListener extends InputListener<GameState> {

    public CopernicusInputListener(GameState gameState) {
        super(gameState);
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        switch (name) {
            case KeyInputManager.A:
                gameState.shipControl.yawLeft(value);
                break;
            case KeyInputManager.D:
                gameState.shipControl.yawRight(value);
                break;
            case KeyInputManager.W:
                gameState.shipControl.pitchUp(value);
                break;
            case KeyInputManager.S:
                gameState.shipControl.pitchDown(value);
                break;
            case KeyInputManager.Q:
                gameState.shipControl.rollLeft(value);
                break;
            case KeyInputManager.E:
                gameState.shipControl.rollRight(value);
                break;
            case MouseInputManager.MOUSE_SCROLL_UP:
                gameState.shipControl.throttleUp(value);
                break;
            case MouseInputManager.MOUSE_SCROLL_DOWN:
                gameState.shipControl.throttleDown(value);
                break;
        }
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (isPressed) {
            switch (name) {
                case KeyInputManager.TAB:
                    gameState.shipControl.openSelectionWindow();
                    break;
                case KeyInputManager.M:
                    gameState.shipControl.matchSpeedManeuver();
                    break;
                case KeyInputManager.O:
                    gameState.shipControl.orientTowardsTargetManeuver();
                    break;
                case KeyInputManager.SPACE:
                    gameState.shipControl.toggleMouseFlight();
                    break;
                case MouseInputManager.LEFT_CLICK:
                    gameState.shipControl.fireMissile();
                    break;
            }
        }
    }
}
