package uet.oop.bomberman.entities.tile.destroyable;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.explosion.Directional;
import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.graphics.ImageLoad;
import uet.oop.bomberman.graphics.Sprite;

public class Destroyable extends Tile {

    private final int MAX_ANIMATE = 7500;
    private int animate = 0;
    protected boolean destroyed = false;
    protected int removeTime = 20;
    protected Image belowImage = new ImageLoad("res\\sprites\\grass.png").getImage();

    public Destroyable(double x, double y, Image image) {
        super(x, y, image);
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Directional) {
            destroy();
        }
        return false;
    }

    protected void destroy() {
        destroyed = true;
    }

    @Override
    public void update() {
        if(destroyed) {
            if (animate < MAX_ANIMATE)
                animate ++;
            else
                animate = 0;
            if (removeTime > 0) {
                removeTime --;
            } else {
                remove();
            }
        }
    }

    public void addBelowImage(Image image) {
        belowImage = image;
    }

    protected Image movingSprite(Image normal, Image x1, Image x2) {
        int calc = animate % 30;

        if(calc < 10) {
            return normal;
        }

        if(calc < 20) {
            return x1;
        }

        return x2;
    }
}
