package pl.rembol.jme3.copernicus;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;
import pl.rembol.jme3.copernicus.ship.Ship;
import pl.rembol.jme3.copernicus.skybox.SkyBox;
import pl.rembol.jme3.copernicus.stellarobjects.Planet;
import pl.rembol.jme3.copernicus.stellarobjects.Star;

public class GameRunningAppState extends AbstractAppState {

    int frame = 200;

    private AppSettings settings;

    private GameState gameState;

    public GameRunningAppState(AppSettings settings) {
        this.settings = settings;
    }

    @Override
    public void update(float tpf) {
        frame++;
    }

    @Override
    public void cleanup() {
        if (gameState != null) {
            gameState.threadManager.tearDown();
        }
    }

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        SimpleApplication simpleApp = (SimpleApplication) app;

        BulletAppState bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        // bulletAppState.setDebugEnabled(true);

        gameState = new GameState(simpleApp, settings, bulletAppState);

        AmbientLight ambient = new AmbientLight();
        ambient.setColor(ColorRGBA.White);
        gameState.rootNode.addLight(ambient);

        new SkyBox(gameState, "sky.jpg");

        Star sun = new Star(gameState, "sun.jpg", "Sun", 20000f);
        sun.setPrecisePosition(new Vector3f(0, 0f, -1_000_000f));

        Planet earth = new Planet(gameState, "earth.jpg", "Earth", 6371f);
        earth.setPrecisePosition(new Vector3f(0, 0f, 70000f));

        Ship bumblebee = new Ship(gameState, "bumblebee/bumblebee.blend");
        bumblebee.setPrecisePosition(new Vector3f(8f, -2f, 0f));

        gameState.focusCamera.setFocusAt(bumblebee);
        gameState.shipControl.control(bumblebee);

    }


}
