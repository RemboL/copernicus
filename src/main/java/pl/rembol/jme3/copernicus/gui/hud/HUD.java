package pl.rembol.jme3.copernicus.gui.hud;

import com.jme3.scene.Node;
import pl.rembol.jme3.copernicus.GameState;

public class HUD extends Node {

    private final GameState gameState;

    public HUD(GameState gameState) {
        this.gameState = gameState;

        AutoPilotIndicator autoPilotIndicator = new AutoPilotIndicator(gameState);
        attachChild(autoPilotIndicator);
        autoPilotIndicator.setLocalTranslation(gameState.camera.getWidth() / 2, gameState.camera.getHeight() / 2, 0);

        MouseFlightIndicator mouseFlightIndicator = new MouseFlightIndicator(gameState);
        attachChild(mouseFlightIndicator);
        mouseFlightIndicator.setLocalTranslation(gameState.camera.getWidth() - 150, 50, 0);

        gameState.guiNode.attachChild(this);
    }
}
