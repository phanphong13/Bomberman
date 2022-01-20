package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;

public interface Render {

    void update();

    void render(GraphicsContext graphicsContext);
}
