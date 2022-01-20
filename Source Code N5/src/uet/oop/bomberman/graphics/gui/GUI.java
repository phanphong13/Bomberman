package uet.oop.bomberman.graphics.gui;

import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.loadFile.FileLevelLoader;
import uet.oop.bomberman.loadFile.TopScore;
import uet.oop.bomberman.sound.Sound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class GUI {

    public static void drawInfo(GraphicsContext gc, int level, int score, int lives, int time) {
        try {
            int h = 443;
            gc.setFill(Color.BLACK);
            gc.fillRect(0, 416, 992, 448);
            gc.setFill(Color.WHITE);
            //gc.setFont(Font.font("Arial", 30));
            gc.setFont(Font.loadFont(new FileInputStream(System.getProperty("user.dir") + "\\res\\fonts\\AGoblinAppears-o2aV.ttf"), 15));
            if (level == 105) {
                gc.fillText("LEVEL: BONUS 1 " , 20, h);
            } else if (level == 205) {
                gc.fillText("LEVEL: BONUS 2 " , 20, h);
            } else {
                gc.fillText("LEVEL: " + level, 20, h);
            }
            gc.fillText("SCORE: " + score, 280, h);
            gc.fillText("LIVES: " + lives, 870, h);
            gc.fillText("TIME: " + time, 600, h);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void printTopScore(GraphicsContext graphicsContext, Group group) {
        List<String> temp = TopScore.printList();
        Canvas backGround = new Canvas(992, 448);
        GraphicsContext gc = backGround.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 992, 448);

        Pane top = new Pane(backGround);
        Text tempTxt = new Text();
        Font font;
        try {
            font = Font.loadFont(new FileInputStream(System.getProperty("user.dir") + "\\res\\fonts\\AGoblinAppears-o2aV.ttf"), 30);

        } catch (FileNotFoundException fileNotFoundException) {
            //fileNotFoundException.printStackTrace();
            font = Font.font("Arial",30);
        }
        tempTxt.setText("TOP GAME SCORE: ");
        tempTxt.setFill(Color.GOLD);
        tempTxt.setFont(font);
        tempTxt.setX(280);
        tempTxt.setY(100);
        top.getChildren().add(tempTxt);
        for (int i = 0; i < temp.size(); i++) {
            tempTxt = new Text();
            tempTxt.setFont(font);
            if (i > 4) {
                tempTxt.setX(600);
                tempTxt.setY(190 + (i - 5) * 40);
            } else {
                tempTxt.setX(200);
                tempTxt.setY(190 + i * 40);
            }
            tempTxt.setText((i + 1) + ". " + temp.get(i));
            tempTxt.setFill(Color.WHITE);
            top.getChildren().add(tempTxt);
        }
        group.getChildren().add(top);
    }

    public static void drawPaused(GraphicsContext gc, Group group) throws FileNotFoundException {
        //Done!
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,992, 448);
        Image label = new Image("graphics/image/logo.png");
        gc.drawImage(label,178,0);

        Image audio = new Image("graphics/image/audio.png");
        Image mute = new Image("graphics/image/mute.png");
        ImageView audioImg = new ImageView();
        if (!Sound.isMute) {
            audioImg.setImage(audio);
        } else {
            audioImg.setImage(mute);
        }
        audioImg.setX(930);
        audioImg.setY(10);

        //int numberOfMuteCheck = 1;
        group.getChildren().add(audioImg);

        audioImg.setOnMouseClicked(click -> {
            //BombermanGame.main(null);
            if (audioImg.getImage() == audio) {
                audioImg.setImage(mute);
                Sound.isMute = true;
            } else {
                audioImg.setImage(audio);
                Sound.isMute = false;
            }
        });
    }

    public static void drawStart(GraphicsContext gc, Group group) throws FileNotFoundException {
        //Done
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0,992, 448);

        Image label = new Image("graphics/image/logo.png");
        gc.drawImage(label,178,0);

        Image start = new Image("graphics/image/start1.png");
        ImageView start1 = new ImageView(start);
        start1.setX(420);
        start1.setY(340);
        group.getChildren().add(start1);

        Image top = new Image("graphics/image/top.png");
        ImageView top1 = new ImageView(top);
        top1.setX(447);
        top1.setY(390);
        group.getChildren().add(top1);

        start1.setOnMouseClicked(click -> {
            System.out.println("Start");
        });

        top1.setOnMouseClicked(click -> {
            System.out.println("Top");
        });
    }

    public static void drawLevel(GraphicsContext gc, int level) {
        gc.clearRect(0,0,992,448);
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, 992, 448);
        //Text stage = new Text("STAGE " + level);
        try {
            gc.setFont(Font.loadFont(new FileInputStream(System.getProperty("user.dir") + "\\res\\fonts\\AGoblinAppears-o2aV.ttf"), 30));

        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        gc.setFill(Color.WHITE);
        if (level == 105) {
            gc.fillText("STAGE B0NUS 1", 200, 230);
        } else if (level == 205) {
            gc.fillText("STAGE B0NUS 2", 200, 230);
        } else {
            gc.fillText("STAGE " + level, 200, 230);
        }
        //stage.setX(200);
        //stage.setY(200);

        //group.getChildren().remove(stage);
    }
    public static void drawGameOver(GraphicsContext gc, Group group){

        // Image endGame = new Image(new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\com\\example\\image\\gameOver.png"));
        //ImageView gameOver = new ImageView(endGame);
        BombermanGame.soundPlay.stop();
        BombermanGame.soundGameOver.play();
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0, 992, 448);

        try {
            gc.setFont(Font.loadFont(new FileInputStream(System.getProperty("user.dir") + "\\res\\fonts\\AGoblinAppears-o2aV.ttf"), 30));

        } catch (FileNotFoundException fileNotFoundException) {
            //fileNotFoundException.printStackTrace();
            gc.setFont(Font.font("Arial",30));
        }

        gc.setFill(Color.WHITE);
        gc.fillText("GAME OVER !", 340, 200);
        gc.fillText("Your Score: " + Bomber.score, 310, 250);

        Image img = new Image("graphics/image/restart.png");
        ImageView restart = new ImageView(img);

        restart.setX(385);
        restart.setY(380);
        group.getChildren().add(restart);


        restart.setOnMouseClicked(click -> {
            FileLevelLoader.restartGame();
            BombermanGame.pausePressTime += 1;
        });

    }

    public static void drawGameWin(GraphicsContext gc, Group group) {
        BombermanGame.soundPlay.stop();
        BombermanGame.soundGameWin.play();
        gc.setFill(Color.BLACK);
        gc.fillRect(0,0, 992, 448);

        try {
            gc.setFont(Font.loadFont(new FileInputStream(System.getProperty("user.dir") + "\\res\\fonts\\AGoblinAppears-o2aV.ttf"), 30));

        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }

        gc.setFill(Color.WHITE);
        gc.fillText("YOU WIN !", 340, 200);
        gc.fillText("Your Score: " + Bomber.score, 310, 250);

        Image img = new Image("graphics/image/restart.png");
        ImageView restart = new ImageView(img);

        restart.setX(385);
        restart.setY(380);
        group.getChildren().add(restart);


        restart.setOnMouseClicked(click -> {
            FileLevelLoader.restartGame();
            BombermanGame.pausePressTime += 1;
        });
    }
}
