package uet.oop.bomberman.entities.character.enemy;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.explosion.Directional;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.enemyControls.Controls;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.graphics.ImageLoad;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.gui.Message;
import uet.oop.bomberman.loadFile.FileLevelLoader;

public abstract class Enemy extends Character {

    protected int scoreGive;
    protected double moveSpeed;
    protected Controls controls;

    protected final double MAX_STEPS;
    protected final double rest;
    protected double steps;

    protected int animation = 30;
    protected Image dieSprite;

    public Enemy(double x, double y, Image image, double speed, int score) {
        super(x, y);
        this.scoreGive = score;
        this.moveSpeed = speed;
        this.MAX_STEPS = 16 / moveSpeed;
        this.rest = (MAX_STEPS - (int) MAX_STEPS) / MAX_STEPS;
        this.steps = MAX_STEPS;
        dieSprite = image;
    }

    @Override
    public void update() {
        animate();
        if (!alive) {
            afterKill();
        } else {
            calculateMove();
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (alive) {
            chooseImage();
        } else {
            if (timeAfter > 0) {
                image = dieSprite;
                animate = 0;
            } else {
                image = ImageLoad.movingSprite(Sprite.mob_dead1, Sprite.mob_dead2, Sprite.mob_dead3, animate, 60);
            }
        }
        gc.drawImage(image, x * 2, y * 2);
    }

    @Override
    public void calculateMove() {
        int xa = 0, ya = 0;
        if (steps <= 0) {
            _direction = controls.enemyMove();
            steps = MAX_STEPS;
        }
        // 0 : up, 1 : right, 2 : down, 3 : left
        if (_direction == 0) ya--;
        if (_direction == 2) ya++;
        if (_direction == 3) xa--;
        if (_direction == 1) xa++;

        if (canMove(xa, ya)) {
            steps -= 1 + rest;
            move(xa * moveSpeed, ya * moveSpeed);
            _moving = true;
        } else {
            steps = 0;
            _moving = false;
        }
    }

    @Override
    public void move(double xa, double ya) {
        if (!alive)
            return;
        y += ya;
        x += xa;
    }

    @Override
    public boolean canMove(double x, double y) {
        double xr = this.x;
        double yr = this.y;
        if (_direction == 0) {
            yr += 15;
            xr += 8;
        }
        if (_direction == 1) {
            yr += 8;
            xr += 1;
        }
        if (_direction == 2) {
            yr += 1;
            xr += 8;
        }
        if (_direction == 3) {
            yr += 8;
            xr += 15;
        }

        int xx = (int) (xr / 16) + (int) x;
        int yy = (int) (yr / 16) + (int) y;

        Entity entity = FileLevelLoader.getEntity(xx, yy, this);

        return entity.collide(this);
    }

    @Override
    public void kill() {
        if (!alive) {
            return;
        }
        alive = false;

        Bomber.score += this.scoreGive;
        Message die = new Message("+ " + scoreGive, x * 2 + 16, y * 2 - 16, 2, Color.RED, 14);
        BombermanGame.addMess(die);
    }

    @Override
    protected void afterKill() {
        if (timeAfter > 0) {
            --timeAfter;
        } else {
            if (animation > 0) {
                --animation;
            } else {
                remove();
            }
        }
    }

    protected abstract void chooseImage();

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Directional) {
            this.kill();
            return false;
        }

        if (entity instanceof Bomber) {
            ((Bomber) entity).kill();
            return false;
        }
        if (entity instanceof Brick) {
            return false;
        }
        return true;
    }
}
