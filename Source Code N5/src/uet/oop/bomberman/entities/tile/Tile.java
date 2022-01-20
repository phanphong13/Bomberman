package uet.oop.bomberman.entities.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

/**
 * Entity cố định, không di chuyển
 */

public abstract class Tile extends Entity {
    public Tile(double x, double y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

    @Override
    public void render(GraphicsContext gc)  {
        gc.drawImage(image, x * 32, y * 32);
    }

    @Override
    public void update() {}
}
