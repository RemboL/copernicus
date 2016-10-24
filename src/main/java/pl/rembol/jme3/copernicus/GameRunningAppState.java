package pl.rembol.jme3.copernicus;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.light.AmbientLight;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.plugins.blender.math.Vector3d;
import com.jme3.system.AppSettings;
import pl.rembol.jme3.copernicus.config.ConfigLoader;
import pl.rembol.jme3.copernicus.missile.Missile;
import pl.rembol.jme3.copernicus.ship.Ship;

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

        new ConfigLoader(gameState).loadFromFile("solar_system.yml");

        Ship bumblebee = new Ship(gameState, "bumblebee/bumblebee.blend");
        gameState.focusCamera.setFocusAt(bumblebee);

        gameState.controlledShip = bumblebee;

        bumblebee.setPrecisePosition(new Vector3d(227_936_637d, 0d, 35_000d));
//        bumblebee.setPrecisePosition(new Vector3d(8f, -2d, 149_565_000d));
        bumblebee.accelerate(new Vector3d(33.14f, 0, 0));

//        Ship bumblebee2 = new Ship(gameState, "bumblebee/bumblebee.blend");
//        bumblebee2.setPrecisePosition(new Vector3d(8f, -2d, 149_565_000.02d));
//        bumblebee2.accelerate(new Vector3d(33.14f, 0, 0));
//        bumblebee2.addControl(new AbstractControl() {
//            float ttl = 5f;
//
//            @Override
//            protected void controlUpdate(float tpf) {
//                bumblebee2.yawRight(tpf / 10);
//                ttl -= tpf;
//                if (ttl < 0) {
//                    new Missile(gameState, bumblebee2);
//                    ttl = 1f;
//                }
//            }
//
//            @Override
//            protected void controlRender(RenderManager rm, ViewPort vp) {
//
//            }
//        });

        gameState.selectionManager.select(gameState.stellarSystem.getObjectForName("Mars"));
//        gameState.selectionManager.select(bumblebee2);
    }


}
