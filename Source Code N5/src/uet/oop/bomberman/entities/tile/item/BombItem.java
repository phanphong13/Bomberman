package uet.oop.bomberman.entities.tile.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class BombItem extends Item {
    public BombItem(double x, double y, Image image) {
        super(x, y, image);
    }

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Bomber) {
            ((Bomber) entity).addItem(this);
            remove();
            return true;
            //remove image
        }
        return false;
    }


    @Override
    public void whenCollected() {
        collected = true;
        Bomber.numberOfBombs += 1;
    }
}
