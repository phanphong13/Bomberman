package uet.oop.bomberman.entities.character.enemy.enemyControls;

import uet.oop.bomberman.control.KeyBoard;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class Medium extends Controls {
    protected Entity bomber;
    protected Enemy enemy;

    public Medium(Entity bomber, Enemy enemy) {
        this.bomber = bomber;
        this.enemy = enemy;
    }

    protected int colDirection() {
        if(bomber.getX() < enemy.getX())
            return 3;
        else if(bomber.getX() > enemy.getX())
            return 1;

        return -1;
    }

    protected int rowDirection() {
        if(bomber.getY() < enemy.getY())
            return 0;
        else if(bomber.getY() > enemy.getY())
            return 2;
        return -1;
    }

    @Override
    public int enemyMove() {
        if(this.bomber == null || Bomber.invisible || (!KeyBoard.up && !KeyBoard.down && !KeyBoard.right && !KeyBoard.left))
            return random.nextInt(4);

        int vertical = random.nextInt(2);

        if(vertical == 1) {
            int v = rowDirection();
            if(v != -1)
                return v;
            else
                return colDirection();

        } else {
            int h = colDirection();

            if(h != -1)
                return h;
            else
                return rowDirection();
        }
    }
}
