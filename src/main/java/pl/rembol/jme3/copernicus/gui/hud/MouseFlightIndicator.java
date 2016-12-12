package pl.rembol.jme3.copernicus.gui.hud;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.control.AbstractControl;
import pl.rembol.jme3.copernicus.GameState;

public class MouseFlightIndicator extends Node {

    private final GameState gameState;

    private BitmapText indicator;
    
    private boolean mouseFlight = false;

    public MouseFlightIndicator(GameState gameState) {
        this.gameState = gameState;
        BitmapFont guiFont = gameState.assetManager.loadFont("Interface/Fonts/Default.fnt");

        indicator = new BitmapText(guiFont);
        indicator.setSize(guiFont.getCharSet().getRenderedSize());
        indicator.setLocalTranslation(-indicator.getLineWidth() / 2, -indicator.getLineHeight(), 0);
        updateIndicator();
        attachChild(indicator);

        addControl(new UpdateIndicatorStatusControl());
    }
    
    private void updateIndicator() {
        mouseFlight = gameState.shipControl.isMouseFlight();
        indicator.setText( mouseFlight ? "MOUSEFLIGHT" : "CURSOR");
    }

    private class UpdateIndicatorStatusControl extends AbstractControl {

        @Override
        protected void controlUpdate(float tpf) {
            if (gameState.shipControl.isMouseFlight() != mouseFlight) {
                updateIndicator();
            }
        }

        @Override
        protected void controlRender(RenderManager rm, ViewPort vp) {

        }
    }

}
