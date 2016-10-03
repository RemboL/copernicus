package pl.rembol.jme3.copernicus.gui;

import com.jme3.math.Vector2f;

public interface Clickable {
    void onClick();

    boolean isClicked(Vector2f cursorPosition);
}
