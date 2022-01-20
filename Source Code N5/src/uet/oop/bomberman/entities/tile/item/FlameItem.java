package uet.oop.bomberman.entities.tile.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class FlameItem extends Item {
    public FlameItem(double x, double y, Image image) {
        super(x, y, image);
    }

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Bomber) {
            //remove
            ((Bomber) entity).addItem(this);
            remove();
            return true;
        }
        return false;
    }

    @Override
    public void whenCollected() {
        collected = true;
        Bomber.bombRadius += 1;
    }
}
