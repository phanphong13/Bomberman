package uet.oop.bomberman.entities.character;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.control.KeyBoard;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.explosion.Directional;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.graphics.ImageLoad;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.gui.GUI;
import uet.oop.bomberman.graphics.gui.Message;
import uet.oop.bomberman.loadFile.FileLevelLoader;
import uet.oop.bomberman.loadFile.TopScore;
import uet.oop.bomberman.sound.Sound;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bomber extends Character {

    public static int lives = 3;
    protected KeyBoard controls;

    public static List<Bomb> bombs = new ArrayList<>();
    public List<Item> items = new ArrayList<>();

    public static double bomberSpeed = 1;
    //public static int speedUpTime = 0;

    public static int numberOfBombs = 1;
    public static int bombRadius = 1;

    public static int score = 0;
    public static int timeSetBomb = -1;

    public static boolean canDetonate = false;
    public static boolean wallPass = false;
    public static boolean bombPass = false;
    public static boolean flamePass = false;
    public static boolean invisible = false;

    public Bomber(double x, double y) {
        super(x, y);
        image = Sprite.player_right;
    }


    @Override
    public void update() {
        checkBomb();
        if (BombermanGame.time < 0) {
            kill();
        }
        if (!alive) {
            afterKill();
            return;
        }
        if (timeSetBomb < -MAX_ANIMATE) {
            timeSetBomb = 0;
        } else {
            timeSetBomb--;
        }
        animate();
        calculateMove();
        checkPlaceBombs();
    }

    public void checkPlaceBombs() {
        if (!KeyBoard.bomb)
            return;
        if(timeSetBomb < 0 && numberOfBombs > 0) {
            int xx = ((int)(x + 8)) / 16;
            int yy = ((int)(y + 8)) / 16;
            Entity e = FileLevelLoader.getEntity(xx, yy, this);
            if ((e instanceof LayeredEntity)) {
                e = ((LayeredEntity) e).firstEntity();
                if (!(e instanceof Grass)) {
                    return;
                }
            }
            if (KeyBoard.denonateBomb) {
                placeBomb(xx, yy,true);
            } else {
                placeBomb(xx, yy, false);
            }
            numberOfBombs--;
            timeSetBomb = 30;
            KeyBoard.bomb = false;
        }

    }

    private void placeBomb(int x, int y, boolean detonate) {
        Bomb bomb = new Bomb(x, y, detonate);
        Sound.playSound("setBomb");
        bomb.isBomb = 1;
        bombs.add(bomb);
    }

    private void checkBomb() {
        Iterator<Bomb> bombIterator = bombs.iterator();
        Bomb bomb;
        while (bombIterator.hasNext()) {
            bomb = bombIterator.next();
            if (bomb.isRemoved()) {
                bombIterator.remove();
                numberOfBombs += 1;
            }
        }
    }

    @Override
    public boolean collide(Entity entity) {
        if (entity instanceof Directional) {
            if (!flamePass)
                this.kill();
            return false;
        }
        if (entity instanceof Enemy) {
            this.kill();
            return true;
        }
        return true;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (alive) {
            chooseSprite();
        } else {
            image = Sprite.player_dead1;
        }
        gc.drawImage(image, x * 2, y * 2);
    }

    @Override
    public void calculateMove() {
        int xa = 0, ya = 0;
        if(KeyBoard.up) ya--;
        if(KeyBoard.down) ya++;
        if(KeyBoard.left) xa--;
        if(KeyBoard.right) xa++;
        if(xa != 0 || ya != 0)  {
            move(xa * bomberSpeed, ya * bomberSpeed);
            _moving = true;
        } else {
            _moving = false;
        }
    }

    @Override
    public void kill() {
        if (!alive) {
            return;
        }
        Sound.playSound("playerDie");
        lives -= 1;
        alive = false;

        Message die = new Message("-1 LIVE", x * 2 + 16, y * 2 - 16, 2, Color.RED, 14);
        BombermanGame.addMess(die);

    }

    @Override
    protected void afterKill() {
        BombermanGame.setTime(201);
        if (timeAfter > 0) {
            --timeAfter;
        } else {
            if (bombs.size() == 0) {
                //timeAfter --;

                if (lives == 0) {
                    if (BombermanGame.level == 205) {
                        BombermanGame.pausePressTime = 1;
                        TopScore.addScore(Bomber.score);
                        GUI.drawGameWin(BombermanGame.gc, BombermanGame.root);
                    } else {
                        BombermanGame.pausePressTime = 1;
                        TopScore.addScore(Bomber.score);
                        GUI.drawGameOver(BombermanGame.gc, BombermanGame.root);
                    }
                } else {
                    clearBomber();
                    BombermanGame.isStart = false;
                    if (BombermanGame.level == 105) {
                        BombermanGame.level = 6;
                        BombermanGame.transferLevel = true;
                        BombermanGame.timeShowTransferLevel = 150;
                        GUI.drawLevel(BombermanGame.gc, BombermanGame.level);
                        FileLevelLoader.nextLevel(BombermanGame.level);
                    } else if (BombermanGame.level == 205) {
                        BombermanGame.pausePressTime = 1;
                        TopScore.addScore(Bomber.score);
                        GUI.drawGameWin(BombermanGame.gc, BombermanGame.root);
                    } else {
                        FileLevelLoader.nextLevel(BombermanGame.level);
                    }
                    BombermanGame.isStart = true;
                }
            }

        }
    }

    protected void clearBomber() {
        bomberSpeed = 1;
        //speedUpTime = 0;

        numberOfBombs = 1;
        bombRadius = 1;

        timeSetBomb = -1;

        canDetonate = false;
        wallPass = false;
        bombPass = false;
        flamePass = false;
        invisible = false;

        clearAllItems();
    }

    @Override
    protected boolean canMove(double x, double y) {
        for (int c = 0; c < 4; c++) {
            double xt = ((this.x + x) + c % 2 * 11) / 16;
            double yt = (this.y + y + c / 2 * 12 - 13) / 16 + 1;

            Entity entity = FileLevelLoader.getEntity(xt, yt, this);


            if (!entity.collide(this)) {
                return false;
            }

        }
        return true;
    }


    @Override
    protected void move(double xa, double ya) {
        // 0 : up, 1 : right, 2 : down, 3 : left
        if(xa > 0) _direction = 1;
        if(xa < 0) _direction = 3;
        if(ya > 0) _direction = 2;
        if(ya < 0) _direction = 0;
        if(canMove(0, ya)) {
            this.y += ya;
        }
        if(canMove(xa, 0)) {
            this.x += xa;
        }
    }

    public void addItem(Item item) {
        if (item.isRemoved()) {
            // nếu item đó đã được nhặt, không có gì xảy ra
        } else {
            Sound.playSound("eatingItem");
            items.add(item);
            item.whenCollected();
        }
    }

    public void clearItem() {
        items.removeIf(item -> !item.isCollected());
    }

    public void clearAllItems() {
        items.clear();
    }

    private void chooseSprite() {
        switch (_direction) {
            case 0:
                this.image = Sprite.player_up;
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.player_up_1, Sprite.player_up_2, animate, 20);
                }
                break;
            case 1:
                image = Sprite.player_right;
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
                }
                break;
            case 2:
                image = Sprite.player_down;
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.player_down_1, Sprite.player_down_2, animate, 20);
                }
                break;
            case 3:
                image = Sprite.player_left;
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.player_left_1, Sprite.player_left_2, animate, 20);
                }
                break;
            default:
                image = Sprite.player_right;
                if (_moving) {
                    image = ImageLoad.movingSprite(Sprite.player_right_1, Sprite.player_right_2, animate, 20);
                }
                break;
        }
    }

    public static Entity getBombAt(double x, double y) {
        for (Bomb bomb : bombs) {
            if (bomb.getX()  == (int) x
                    && bomb.getY() == (int) y) {
                return bomb;
            }
        }
        return null;
    }

    public static int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        Bomber.lives = lives;
    }
}
