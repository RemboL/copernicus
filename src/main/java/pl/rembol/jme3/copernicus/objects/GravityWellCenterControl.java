package pl.rembol.jme3.copernicus.objects;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.stellarobjects.AstralObject;

class GravityWellCenterControl extends AbstractControl {

    private static final long UPDATE_DELAY = 1_000;
    private long lastCheck = System.currentTimeMillis();

    private AstralObject gravityWellCenter;

    private final GameState gameState;

    private final SpaceObject spaceObject;

    GravityWellCenterControl(GameState gameState, SpaceObject spaceObject) {
        this.gameState = gameState;
        this.spaceObject = spaceObject;
    }

    private void updateGravityWellCenter() {
        lastCheck = System.currentTimeMillis();
        gravityWellCenter = gameState.stellarSystem.getObjectOfStrongestGravitationalAcceleration(spaceObject.getPrecisePosition());
    }

    AstralObject getGravityWellCenter() {
        if (gravityWellCenter == null) {
            updateGravityWellCenter();
        }
        return gravityWellCenter;
    }

    @Override
    protected void controlUpdate(float tpf) {
        if (System.currentTimeMillis() - lastCheck > UPDATE_DELAY) {
            updateGravityWellCenter();
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }
}
