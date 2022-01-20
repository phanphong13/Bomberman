package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.ImageLoad;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Grass extends Tile {
    private Image[] imageList = {Sprite.grass, Sprite.grass1, new ImageLoad("res\\sprites\\grass.png").getImage(), Sprite.grass2, Sprite.grass3, Sprite.grass4, Sprite.grass5, Sprite.grass6, Sprite.grass7};

    public Grass(double x, double y, int level) {
        super(x, y, null);
        if (level >= 10) {
            this.image = imageList[3];
        } else {
            this.image = imageList[level - 1];
        }
    }


    @Override
    public boolean collide(Entity e) {
        return true; // luôn cho đi qua.
    }
}
