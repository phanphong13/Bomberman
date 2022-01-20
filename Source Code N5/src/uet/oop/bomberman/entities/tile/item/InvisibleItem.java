package uet.oop.bomberman.entities.tile.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;

public class InvisibleItem extends Item {


    // Item giúp Bomber thoát khỏi sự truy đuổi của Eneny thông minh ( tìm đường dựa vào vị trí người chơi)
    // Tuy nhiên nếu va chạm thì vẫn hy sinh như thường :)
    public InvisibleItem(double x, double y, Image image) {
        super(x, y, image);
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

    @Override
    public void whenCollected() {
        collected = true;
        Bomber.invisible = true;
    }

}
