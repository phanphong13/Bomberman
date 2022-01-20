package uet.oop.bomberman.entities.tile;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.gui.GUI;
import uet.oop.bomberman.loadFile.FileLevelLoader;
import uet.oop.bomberman.sound.Sound;

public class Portal extends Entity {

    private boolean isOpen = false;

    public Portal(int x, int y, Image image) {
        this.x = x;
        this.y = y;
        this.image = image;
    }

    public void render(GraphicsContext gc) {
        gc.drawImage(image, x * 32, y * 32);
    }

    @Override
    public void update() {
    }

    @Override
    public boolean collide(Entity e) {
        if (e instanceof Bomber) {
            if (FileLevelLoader.emptyMobCheck()) {
                if ((int) ((e.getX() + 8) / 16) == this.x && (int) ((e.getY() + 8) / 16) == this.y) {
                    /*if (BombermanGame.level == 11) {
                        GUI.drawGameWin(BombermanGame.gc);
                        return true;
                    }*/
                    int tempLevel = BombermanGame.level;
                    BombermanGame.isStart = false;
                    if (BombermanGame.level % 5 == 0) {
                        int bonus = BombermanGame.level / 5;
                        if (bonus == 1 ) {
                            BombermanGame.level = 105;
                            FileLevelLoader.nextLevel(105);
                        } else if (bonus == 2) {
                            BombermanGame.level = 205;
                            FileLevelLoader.nextLevel(205);
                        }

                    } else {
                        BombermanGame.level += 1;
                        FileLevelLoader.nextLevel(BombermanGame.level);
                        BombermanGame.time += 10;
                    }
                    //BombermanGame.level += 1;
                    //FileLevelLoader.nextLevel(BombermanGame.level += 1);
                    BombermanGame.isStart = true;
                    BombermanGame.transferLevel = true;
                    return true;
                }
            } else {
                return true;
            }
        }
        return true;
    }

}
