package pl.rembol.jme3.copernicus.gui.hud;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import pl.rembol.jme3.copernicus.GameState;

import static java.lang.Math.abs;
import static java.lang.Math.sin;

public class AutoPilotIndicator extends Node {

    private final GameState gameState;

    private BitmapText indicator;

    public AutoPilotIndicator(GameState gameState) {
        this.gameState = gameState;
        BitmapFont guiFont = gameState.assetManager.loadFont("Interface/Fonts/Default.fnt");

        indicator = new BitmapText(guiFont);
        indicator.setSize(guiFont.getCharSet().getRenderedSize());
        indicator.setText("AUTO");
        indicator.setLocalTranslation(-indicator.getLineWidth() / 2, -indicator.getLineHeight(), 0);
        attachChild(indicator);

        addControl(new BlinkControl());
        addControl(new UpdateIndicatorStatusControl());
    }

    private class UpdateIndicatorStatusControl extends AbstractControl {

        @Override
        protected void controlUpdate(float tpf) {
            if (gameState.controlledShip.isAutoPiloting()) {
                if (indicator.getParent() == null) {
                    attachChild(indicator);
                }
            } else {
                if (indicator.getParent() != null) {
                    detachChild(indicator);
                }
            }
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {

        }
    }

    private class BlinkControl extends AbstractControl {

        float phase = 0;

        @Override
        protected void controlUpdate(float tpf) {
            phase += tpf * 3;
            indicator.setAlpha((float) abs(sin(phase)));
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {

        }
    }

}
