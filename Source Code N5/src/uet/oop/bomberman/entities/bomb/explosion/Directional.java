package uet.oop.bomberman.entities.bomb.explosion;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.loadFile.FileLevelLoader;

public class Directional extends Entity {

    protected int direction;
    private int radius;
    protected int centerX;
    protected int centerY;
    protected Explosion[] explosions;

    public Directional(int x, int y, int direction, int radius) {
        this.centerX = x;
        this.centerY = y;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.radius = radius;
        explosions = new Explosion[calculateDistance()];
        createExplosion();
    }

    @Override
    public void render(GraphicsContext gc) {
        for (int i = 0; i < explosions.length; i++) {
            gc.drawImage(explosions[i].getImage(), explosions[i].getX() * 32, explosions[i].getY() * 32);
        }
    }

    @Override
    public void update() {

    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Character) {
            if (e instanceof Bomber) {
                if (Bomber.flamePass) {
                    return true;
                }
            }
            ((Character) e).kill();
        }
        return true;
    }

    public Explosion explosionAt(int x, int y) {
        for (int i = 0; i < explosions.length; i++) {
            if ((int) explosions[i].getX() == x
                    && (int) explosions[i].getY() == y) {
                return explosions[i];
            }
        }
        return null;
    }

    public int calculateDistance() {
        int radius = 0;
        int x = (int) this.x;
        int y = (int) this.y;
        while (radius < this.radius) {
            if (direction == 0)
                y--;
            if (direction == 1)
                x++;
            if (direction == 2)
                y++;
            if (direction == 3)
                x--;

            Entity entity = FileLevelLoader.getEntity(x, y, null);
            if (entity instanceof Character)
                ++radius;
            if (!entity.collide(this))
                break;
            ++radius;
        }
        return radius;
    }

    private void createExplosion() {
        boolean end = false;

        int x = (int) this.x;
        int y = (int) this.y;

        for (int i = 0; i < explosions.length; i++) {
            end = (i == explosions.length - 1);

            switch (direction) {
                case 0:
                    y--;
                    break;
                case 1:
                    x++;
                    break;
                case 2:
                    y++;
                    break;
                case 3:
                    x--;
                    break;
            }
            explosions[i] = new Explosion(x, y, direction, end);
        }
    }
}
