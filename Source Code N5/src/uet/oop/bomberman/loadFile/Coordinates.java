package uet.oop.bomberman.loadFile;

public class Coordinates {

    public static final int TILES_SIZE = 16;

    public static int pixelToTile(double i) {
        return (int)(i / TILES_SIZE);
    }

    public static int tileToPixel(int i) {
        return i * TILES_SIZE;
    }

    public static int tileToPixel(double i) {
        return (int)(i * TILES_SIZE);
    }
}
