package uet.oop.bomberman.entities.character.enemy.enemyControls;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.character.enemy.enemyControls.aStar.AStar;
import uet.oop.bomberman.entities.character.enemy.enemyControls.aStar.Node;
import uet.oop.bomberman.loadFile.FileLevelLoader;

import java.util.List;

/**
 * Based on A* Algorithm
 */
public class High extends Controls {
    private Enemy enemy;
    private Entity bomber;
    private int tempDirec = -1;

    public High(Character bomber, Enemy enemy) {
        this.bomber = bomber;
        this.enemy = enemy;
    }


    @Override
    public int enemyMove() {
        if (BombermanGame.isStart) {
            if (!Bomber.invisible) {
                if (enemy.getX() % 16 == 0 || enemy.getY() % 16 == 0) {
                    //System.out.println("AStar !");
                    Node enemyNode = new Node((int) (enemy.getY() + 8) / 16, (int) (enemy.getX() + 8) / 16);
                    Node bomberNode = new Node((int) (bomber.getY() + 8) / 16, (int) (bomber.getX() + 8) / 16);

                    int row = 13;
                    int col = 31;

                    AStar aStar = new AStar(row, col, enemyNode, bomberNode, 1, 20);

                    int[][] wallsArray = new int[row * col][2];
                    int count = 0;

                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < col; j++) {
                            Entity entity = FileLevelLoader.getStillObj(i, j);
                            if (!entity.collide(this.enemy)) {
                                if (!entity.isRemoved()) {
                                    wallsArray[count][0] = i;
                                    wallsArray[count][1] = j;
                                    count++;
                                }
                            }
                        }
                    }

                    aStar.setBlocks(wallsArray);


                    List<Node> path = aStar.findPath();

                    if (path.size() <= 1) {
                        //System.out.println("Size = 0");
                        //int temp = random.nextInt(4);
                        //tempDirec = temp;
                        //return tempDirec;
                        return random.nextInt(4);
                        //return 0;
                    } else {

                        int xx = path.get(1).getCol();
                        int yy = path.get(1).getRow();

                        //System.out.println(xx + " " + yy);

                        if (enemy.getX() < xx * 16) {
                            //System.out.println("R");
                            tempDirec = 1;
                            return 1;
                        }
                        if (enemy.getX() > xx * 16) {
                            //System.out.println("L");
                            tempDirec = 3;
                            return 3;
                        }
                        if (enemy.getY() > yy * 16) {
                            tempDirec = 0;
                            //System.out.println("U");
                            return 0;
                        }
                        if (enemy.getY() < yy * 16) {
                            //System.out.println("D");
                            tempDirec = 2;
                            return 2;
                        }
                        if (enemy.getY() == yy * 16 || enemy.getX() == xx * 16) {
                            return -1;
                        }
                    }
                } else {
                    return tempDirec;
                }
            } else {
                return random.nextInt(4);
            }
        }
        //System.out.println("Random");
        return -1;
    }
}

