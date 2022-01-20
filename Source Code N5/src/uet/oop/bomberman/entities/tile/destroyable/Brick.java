package uet.oop.bomberman.entities.tile.destroyable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.explosion.Directional;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.*;
import uet.oop.bomberman.graphics.ImageLoad;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadFile.Coordinates;
import uet.oop.bomberman.loadFile.FileLevelLoader;

import java.util.Random;

public class Brick extends Destroyable {
    private Image[] imageList = {Sprite.brick, Sprite.brick, new ImageLoad("res\\sprites\\brick.png").getImage(), Sprite.brick2, Sprite.brick3, Sprite.brick4, Sprite.brick5, Sprite.brick6, Sprite.brick7};


    public Brick(double x, double y, int level) {
        super(x, y, null);
        if (level >= 10) {
            this.image = imageList[3];
        } else {
            this.image = imageList[level - 1];
        }
    }


    @Override
    public void update() {
        super.update();
    }

    @Override
    public void render(GraphicsContext gc) {
        int xx = Coordinates.tileToPixel(this.x);
        int yy = Coordinates.tileToPixel(this.y);
        if (destroyed) {
            image = movingSprite(new ImageLoad("res\\sprites\\brick_exploded.png").getImage(), new ImageLoad("res\\sprites\\brick_exploded1.png").getImage(), new ImageLoad("res\\sprites\\brick_exploded2.png").getImage());
            gc.drawImage(image, xx * 2, yy * 2);
        } else {
            gc.drawImage(image, xx * 2, yy * 2);
        }
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            if (Bomber.wallPass) {
                return true;
            }
        }
        if (e instanceof Directional) {
            destroy();
        }
        if (e instanceof Kondoria || e instanceof Ovapi || e instanceof Pontan) {
            return true;
        }
        return false;
    }

}
