package pl.rembol.jme3.copernicus;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Node;
import com.jme3.system.AppSettings;
import pl.rembol.jme3.copernicus.camera.FocusCamera;
import pl.rembol.jme3.copernicus.gui.hud.HUD;
import pl.rembol.jme3.copernicus.gui.window.WindowManager;
import pl.rembol.jme3.copernicus.input.KeyInputManager;
import pl.rembol.jme3.copernicus.input.MouseInputManager;
import pl.rembol.jme3.copernicus.objects.GravityAppState;
import pl.rembol.jme3.copernicus.selection.SelectionManager;
import pl.rembol.jme3.copernicus.ship.Ship;
import pl.rembol.jme3.copernicus.stellarobjects.StellarSystem;
import pl.rembol.jme3.threads.ThreadManager;

public class GameState {

    public final SimpleApplication simpleApplication;
    public final AppSettings settings;
    public final BulletAppState bulletAppState;

    public final AssetManager assetManager;
    public final Node rootNode;
    public final Node guiNode;
    public final Camera camera;
    public final InputManager inputManager;

    public final FocusCamera focusCamera;

    public final ThreadManager threadManager = new ThreadManager();
    public final ShipControl shipControl;
    public final StellarSystem stellarSystem;

    public final GravityAppState gravityAppState;
    public final SelectionManager selectionManager;
    public final WindowManager windowManager;
    public final HUD hud;

    public Ship controlledShip;

    public GameState(SimpleApplication simpleApplication, AppSettings settings, BulletAppState bulletAppState) {
        this.simpleApplication = simpleApplication;
        this.settings = settings;
        this.bulletAppState = bulletAppState;

        assetManager = simpleApplication.getAssetManager();
        rootNode = simpleApplication.getRootNode();
        guiNode = simpleApplication.getGuiNode();
        camera = simpleApplication.getCamera();
        inputManager = simpleApplication.getInputManager();

        new KeyInputManager(this);
        new MouseInputManager(this);
        
        focusCamera = new FocusCamera(camera);
        shipControl = new ShipControl(this);
        stellarSystem = new StellarSystem();

        gravityAppState = new GravityAppState(this);
        simpleApplication.getStateManager().attach(gravityAppState);
        selectionManager = new SelectionManager(this);
        windowManager = new WindowManager(this);
        hud = new HUD(this);
    }

}
