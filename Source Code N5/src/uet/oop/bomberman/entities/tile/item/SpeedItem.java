package uet.oop.bomberman.entities.tile.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class SpeedItem extends Item {
    public SpeedItem(double x, double y, Image image) {
        super(x, y, image);
    }

    @Override
    public void whenCollected() {
        collected = true;
        Bomber.bomberSpeed += 0.2;
    }

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Bomber) {
            ((Bomber) entity).addItem(this);
            remove();
            return true;
        }
        return false;
    }

}
