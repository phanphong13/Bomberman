package uet.oop.bomberman.entities.tile.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;

public class FlamepassItem extends Item {
    public FlamepassItem(double x, double y, Image image) {
        super(x, y, image);
    }

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Bomber) {
            ((Bomber) entity).addItem(this);
            remove();
            return true;
        }
        return true;
    }

    @Override
    public void whenCollected() {
        collected = true;
        Bomber.flamePass = true;
    }
}
