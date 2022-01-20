package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.ImageLoad;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Wall extends Tile {
    private Image[] imageList = {Sprite.wall , Sprite.wall1, new ImageLoad("res\\sprites\\wall.png").getImage(),Sprite.wall2, Sprite.wall3, Sprite.wall4, Sprite.wall5, Sprite.wall6, Sprite.wall7};

    public Wall(double x, double y, int level) {
        super(x, y, null);
        if (level >= 10) {
            this.image = imageList[3];
        } else {
            this.image = imageList[level - 1];
        }
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

}
