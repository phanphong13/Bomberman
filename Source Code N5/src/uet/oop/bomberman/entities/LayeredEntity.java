package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.tile.destroyable.Destroyable;
import uet.oop.bomberman.graphics.Sprite;

import java.util.LinkedList;

/**
 * Chứa và quản lý nhiều Entity tại cùng một vị trí
 * Ví dụ: tại vị trí dấu Item, có 3 Entity [Grass, Item, Brick]
 */

public class LayeredEntity extends Entity {


    protected LinkedList<Entity> _entities = new LinkedList<>();

    public LayeredEntity(double x, double y, Entity ... entities) {
        this.x = x;
        this.y = y;

        for (int i = 0; i < entities.length; i++) {
            _entities.add(entities[i]);

            if(i >= 1) {
                if(entities[i] instanceof Destroyable)
                    ((Destroyable) entities[i]).addBelowImage(entities[i-1].getImage());
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        firstEntity().render(gc);
    }

    @Override
    public void update() {
        Entity first = firstEntity();
        if (first.isRemoved()) {
            _entities.removeLast();
        }
        firstEntity().update();
    }

    public void addBeforeTopEntity(Entity entity) {
        _entities.add(_entities.size() - 1, entity);
    }

    /**
     * Lấy Entity ở trên cùng.
     * @return
     */
    public Entity firstEntity() {
        return _entities.getLast();
    }

    @Override
    public boolean collide(Entity e) {
        return firstEntity().collide(e);
    }

}
