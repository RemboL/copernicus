package pl.rembol.jme3.copernicus;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.plugins.blender.math.Vector3d;
import com.jme3.system.AppSettings;
import pl.rembol.jme3.copernicus.config.ConfigLoader;
import pl.rembol.jme3.copernicus.ship.Ship;

public class GameRunningAppState extends AbstractAppState {

    private AppSettings settings;

    private GameState gameState;

    public GameRunningAppState(AppSettings settings) {
        this.settings = settings;
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

        new ConfigLoader(gameState).loadFromFile("solar_system.yml");

        Ship bumblebee = new Ship(gameState, "bumblebee/bumblebee.blend", .003f, 1f);
        gameState.focusCamera.setFocusAt(bumblebee);

        gameState.controlledShip = bumblebee;

        bumblebee.setPrecisePosition(new Vector3d(227_936_637d, 0d, 35_000d));
//        bumblebee.setPrecisePosition(new Vector3d(8f, -2d, 149_565_000d));
        bumblebee.accelerate(new Vector3d(33.14f, 0, 0));

        int n = 5;
        for (int i = 1; i <= n; ++i) {
            for (int j = 1; j <= n; ++j) {
                for (int k = 1; k <= n; ++k) {
                    Ship bumblebee2 = new Ship(gameState, "bumblebee/bumblebee.blend", .003f, .2f);
                    bumblebee2.setPrecisePosition(new Vector3d(.007d * i + 227_936_637d, .007d * k, .007d * j + 35_000d));
                    bumblebee2.accelerate(new Vector3d(33.14f, 0, 0));
                    gameState.selectionManager.select(bumblebee2);
                }
            }
        }

    }


}
