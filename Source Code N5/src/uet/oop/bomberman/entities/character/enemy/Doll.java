package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.enemy.enemyControls.Low;
import uet.oop.bomberman.graphics.ImageLoad;
import uet.oop.bomberman.graphics.Sprite;

public class Doll extends Enemy {

    public Doll(double x, double y, Image image) {
        super(x, y, Sprite.doll_dead, 1,400);
        this.image = Sprite.doll_right1;
        controls = new Low();
        _direction = controls.enemyMove();
    }

    @Override
    protected void chooseImage() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.doll_right1, Sprite.doll_right2, Sprite.doll_right3, animate, 60);
                }
                break;
            case 2:
            case 3:
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.doll_left1, Sprite.doll_left2, Sprite.doll_left3, animate, 60);
                }
                break;
        }
    }
}
