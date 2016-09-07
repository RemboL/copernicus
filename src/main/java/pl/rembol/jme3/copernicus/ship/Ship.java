package pl.rembol.jme3.copernicus.ship;

import com.jme3.scene.Node;
import pl.rembol.jme3.copernicus.GameState;

public class Ship extends Node {

    public Ship(GameState gameState, String modelName) {
        Node model =(Node) gameState.assetManager
                .loadModel(modelName);
        attachChild(model);
        gameState.rootNode.attachChild(this);

    }
}
