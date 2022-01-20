package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.enemyControls.Medium;
import uet.oop.bomberman.graphics.ImageLoad;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadFile.FileLevelLoader;

public class Minvo extends Enemy {
    public Minvo(double x, double y, Image image) {
        super(x, y, Sprite.minvo_dead, 1.5,800);
        this.image = Sprite.minvo_right1;
        controls = new Medium((Bomber) FileLevelLoader.getBomber(), this);
        _direction = controls.enemyMove();
    }

    @Override
    protected void chooseImage() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2, Sprite.minvo_right3, animate, 60);
                }
                break;
            case 2:
            case 3:
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2, Sprite.minvo_left3, animate, 60);
                }
                break;
        }
    }
}
