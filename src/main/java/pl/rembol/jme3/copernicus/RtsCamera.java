package pl.rembol.jme3.copernicus;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

class RtsCamera implements AnalogListener {

    private static final String ROTATE_LEFT = "rtsCamera_rotateLeft";

    private static final String ROTATE_RIGHT = "rtsCamera_rotateRight";

    private static final String TILT_UP = "rtsCamera_tiltUp";

    private static final String TILT_DOWN = "rtsCamera_tiltDown";

    private float rotation = 0f;

    private float rotationSpeed = 1f;

    private float tilt = 45f * FastMath.DEG_TO_RAD;

    private final GameState gameState;

    RtsCamera(GameState gameState) {
        this.gameState = gameState;

        updateCamera();

        registerInput();
    }

    private void updateCamera() {
        Quaternion rotationQuaternion = new Quaternion().fromAngleAxis(
                rotation, Vector3f.UNIT_Y).mult(
                new Quaternion().fromAngleAxis(tilt, Vector3f.UNIT_X));
        gameState.camera.setRotation(rotationQuaternion);
    }

    private void rotateLeft(float value) {
        rotation += value * rotationSpeed;

        if (rotation > FastMath.TWO_PI) {
            rotation -= FastMath.TWO_PI;
        }

        if (rotation < 0) {
            rotation += FastMath.TWO_PI;
        }
        updateCamera();
    }

    private void rotateRight(float value) {
        rotateLeft(-value);
    }

    private void tiltUp(float value) {
        tilt -= value;

        if (tilt < -FastMath.HALF_PI) {
            tilt = -FastMath.HALF_PI;
        }

        if (tilt > FastMath.HALF_PI) {
            tilt = FastMath.HALF_PI;
        }

        updateCamera();
    }

    private void tiltDown(float value) {
        tiltUp(-value);
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
                    tiltUp(value);
                    break;
                case TILT_DOWN:
                    tiltDown(value);
                    break;
            }
    }

    private void registerInput() {
        registerKey(TILT_UP, KeyInput.KEY_W);
        registerKey(TILT_DOWN, KeyInput.KEY_S);
        registerKey(ROTATE_LEFT, KeyInput.KEY_A);
        registerKey(ROTATE_RIGHT, KeyInput.KEY_D);
    }

    private void registerKey(String name, int key) {
        gameState.inputManager.addMapping(name, new KeyTrigger(key));
        gameState.inputManager.addListener(this, name);
    }

}
