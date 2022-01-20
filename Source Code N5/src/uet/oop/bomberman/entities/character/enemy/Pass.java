package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.enemyControls.High;
import uet.oop.bomberman.entities.character.enemy.enemyControls.Low;
import uet.oop.bomberman.graphics.ImageLoad;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadFile.FileLevelLoader;

public class Pass extends Enemy {

    public Pass(double x, double y, Image image) {
        super(x, y, Sprite.pass_dead, 1,4000);
        this.image = Sprite.pass_left1;

        controls = new High((Bomber) FileLevelLoader.getBomber(), this);
        _direction = controls.enemyMove();
    }

    @Override
    protected void chooseImage() {
        switch (_direction) {
            case 0:
            case 1:
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.pass_right1, Sprite.pass_right2, Sprite.pass_right3, animate, 60);
                }
                break;
            case 2:
            case 3:
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.pass_left1, Sprite.pass_left2, Sprite.pass_left3, animate, 60);
                }
                break;
        }
    }
}
