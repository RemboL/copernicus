package pl.rembol.jme3.copernicus;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import pl.rembol.jme3.copernicus.ship.Ship;

public class ShipControl implements AnalogListener {

    private static final String ROTATE_LEFT = "shipControl_rotateLeft";

    private static final String ROTATE_RIGHT = "shipControl_rotateRight";

    private static final String TILT_UP = "shipControl_tiltUp";

    private static final String TILT_DOWN = "shipControl_tiltDown";

    private static final String YAW_LEFT = "shipControl_yawLeft";

    private static final String YAW_RIGHT = "shipControl_yawRight";

    private Ship ship;

    ShipControl(InputManager inputManager) {

        registerInput(inputManager);
    }

    public void control(Ship ship) {
        this.ship = ship;
    }

    private void rotateLeft(float value) {
        if (ship != null) {
            ship.steerLeft(value);
        }
    }

    private void rotateRight(float value) {
        if (ship != null) {
            ship.steerRight(value);
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

    @Override
    public void onAnalog(String name, float value, float tpf) {
        switch (name) {
            case ROTATE_LEFT:
                rotateLeft(value);
                break;
            case ROTATE_RIGHT:
                rotateRight(value);
                break;
            case TILT_UP:
                pitchUp(value);
                break;
            case TILT_DOWN:
                pitchDown(value);
                break;
            case YAW_LEFT:
                yawLeft(value);
                break;
            case YAW_RIGHT:
                yawRight(value);
                break;
        }
    }

    private void registerInput(InputManager inputManager) {
        registerKey(inputManager, TILT_UP, KeyInput.KEY_W);
        registerKey(inputManager, TILT_DOWN, KeyInput.KEY_S);
        registerKey(inputManager, ROTATE_LEFT, KeyInput.KEY_A);
        registerKey(inputManager, ROTATE_RIGHT, KeyInput.KEY_D);
        registerKey(inputManager, YAW_LEFT, KeyInput.KEY_Q);
        registerKey(inputManager, YAW_RIGHT, KeyInput.KEY_E);
    }

    private void registerKey(InputManager inputManager, String name, int key) {
        inputManager.addMapping(name, new KeyTrigger(key));
        inputManager.addListener(this, name);
    }

}
