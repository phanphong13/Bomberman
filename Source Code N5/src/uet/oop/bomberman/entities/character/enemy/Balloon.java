package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.enemy.enemyControls.Low;
import uet.oop.bomberman.graphics.ImageLoad;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Enemy {

    public Balloon(double x, double y, Image image) {
        super(x, y, Sprite.balloom_dead, 0.5,100);
        this.image = Sprite.balloom_left1;
        controls = new Low();
        _direction = controls.enemyMove();
    }

    @Override
    protected void chooseImage() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, animate, 60);
                }
                break;
            case 2:
            case 3:
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, animate, 60);
                }
                break;
        }
    }
}
