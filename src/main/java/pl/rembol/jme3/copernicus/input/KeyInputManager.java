package pl.rembol.jme3.copernicus.input;

import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import pl.rembol.jme3.copernicus.GameState;

public class KeyInputManager implements ActionListener, AnalogListener {
    public static final String A = "Key_A";
    public static final String D = "Key_D";
    public static final String E = "Key_E";
    public static final String Q = "Key_Q";
    public static final String S = "Key_S";
    public static final String W = "Key_W";
    public static final String SPACE = "Key_Space";
    public static final String TAB = "Key_Tab";
    private GameState gameState;

    public KeyInputManager(GameState gameState) {
        this.gameState = gameState;

        bindKey(A, KeyInput.KEY_A);
        bindKey(D, KeyInput.KEY_D);
        bindKey(E, KeyInput.KEY_E);
        bindKey(Q, KeyInput.KEY_Q);
        bindKey(S, KeyInput.KEY_S);
        bindKey(W, KeyInput.KEY_W);
        bindKey(SPACE, KeyInput.KEY_SPACE);
        bindKey(TAB, KeyInput.KEY_TAB);
    }

    @Override
    public void onAction(String name, boolean keyPressed, float tpf) {
        if (!gameState.windowManager.getTopWindow().isPresent()) {
            if (keyPressed) {
                gameState.shipControl.onAction(name, keyPressed, tpf);
            }
        }
    }

    @Override
    public void onAnalog(String name, float value, float tpf) {
        if (!gameState.windowManager.getTopWindow().isPresent()) {
            gameState.shipControl.onAnalog(name, value, tpf);
        }
    }
    
    private void bindKey(String command, int key) {
        gameState.inputManager.addMapping(command, new KeyTrigger(key));
        gameState.inputManager.addListener(this, command);
    }
}