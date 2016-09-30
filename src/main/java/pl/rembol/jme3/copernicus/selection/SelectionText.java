package pl.rembol.jme3.copernicus.selection;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import pl.rembol.jme3.copernicus.GameState;

import java.util.ArrayList;
import java.util.List;

class SelectionText extends Node {

    private List<BitmapText> texts = new ArrayList<>();

    private final GameState gameState;

    SelectionText(GameState gameState) {
        this.gameState = gameState;

        gameState.guiNode.attachChild(this);
    }

    void updateText(List<String> lines) {
        if (texts.size() < lines.size()) {
            for (int i = texts.size(); i < lines.size(); ++i) {
                addStatusLine();
            }
        }

        for (int i = 0; i < texts.size(); ++i) {
            if (i < lines.size()) {
                texts.get(i).setText(lines.get(i));
            } else {
                texts.get(i).setText("");
            }
        }

    }

    private void addStatusLine() {
        BitmapFont guiFont = gameState.assetManager.loadFont("Interface/Fonts/Console.fnt");

        BitmapText textLine = new BitmapText(guiFont);
        textLine.setSize(guiFont.getCharSet().getRenderedSize());
        attachChild(textLine);
        textLine.move(50, 50 + (1 - texts.size()) * textLine.getLineHeight(), 0);
        texts.add(textLine);
    }

}
