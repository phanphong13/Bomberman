package uet.oop.bomberman.graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageLoad {

    public static final int SIZE = 32;

    private Image image;
    private int x;
    private int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void render(GraphicsContext graphicsContext, int x, int y) {
        graphicsContext.drawImage(image,x,y);
    }

    public Image getImage() {
        return image;
    }

    public ImageLoad(String path) {
        try {
            image = new Image(new FileInputStream(path),32,32,false,true);
        } catch(FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

    public static Image movingSprite(Image normal, Image x1, Image x2, int animate, int time) {
        int calc = animate % time;
        int diff = time / 3;

        if(calc < diff) {
            return normal;
        }

        if(calc < diff * 2) {
            return x1;
        }

        return x2;
    }

    public static Image movingSprite(Image x1, Image x2, int animate, int time) {
        int diff = time / 2;
        return (animate % time > diff) ? x1 : x2;
    }

}
