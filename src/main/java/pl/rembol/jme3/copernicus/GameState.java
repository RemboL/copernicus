package pl.rembol.jme3.copernicus;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.system.AppSettings;
import pl.rembol.jme3.copernicus.camera.FocusCamera;
import pl.rembol.jme3.copernicus.gui.hud.HUD;
import pl.rembol.jme3.copernicus.objects.GravityAppState;
import pl.rembol.jme3.copernicus.selection.SelectionManager;
import pl.rembol.jme3.copernicus.ship.Ship;
import pl.rembol.jme3.copernicus.stellarobjects.StellarSystem;
import pl.rembol.jme3.game.GenericGameState;
import pl.rembol.jme3.game.input.InputListener;

public class GameState extends GenericGameState {

    public final FocusCamera focusCamera;

    public final ShipControl shipControl;
    public final StellarSystem stellarSystem;

    public final GravityAppState gravityAppState;
    public final SelectionManager selectionManager;
    public final HUD hud;

    public Ship controlledShip;

    public GameState(SimpleApplication simpleApplication, AppSettings settings, BulletAppState bulletAppState) {
        super(simpleApplication, settings, bulletAppState);

        focusCamera = new FocusCamera(camera);
        shipControl = new ShipControl(this);
        stellarSystem = new StellarSystem();

        gravityAppState = new GravityAppState(this);
        simpleApplication.getStateManager().attach(gravityAppState);
        selectionManager = new SelectionManager(this);
        hud = new HUD(this);
    }

    protected InputListener createInputListener() {
        return new CopernicusInputListener(this);
    }

}
