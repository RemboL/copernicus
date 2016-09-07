package pl.rembol.jme3.copernicus;

import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import pl.rembol.jme3.copernicus.ship.Ship;

public class ShipControl implements AnalogListener {

    private static final String YAW_LEFT = "shipControl_yawLeft";

    private static final String YAW_RIGHT = "shipControl_yawRight";

    private static final String PITCH_UP = "shipControl_pitchUp";

    private static final String PITCH_DOWN = "shipControl_pitchDown";

    private static final String ROLL_LEFT = "shipControl_rollLeft";

    private static final String ROLL_RIGHT = "shipControl_rollRight";

    private Ship ship;

    ShipControl(InputManager inputManager) {

        registerInput(inputManager);
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

    @Override
    public void onAnalog(String name, float value, float tpf) {
        switch (name) {
            case YAW_LEFT:
                yawLeft(value);
                break;
            case YAW_RIGHT:
                yawRight(value);
                break;
            case PITCH_UP:
                pitchUp(value);
                break;
            case PITCH_DOWN:
                pitchDown(value);
                break;
            case ROLL_LEFT:
                rollLeft(value);
                break;
            case ROLL_RIGHT:
                rollRight(value);
                break;
        }
    }

    private void registerInput(InputManager inputManager) {
        registerKey(inputManager, PITCH_UP, KeyInput.KEY_W);
        registerKey(inputManager, PITCH_DOWN, KeyInput.KEY_S);
        registerKey(inputManager, YAW_LEFT, KeyInput.KEY_A);
        registerKey(inputManager, YAW_RIGHT, KeyInput.KEY_D);
        registerKey(inputManager, ROLL_LEFT, KeyInput.KEY_Q);
        registerKey(inputManager, ROLL_RIGHT, KeyInput.KEY_E);
    }

    private void registerKey(InputManager inputManager, String name, int key) {
        inputManager.addMapping(name, new KeyTrigger(key));
        inputManager.addListener(this, name);
    }

}
