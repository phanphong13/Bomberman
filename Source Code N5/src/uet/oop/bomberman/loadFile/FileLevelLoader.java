package uet.oop.bomberman.loadFile;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.control.KeyBoard;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.bomb.explosion.Explosion;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.entities.character.enemy.*;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Spawner;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.*;
import uet.oop.bomberman.graphics.ImageLoad;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileLevelLoader  {

    protected static int _width = 20;
    protected static int _height = 20;
    protected static int _level = 1;

    public static int getWidth() {
        return _width;
    }
    public static int getHeight() {
        return _height;
    }
    public static int getLevel() {
        return _level;
    }
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Character> mobs = new ArrayList<>();

    public static void loadLevel(int level) {
        String path;
        if (level == 11) {
            return;
        }
        if (level >= 100) {
            path = "res\\levels\\LevelBonus" + (int) (level / 100) + ".txt";
        } else {
            path = "res\\levels\\Level" + level + ".txt";
        }
        List<String> list = new ArrayList<>();
        try {
            FileReader fr = new FileReader(path);//doc tep luu map
            //FileReader fr = new FileReader("res\\levels\\Level" + level + ".txt");//doc tep luu map
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (!line.equals("")) {
                list.add(line);
                line = br.readLine();
            }
            br.close();
            fr.close();
        } catch (Exception e) {
            //e.printStackTrace();
            //System.out.println("End Game !");
        }
        int hardmode = 0;
        String[] arrays = list.get(0).trim().split(" ");
        _level = Integer.parseInt(arrays[0]);
        _height = Integer.parseInt(arrays[1]);
        _width = Integer.parseInt(arrays[2]);
        if (_level == 205) {
            hardmode = 2;
        } else if (_level == 105) {
            hardmode = 1;
        }

        for (int i = 0; i < _height; i++) {
            for (int j = 0; j < _width; j++) {
                char c = list.get(i + 1).charAt(j);
                Entity object;
                switch (c) {
                    case '#' :
                        object = new Wall(j, i, level);
                        break;
                    case '*' :
                        object = new LayeredEntity(j, i, new Grass(j, i, level),
                                new Brick(j, i, level));
                        break;
                    case 'x' :
                        object = new LayeredEntity(j, i, new Grass(j, i, level),
                                new Portal(j, i , new ImageLoad("res\\sprites\\portal.png").getImage()),
                                new Brick(j, i, level));
                        break;
                    case 'S' :
                        object = new Spawner(j, i, new ImageLoad("res\\sprites\\spawner.png").getImage(), hardmode);
                        break;
                    case 'p' :
                        object = new Grass(j, i, level);
                        mobs.add(new Bomber(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i) ));
                        break;
                    case '1' :
                        object = new Grass(j, i, level);
                        mobs.add(new Balloon(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i), Sprite.balloom_right1));
                        break;
                    case '2' :
                        object = new Grass(j, i, level);
                        mobs.add(new Oneal(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i), Sprite.oneal_right1));
                        break;
                    case '3' :
                        object = new Grass(j, i, level);
                        mobs.add(new Doll(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i), Sprite.doll_right1));
                        break;
                    case '4' :
                        object = new Grass(j, i, level);
                        mobs.add(new Minvo(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i), Sprite.minvo_right1));
                        break;
                    case '5' :
                        object = new Grass(j, i, level);
                        mobs.add(new Kondoria(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i), Sprite.kondoria_right1));
                        break;
                    case '6' :
                        object = new Grass(j, i, level);
                        mobs.add(new Ovapi(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i), Sprite.ovapi_right1));
                        break;
                    case '7' :
                        object = new Grass(j, i, level);
                        mobs.add(new Pass(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i), Sprite.pass_right1));
                        break;
                    case '8' :
                        object = new Grass(j, i, level);
                        mobs.add(new Pontan(Coordinates.tileToPixel(j), Coordinates.tileToPixel(i), Sprite.pontan_right1));
                        break;

                    case 'b' :
                        object = new LayeredEntity(j, i, new Grass(j, i, level),
                                new BombItem(j, i, Sprite.powerup_bombs),
                                new Brick(j, i, level));
                        break;
                    case 'd' :
                        object = new LayeredEntity(j, i, new Grass(j, i, level),
                                new DetonatorItem(j, i, Sprite.powerup_detonator),
                                new Brick(j, i, level));
                        break;
                    case 'w' :
                        object = new LayeredEntity(j, i, new Grass(j, i, level),
                                new WallpassItem(j, i, Sprite.powerup_wallpass),
                                new Brick(j, i, level));
                        break;
                    case 'v' :
                        object = new LayeredEntity(j, i, new Grass(j, i, level),
                                new BombpassItem(j, i, Sprite.powerup_bombpass),
                                new Brick(j, i, level));
                        break;
                    case 'n' :
                        object = new LayeredEntity(j, i, new Grass(j, i, level),
                                new FlamepassItem(j, i, Sprite.powerup_flamepass),
                                new Brick(j, i, level));
                        break;
                    case 'f' :
                        object = new LayeredEntity(j, i, new Grass(j, i, level),
                                new FlameItem(j, i, Sprite.powerup_flames),
                                new Brick(j, i, level));
                        break;
                    case 's' :
                        object = new LayeredEntity(j, i, new Grass(j, i, level),
                                new SpeedItem(j, i, Sprite.powerup_speed),
                                new Brick(j, i, level));
                        break;
                    case 'i' :
                        object = new LayeredEntity(j, i, new Grass(j, i, level),
                                new InvisibleItem(j, i, Sprite.invisible),
                                new Brick(j, i, level));
                        break;
                    default:
                        object = new Grass(j, i, level);
                }
                stillObjects.add(j + i * _width, object);
            }
        }
        list.clear();
    }

    public static Entity getBomber() {
        for (Entity e : mobs) {
            if (e instanceof Bomber) {
                return e;
            }
        }
        return null;
    }

    public static Entity getEntity(double x, double y, Character c) {
        if (getExplosionAt((int) x, (int) y) != null) {
            return getExplosionAt((int) x, (int) y);
        }
        if (Bomber.getBombAt(x, y) != null ) {
            return Bomber.getBombAt(x, y);
        }
        if (getMobAt(x, y, c) != null) {
            return getMobAt(x, y, c);
        }
        if (getBlockAt((int) x,(int) y) != null) {
            return getBlockAt((int) x,(int) y);
        }
        return null;
    }

    private static Explosion getExplosionAt(int x, int y) {
        Iterator<Bomb> temp = Bomber.bombs.iterator();
        Bomb bomb;
        while (temp.hasNext()) {
            bomb = temp.next();
            Explosion explosion = bomb.getExplosionAt(x, y);
            if (explosion != null) {
                return explosion;
            }
        }
        return null;
    }

    public static Character getMobAt(double x, double y, Character exclude) {
        Iterator<Character> temp = mobs.iterator();
        Character c;
        while (temp.hasNext()) {
            c = temp.next();
            if (c == exclude) {
                continue;
            }
            if ( ((int)(c.getX() + 8) / 16) == (int) x && ((int)((c.getY() + 8 )/ 16)) == (int) y) {
                return c;
            }
        }
        return null;
    }
    public static Character getMobAt(double x, double y) {
        Iterator<Character> temp = mobs.iterator();
        Character c;
        while (temp.hasNext()) {
            c = temp.next();
            if ((int)(c.getX() + 8) / 16 == (int) x && (int)(c.getY() + 8) / 16 == (int) y) {
                return c;
            }
        }
        return null;
    }

    public static Entity getBlockAt(int x, int y) {
        for (Entity entity : stillObjects) {
            if (entity.getX() == x
                    && entity.getY() == y) {
                return entity;
            }
        }
        return null;
    }

    public static Entity getStillObj(int i, int j) {
        return stillObjects.get(i * 31 + j);
    }

    public static void nextLevel(int level) {
        //BombermanGame.pausePressTime = 1;
        /*if (BombermanGame.level == 11) {
            GUI.drawGameWin(BombermanGame.gc);
        }*/
        stillObjects.clear();
        mobs.clear();
        Bomber.bombs.clear();
        BombermanGame.setTime(200 + (level - 1) * 20);
        loadLevel(level);
        KeyBoard.bomb = false;
        BombermanGame.messages.clear();
        //Bomber.bomberSpeed = 1;
    }

    public static void restartGame() {
        BombermanGame.level = 1;
        stillObjects.clear();
        mobs.clear();
        Bomber.bombs.clear();
        BombermanGame.setTime(200);
        Bomber.lives = 3;
        Bomber.score = 0;
        loadLevel(BombermanGame.level);
        BombermanGame.messages.clear();
    }

    public static boolean emptyMobCheck() {
        for (Character character : mobs) {
            if (character instanceof Bomber) {
                continue;
            }
            if (character instanceof Enemy) {
                Enemy enemy = (Enemy) character;
                if (!enemy.isRemoved())
                    return false;
            }
        }
        return true;
    }

    public static void addMob(Character character) {
        mobs.add(character);
    }
}

