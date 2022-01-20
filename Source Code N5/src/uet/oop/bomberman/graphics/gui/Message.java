package uet.oop.bomberman.graphics.gui;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.oop.bomberman.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class Message extends Entity {

    private String text;
    private int duration;
    private Color color;
    private int size;

    public Message(String txt, double x, double y, int duration, Color color, int size) {
        this.x = x;
        this.y = y;
        this.text = txt;
        this.duration = duration * 60;
        this.color = color;
        this.size = size;
    }

    @Override
    public void render(GraphicsContext gc) {

    }

    @Override
    public void update() {
    }

    @Override
    public boolean collide(Entity e) {
        return true;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }
}
