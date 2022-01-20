package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.enemyControls.Low;
import uet.oop.bomberman.entities.character.enemy.enemyControls.Medium;
import uet.oop.bomberman.graphics.ImageLoad;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadFile.FileLevelLoader;

public class Oneal extends Enemy {

    public Oneal(double x, double y, Image image) {
        super(x, y, Sprite.oneal_dead, 1,200);
        this.image = Sprite.oneal_right1;
        controls = new Medium((Bomber) FileLevelLoader.getBomber(), this);
        _direction = controls.enemyMove();
    }

    @Override
    protected void chooseImage() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, animate, 60);
                }
                break;
            case 2:
            case 3:
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, animate, 60);
                }
                break;
        }
    }

}
