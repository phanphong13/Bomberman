package uet.oop.bomberman.entities.bomb;

import javafx.scene.canvas.GraphicsContext;
import uet.oop.bomberman.control.KeyBoard;
import uet.oop.bomberman.entities.Animated;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.explosion.Directional;
import uet.oop.bomberman.entities.bomb.explosion.Explosion;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.ImageLoad;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.loadFile.FileLevelLoader;
import uet.oop.bomberman.sound.Sound;

public class Bomb extends Animated {
    protected double explodeTime = 120;
    public int timeAfter = 20;
    protected boolean denoteCheck = false;
    protected boolean passOver = true;
    protected Directional[] explosions = null;
    public int isBomb = 0; //0: empty bomb 1: had bomb 2: explosion

    public Bomb(double x, double y, boolean denoteCheck) {
        this.x = x;
        this.y = y;
        this.denoteCheck = denoteCheck;
        image = Sprite.bomb;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (removed) {
            return;
        } else if (isBomb == 2) {
            image = Sprite.bomb_exploded2;
            renderExplosion(gc);
        } else if (isBomb == 1) {
            image = ImageLoad.movingSprite(Sprite.bomb,Sprite.bomb_1,Sprite.bomb_2,animate,60);
        }

        gc.drawImage(image, x * 32, y * 32);
    }

    public void renderExplosion(GraphicsContext gc) {
        for (int i = 0; i < explosions.length; i++) {
            explosions[i].render(gc);
        }
    }

    public void updateExplosion() {
        for (int i = 0; i < explosions.length; i++) {
            explosions[i].update();
        }
    }

    public Explosion getExplosionAt(int x, int y) {
        if (isBomb != 2)
            return null;
        for (int i = 0; i < explosions.length; i++) {
            if (explosions[i] == null)
                return null;
            Explosion explosion = explosions[i].explosionAt(x, y);
            if (explosion != null)
                return explosion;
        }
        return null;
    }

    protected void explosion() {
        passOver = true;
        isBomb = 2;

        Character mob = FileLevelLoader.getMobAt(x, y);
        //System.out.println(x + " " + y);
        if (mob != null) {
            if (mob instanceof Bomber) {
                if (!Bomber.flamePass) {
                    mob.kill();
                }
            } else {
                mob.kill();
            }
        }

        explosions = new Directional[4];

        for (int i = 0; i < explosions.length; i++) {
            explosions[i] = new Directional((int) x, (int) y, i, Bomber.bombRadius);
        }
    }

    protected void explode() {
        explodeTime = 0;
    }

    @Override
    public void update() {

        if (denoteCheck) {
            denoteBomb();
        }
        if (explodeTime > 0) {
            if (!denoteCheck)
                explodeTime--;
        } else {
            if (isBomb == 1) {
                explosion();
                Sound.playSound("bomb_explosion");
            } else {
                isBomb = 2;
                updateExplosion();
            }
            if (timeAfter > 0) {
                timeAfter --;
            } else {
                isBomb = 0;
                remove();
            }
        }
        animate();
    }

    @Override
    public boolean collide(Entity e) {

        if (e instanceof Bomber) {
            if (Bomber.bombPass) {
                return true;
            } else {
                double deltaX = e.getX() - this.getX() * 16;
                double deltaY = e.getY() - this.getY() * 16;

                if (!(deltaX >= -10 && deltaX < 16 && deltaY >= -14 && deltaY <= 12)) {
                    passOver = false;
                }
                return passOver;
            }
        }
        if (e instanceof Directional) {
            explode();
            return true;
        }
        return false;
    }

    public void denoteBomb() {

        if (KeyBoard.detonate) {
            for (Bomb bomb : Bomber.bombs) {
                bomb.explode();
            }
        }
    }

}
