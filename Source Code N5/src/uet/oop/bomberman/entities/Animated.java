package uet.oop.bomberman.entities;

/**
 * Entity có hiệu ứng hoạt hình
 */

public abstract class Animated extends Entity {
    protected int animate = 0;
    protected final int MAX_ANIMATE = 7500;

    protected void animate() {
        if (animate < MAX_ANIMATE) {
            animate ++;
        } else {
            animate = 0;
        }
    }
}
