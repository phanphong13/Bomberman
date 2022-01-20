package uet.oop.bomberman.entities.bomb.explosion;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.ImageLoad;
import uet.oop.bomberman.graphics.Sprite;

public class Explosion extends Entity {

    protected boolean end = false;
    protected Image image1, image2;

    public Explosion(double x, double y, int direction, boolean end) {
        this.x = x;
        this.y = y;
        this.end = end;
        switch (direction) {
            case 0:
                if (!end) {
                    image = Sprite.explosion_vertical2;
                } else {
                    image = Sprite.explosion_vertical_top_last2;
                }
                break;
            case 1:
                if (!end) {
                    image = Sprite.explosion_horizontal2;
                } else {
                    image = Sprite.explosion_horizontal_right_last2;
                }
                break;
            case 2:
                if (!end) {
                    image = Sprite.explosion_vertical2;
                } else {
                    image = Sprite.explosion_vertical_down_last2;
                }
                break;
            case 3:
                if (!end) {
                    image = Sprite.explosion_horizontal2;
                } else {
                    image = Sprite.explosion_horizontal_left_last2;
                }
                break;

        }
    }

    @Override
    public void render(GraphicsContext gc) {
        int xx = (int)x << 4;
        int yy = (int)y << 4;
        gc.drawImage(image, xx, yy);
    }

    @Override
    public void update() {
        System.out.println(x + " " + y);
    }

    @Override
    public boolean collide(Entity e) {

        if (e instanceof Character) {
            if (e instanceof Bomber) {
                if (Bomber.flamePass) {
                    return true;
                }
            }
            ((Character) e).kill();
        }
        return true;
    }

}
