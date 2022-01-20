package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import uet.oop.bomberman.control.KeyBoard;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Render;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.graphics.gui.GUI;
import uet.oop.bomberman.graphics.gui.Message;
import uet.oop.bomberman.loadFile.FileLevelLoader;
import uet.oop.bomberman.loadFile.TopScore;
import uet.oop.bomberman.sound.Sound;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BombermanGame extends Application implements Render {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static int pausePressTime = 0;
    private int playCode = 0;
    public static List<Message> messages = new ArrayList<>();
    public static int time = 200;
    private long startTime = 0;

    public static boolean isStart = false;
    public static GraphicsContext gc;
    private Canvas canvas;
    public static Group root;
    private static Bomber bomber;
    public static int level = 1;
    protected int timeEff = 0;

    public static Sound themeSound = new Sound("loop");
    public static Sound soundPlay = new Sound("bomberman");
    public static Sound soundLevelComplete = new Sound("levelComplete");
    public static Sound soundGameOver = new Sound("gameOver");
    public static Sound soundGameWin = new Sound("gameWin");
    public static int timeShowTransferLevel = 150;
    public static boolean transferLevel = false;

    public static void main(String[] args) {
        TopScore.getList();
        FileLevelLoader.loadLevel(level);
        launch(args);
        TopScore.outScore();
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT + 1));
        gc = canvas.getGraphicsContext2D();
        stage.setResizable(false);
        stage.setTitle("Bomberman by UET boys");

        root = new Group();
        root.getChildren().add(canvas);

        drawStart(gc, root);

        Image icon = new Image("graphics/image/iconic.png");
        stage.getIcons().add(icon);
        stage.setScene(controls(root));
        stage.show();
        //drawGame(gc);
    }

    @Override
    public void update() {
        if (transferLevel) {
            soundLevelComplete.play();
        } else {
            soundLevelComplete.stop();
        }
        if (pausePressTime % 2 == 1) {
            return;
        }
        for (int i = 0; i < FileLevelLoader.stillObjects.size(); i++) {
            FileLevelLoader.stillObjects.get(i).update();
        }
        for (int i = 0; i < FileLevelLoader.mobs.size(); i++) {
            FileLevelLoader.mobs.get(i).update();
        }
        for (int i = 0; i < Bomber.bombs.size(); i++) {
            Bomber.bombs.get(i).update();
        }
        updateMess();
        for (int i = 0; i < FileLevelLoader.mobs.size(); i++) {
            Character character = FileLevelLoader.mobs.get(i);
            if (character.isRemoved()) {
                FileLevelLoader.mobs.remove(i);
            }
        }
    }

    private void updateMess() {
        int timeDuration = 0;
        for (int i = 0; i < messages.size(); i++) {
            Message m = messages.get(i);
            timeDuration = m.getDuration();
            if (timeDuration > 0) {
                m.setDuration(--timeDuration);
            } else {
                messages.remove(i);
            }
        }
    }

    @Override
    public void render(GraphicsContext gc) {
        if (pausePressTime % 2 == 1) {
            return;
        }
        root.getChildren().clear();
        root.getChildren().add(canvas);
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if (!transferLevel) {
            for (int i = 0; i < FileLevelLoader.stillObjects.size(); i++) {
                FileLevelLoader.stillObjects.get(i).render(gc);
            }
            for (int i = 0; i < FileLevelLoader.mobs.size(); i++) {
                FileLevelLoader.mobs.get(i).render(gc);
            }
            for (int i = 0; i < messages.size(); i++) {
                Message m = messages.get(i);
                gc.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, m.getSize()));
                gc.setFill(m.getColor());
                gc.stroke();
                gc.fillText(m.getText(), (int) m.getX(), (int) m.getY() + 8);
            }
            soundPlay.play();
            GUI.drawInfo(gc, level, Bomber.score, Bomber.getLives(), time);
            if (Bomber.bombs.size() > 0) {
                Bomber.bombs.forEach(g -> g.render(gc));
            }
        } else {
            soundPlay.stop();
            if (timeShowTransferLevel-- > 0) {
                GUI.drawLevel(gc, level);
            } else {
                transferLevel = false;
                timeShowTransferLevel = 150;
                BombermanGame.soundLevelComplete.stop();
            }
        }
    }

    public void paused() throws FileNotFoundException {
        GUI.drawPaused(gc, root);
        System.out.println("Paused");

    }

    public void drawGame(GraphicsContext gc) {
        startTime = System.currentTimeMillis();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render(gc);
                update();
                timing();
            }
        };
        timer.start();
    }

    public static void reloadLevel() {
        FileLevelLoader.loadLevel(level);
    }

    private Scene controls(Group root) {
        Scene scene = new Scene(root);
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP:
                    case W:
                        KeyBoard.up = true;
                        break;
                    case DOWN:
                    case S:
                        KeyBoard.down = true;
                        break;
                    case LEFT:
                    case A:
                        KeyBoard.left = true;
                        break;
                    case RIGHT:
                    case D:
                        KeyBoard.right = true;
                        break;
                    case B:
                        if (Bomber.bombs.size() > 0) {
                            KeyBoard.detonate = true;
                        }
                        break;
                    case SPACE:
                        if (Bomber.numberOfBombs > 0) {
                            KeyBoard.bomb = true;
                            //System.out.println(Bomber.numberOfBombs);
                        }
                        break;
                    case ESCAPE: {
                        pausePressTime++;
                        if (themeSound.isRunning()) {
                            themeSound.stop();
                        }
                        if (soundPlay.isRunning()) {
                            soundPlay.stop();
                        }
                        try {
                            paused();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    case SHIFT:
                        if (Bomber.canDetonate)
                            KeyBoard.denonateBomb = true;
                        break;
                }
            }
        });

        scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case W:
                    case UP:
                        KeyBoard.up = false;
                        break;
                    case S:
                    case DOWN:
                        KeyBoard.down = false;
                        break;
                    case A:
                    case LEFT:
                        KeyBoard.left = false;
                        break;
                    case D:
                    case RIGHT:
                        KeyBoard.right = false;
                        break;
                    case B:
                        KeyBoard.detonate = false;
                    case SPACE:
                        break;
                    case SHIFT:
                        KeyBoard.denonateBomb = false;
                        break;
                }
            }
        });
        return scene;
    }

    public void drawStart(GraphicsContext gc, Group group) throws FileNotFoundException {
        //Done
        //gc.setFill(Color.BLACK);
        //gc.fillRect(0,0,992, 448);
        Canvas backGround = new Canvas(992, 448);
        GraphicsContext graphicsContext = backGround.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.fillRect(0, 0, 992, 448);

        Pane pane = new Pane(backGround);

        Image label = new Image("graphics/image/logo.png");
        ImageView lableIV = new ImageView(label);
        lableIV.setX(178);
        lableIV.setY(0);
        pane.getChildren().add(lableIV);
        //gc.drawImage(label,178,0);

        Image start = new Image("graphics/image/start1.png");
        ImageView startIV = new ImageView(start);
        startIV.setX(420);
        startIV.setY(340);
        pane.getChildren().add(startIV);

        Image top = new Image("graphics/image/top.png");
        ImageView topIV = new ImageView(top);
        topIV.setX(447);
        topIV.setY(390);

        Image audio = new Image("graphics/image/audio.png");
        ImageView audioIV = new ImageView(audio);
        audioIV.setX(930);
        audioIV.setY(10);

        int numberOfMuteCheck = 1;
        pane.getChildren().add(audioIV);
        pane.getChildren().add(topIV);
        group.getChildren().add(pane);
        themeSound.play();
        startIV.setOnMouseClicked(click -> {
            //System.out.println("Start");
            themeSound.stop();
            //group.getChildren().clear();
            group.getChildren().remove(pane);
            //group.getChildren().remove(startIV);
            //group.getChildren().remove(topIV);
            //group.getChildren().remove(audioIV);

            transferLevel = true;
            /*
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

             */
            drawGame(gc);
            isStart = true;
        });

        topIV.setOnMouseClicked(click -> {
            //System.out.println("Top");
            group.getChildren().remove(pane);
            GUI.printTopScore(gc, group);
            //Pane back = new Pane(new Canvas(40, 20));
            Text back = new Text(20,20,"BACK");
            back.setFill(Color.WHITE);
            group.getChildren().add(back);
            back.setOnMouseClicked(click2 -> {
                group.getChildren().clear();
                group.getChildren().add(pane);
            });
        });

        audioIV.setOnMouseClicked(click -> {
            if (audioIV.getImage() == audio) {
                Image mute = new Image("graphics/image/mute.png");
                audioIV.setImage(mute);
                Sound.isMute = true;
                themeSound.stop();
            } else {
                audioIV.setImage(audio);
                Sound.isMute = false;
                themeSound.play();
            }
        });
    }

    public void timing() {
        if (pausePressTime % 2 == 1) {
            return;
        }
        long current = System.currentTimeMillis();
        if (current - startTime > 1000) {
            startTime = System.currentTimeMillis();
            time --;
            if (time < 0) {
                //die
                //bomber.kill();
            }
        }
    }

    public static void addMess(Message message) {
        messages.add(message);
    }


    public static void setTime(int time) {
        BombermanGame.time = time;
    }

}
