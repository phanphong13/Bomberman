package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.enemyControls.High;
import uet.oop.bomberman.entities.character.enemy.enemyControls.Low;
import uet.oop.bomberman.entities.character.enemy.enemyControls.Medium;
import uet.oop.bomberman.graphics.ImageLoad;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadFile.FileLevelLoader;

public class Ovapi extends Enemy {
    public Ovapi(double x, double y, Image image) {
        super(x, y, Sprite.ovapi_dead, 0.5,2000);
        this.image = Sprite.ovapi_left1;

        controls = new Medium((Bomber) FileLevelLoader.getBomber(), this);
        _direction = controls.enemyMove();
    }

    @Override
    protected void chooseImage() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.ovapi_right1, Sprite.ovapi_right2, Sprite.ovapi_right3, animate, 60);
                }
                break;
            case 2:
            case 3:
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.ovapi_left1, Sprite.ovapi_left2, Sprite.ovapi_left3, animate, 60);
                }
                break;
        }
    }
}
