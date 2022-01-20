package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Render;
import uet.oop.bomberman.graphics.Sprite;

/**
 * Lớp đại diện cho tất cả thực thể trong game (Bomber, Enemy, Wall, Brick,...)
 */

public abstract class Entity implements Render {

    protected double x; //Tọa độ X tính từ góc trái trên trong Canvas
    protected double y; //Tọa độ Y tính từ góc trái trên trong Canvas
    protected Image image;
    protected boolean removed = false;

    @Override
    public abstract void render(GraphicsContext gc);

    @Override
    public abstract void update();

    public abstract boolean collide(Entity e);

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public void remove() {
        this.removed = true;
    }

    public boolean isRemoved() {
        return removed;
    }

}
