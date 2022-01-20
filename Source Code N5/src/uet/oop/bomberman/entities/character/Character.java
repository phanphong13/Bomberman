package uet.oop.bomberman.entities.character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Animated;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Character extends Animated {
    protected boolean _moving = false;
    protected int _direction = -1; // 0 : up, 1 : right, 2 : down, 3 : left
    protected boolean alive = true;
    public int timeAfter = 60;

    public Character(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public abstract void update();

    @Override
    public abstract void render(GraphicsContext gc);

    protected abstract void calculateMove();

    protected abstract void move(double xa, double ya);

    protected abstract boolean canMove(double x, double y);

    public abstract void kill();

    protected abstract void afterKill();
}
