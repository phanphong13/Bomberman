package uet.oop.bomberman.entities.tile.item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.tile.Tile;
import uet.oop.bomberman.graphics.Sprite;

public abstract class Item extends Tile {

    protected int duration = -1;
    protected boolean collected = false;

    public Item(double x, double y, Image image) {
        super(x, y, image);
    }

    public abstract void whenCollected();

    public void timeUsing() {
        if (duration > 0) {
            duration --;
        }
        if (duration == 0) {
            collected = false;
        }
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isCollected() {
        return collected;
    }

    public void setCollected(boolean collected) {
        this.collected = collected;
    }
}
