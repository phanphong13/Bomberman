package uet.oop.bomberman.entities.tile;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.enemy.*;
import uet.oop.bomberman.graphics.ImageLoad;
import uet.oop.bomberman.loadFile.Coordinates;
import uet.oop.bomberman.loadFile.FileLevelLoader;

import java.util.Random;

public class Spawner extends Tile {
    private Random random = new Random();
    private int mode; // 1 - easy, 2 - hard

    public Spawner(double x, double y, Image image, int mode) {
        super(x, y, image);
        this.mode = mode;
    }

    @Override
    public void update() {

        int bound = 0;
        if (this.mode == 1) {
            bound = 1500;
        } else if (this.mode == 2) {
            bound = 1000;
        }
        int rand = random.nextInt(1000);
        if (rand == 5) { // random number
            randomSpawnMob();
        }
    }

    private void randomSpawnMob() {
        int bound = 0;
        if (mode == 1) {
            bound = 4;
        } else if (mode == 2) {
            bound = 8;
        }
        int rand = random.nextInt(bound);
        int j = (int) this.x;
        int i = (int) this.y;
        if (rand == 1) {
            FileLevelLoader.addMob(new Balloon(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i), new ImageLoad("res\\sprites\\balloom_right1.png").getImage()));
        } else if (rand == 2) {
            FileLevelLoader.addMob(new Oneal(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i), new ImageLoad("res\\sprites\\oneal_right1.png").getImage()));
        } else if (rand == 3) {
            //FileLevelLoader.addMob(new Balloon(Coordinates.tileToPixel(this.x * 16), Coordinates.tileToPixel(this.y * 16), new ImageLoad("res\\sprites\\balloom_right1.png").getImage()));
        } else if (rand == 4) {
            //FileLevelLoader.addMob(new Balloon(Coordinates.tileToPixel(this.x * 16), Coordinates.tileToPixel(this.y * 16), new ImageLoad("res\\sprites\\balloom_right1.png").getImage()));
        } else if (rand == 5) {
            //FileLevelLoader.addMob(new Balloon(Coordinates.tileToPixel(this.x * 16), Coordinates.tileToPixel(this.y * 16), new ImageLoad("res\\sprites\\balloom_right1.png").getImage()));
        } else if (rand == 6) {
            FileLevelLoader.addMob(new Ovapi(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i), new ImageLoad("res\\sprites\\oneal_right1.png").getImage()));
        } else if (rand == 7) {
            FileLevelLoader.addMob(new Pass(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i), new ImageLoad("res\\sprites\\oneal_right1.png").getImage()));
        } else if (rand == 8) {
            FileLevelLoader.addMob(new Pontan(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i), new ImageLoad("res\\sprites\\oneal_right1.png").getImage()));
        }
    }

    @Override
    public boolean collide(Entity e) {
        return true; // luôn cho đi qua.
    }
}
