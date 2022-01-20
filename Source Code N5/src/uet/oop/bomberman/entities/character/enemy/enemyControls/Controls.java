package uet.oop.bomberman.entities.character.enemy.enemyControls;

import java.util.Random;

public abstract class Controls {
    protected Random random = new Random();

    public abstract int enemyMove();
}
