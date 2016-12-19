package pl.rembol.jme3.copernicus;

import com.jme3.math.Vector2f;
import pl.rembol.jme3.copernicus.objects.SpaceObject;
import pl.rembol.jme3.copernicus.selection.window.SelectionWindow;
import pl.rembol.jme3.copernicus.ship.maneuver.MatchSpeedManeuver;
import pl.rembol.jme3.copernicus.ship.maneuver.MoveToAndMatchVelocityManeuver;
import pl.rembol.jme3.copernicus.ship.maneuver.OrientShipManeuver;

public class ShipControl {

    public static final float MOUSEFLIGHT_DEADZONE = .1f;
    
    private boolean mouseFlight = false;

    private final GameState gameState;

    public ShipControl(GameState gameState) {
        this.gameState = gameState;
    }

    public void yawLeft(float value) {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.yawLeft(value);
        }
    }

    public void yawRight(float value) {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.yawRight(value);
        }
    }

    public void pitchUp(float value) {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.pitchUp(value);
        }
    }

    public void pitchDown(float value) {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.pitchDown(value);
        }
    }

    public void rollLeft(float value) {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.rollLeft(value);
        }
    }

    public void rollRight(float value) {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.rollRight(value);
        }
    }

    public void throttleUp(float value) {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.throttleUp(value);
        }
    }

    public void throttleDown(float value) {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
//            gameState.controlledShip.throttleDown(value);
            gameState.controlledShip.stopEngines();
        }
    }

    public void fireMissile() {
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
            gameState.controlledShip.fireMissile();
        }
    }

    public void matchSpeedManeuver() {
        if (gameState.controlledShip != null && gameState.selectionManager.getSelectedObject() != null) {
            gameState.controlledShip.setManeuver(new MatchSpeedManeuver(gameState.selectionManager.getSelectedObject()));
            disableMouseFlight();
        }
    }

    public void orientTowardsTargetManeuver() {
        if (gameState.controlledShip != null && gameState.selectionManager.getSelectedObject() != null) {
            gameState.controlledShip.setManeuver(new OrientShipManeuver(gameState.selectionManager.getSelectedObject()));
            disableMouseFlight();
        }
    }
    
    public void moveToAndMatchVelocityManeuver(SpaceObject spaceObject, double distance) {
        if (gameState.controlledShip != null && gameState.selectionManager.getSelectedObject() != null) {
            gameState.controlledShip.setManeuver(new MoveToAndMatchVelocityManeuver(spaceObject, distance));
            disableMouseFlight();
        }
    }

    public void openSelectionWindow() {
        gameState.windowManager.addWindowCentered(new SelectionWindow(gameState));
    }

    public void disableMouseFlight() {
        mouseFlight = false;
    }
    
    public void enableMouseFlight() {
        mouseFlight = true;
        if (gameState.controlledShip != null) {
            gameState.controlledShip.disableAutoPilot();
        }
    }
    
    public void toggleMouseFlight() {
        if (mouseFlight) {
            disableMouseFlight();
        } else {
            enableMouseFlight();
        }
    }

    public boolean isMouseFlight() {
        return mouseFlight;
    }

    public void updateMouseFlight(float tpf) {
        if (mouseFlight) {
            float halfWidth = gameState.camera.getWidth() / 2;
            float halfHeight = gameState.camera.getHeight() / 2;
            Vector2f cursorPosition = new Vector2f(
                    gameState.inputManager.getCursorPosition().x / halfWidth - 1,
                    gameState.inputManager.getCursorPosition().y / halfHeight - 1);
            if (cursorPosition.x < -MOUSEFLIGHT_DEADZONE) {
                yawLeft(tpf * (-cursorPosition.x - MOUSEFLIGHT_DEADZONE) / (1 - MOUSEFLIGHT_DEADZONE));
            } else if (cursorPosition.x > MOUSEFLIGHT_DEADZONE) {
                yawRight(tpf * (cursorPosition.x - MOUSEFLIGHT_DEADZONE) / (1 - MOUSEFLIGHT_DEADZONE));
            }

            if (cursorPosition.y < -MOUSEFLIGHT_DEADZONE) {
                pitchUp(tpf * (-cursorPosition.y - MOUSEFLIGHT_DEADZONE) / (1 - MOUSEFLIGHT_DEADZONE));
            } else if (cursorPosition.y > MOUSEFLIGHT_DEADZONE) {
                pitchDown(tpf * (cursorPosition.y - MOUSEFLIGHT_DEADZONE) / (1 - MOUSEFLIGHT_DEADZONE));
            }
        }
    }
}
