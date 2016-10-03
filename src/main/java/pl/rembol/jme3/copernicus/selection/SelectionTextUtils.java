package pl.rembol.jme3.copernicus.selection;

import pl.rembol.jme3.copernicus.GameState;
import pl.rembol.jme3.copernicus.objects.SpaceObject;

import java.text.DecimalFormat;

public class SelectionTextUtils {
    
    public static String distance(GameState gameState, SpaceObject spaceObject) {
        return format(gameState.focusCamera.getCameraPosition().distance(spaceObject.getPrecisePosition()) * 1000);
    }
    
    public static String relativeVelocity(GameState gameState, SpaceObject spaceObject) {
        return format(gameState.focusCamera.getVelocity().distance(spaceObject.getVelocity()) * 1000);
    }

    private static String format(double number) {
        String scale;

        if (number < Math.pow(10, 3)) {
            scale = "";
        } else if (number < Math.pow(10, 6)) {
            scale = "k";
            number /= Math.pow(10, 3);
        } else if (number < Math.pow(10, 9)) {
            scale = "M";
            number /= Math.pow(10, 6);
        } else {
            scale = "G";
            number /= Math.pow(10, 9);
        }

        return new DecimalFormat("0.000").format(number) + scale;
    }

}
