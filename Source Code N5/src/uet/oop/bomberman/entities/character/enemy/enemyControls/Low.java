package uet.oop.bomberman.entities.character.enemy.enemyControls;

public class Low extends Controls{
    @Override
    public int enemyMove() {
        return random.nextInt(4);
    }
}
