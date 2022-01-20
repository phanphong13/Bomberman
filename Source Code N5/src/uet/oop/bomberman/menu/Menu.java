package uet.oop.bomberman.menu;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;


public class Menu {

    private boolean firstTime = true;
    @FXML
    private Pane instructPane;
    @FXML
    private Pane leaderBoardPane;
    @FXML
    private Pane aboutPane;
    @FXML
    private AnchorPane slider;
    @FXML
    private Label playLabel, instructionLabel, leaderboardLabel, aboutLabel;

    public void showInstruction(MouseEvent event) {
        if (firstTime) {
            slideRight(0,-220,slider);
            firstTime = false;
            changeSize();
            changeLocation();
        }
        slideRight(500,0,instructPane);
        instructPane.setVisible(true);
        leaderBoardPane.setVisible(false);
        aboutPane.setVisible(false);
    }

    public void showLeaderBoard(MouseEvent event) {
        if (firstTime) {
            slideRight(450,-220,slider);
            firstTime = false;
            changeSize();
            changeLocation();
        }
        instructPane.setVisible(false);
        slideUp(624,0,leaderBoardPane);
        leaderBoardPane.setVisible(true);
        aboutPane.setVisible(false);
    }

    public void showAbout(MouseEvent event) {
        if (firstTime) {
            slideRight(450,-220,slider);
            firstTime = false;
            changeLocation();
            changeSize();
            changeLocation();
        }
        instructPane.setVisible(false);
        leaderBoardPane.setVisible(false);
        slideUp(-624,0, aboutPane);
        aboutPane.setVisible(true);
    }

    public void slideRight(int x, int y, Pane pane) {
        pane.setTranslateX(x);
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(pane);

        slide.setToX(y);
        slide.play();

        pane.setTranslateX(x);
    }

    public void slideUp(int x, int y, Pane pane) {
        pane.setTranslateY(x);
        TranslateTransition slide = new TranslateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(pane);

        slide.setToY(y);
        slide.play();

        pane.setTranslateY(x);
    }
    public void changeSize() {
        playLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold");
        instructionLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold");
        leaderboardLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold");
        aboutLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold");
    }

    public void changeLocation() {
        playLabel.setLayoutX(40);
        playLabel.setLayoutY(110);
        instructionLabel.setLayoutX(40);
        instructionLabel.setLayoutY(150);
        aboutLabel.setLayoutX(40);
        aboutLabel.setLayoutY(230);
        leaderboardLabel.setLayoutX(40);
        leaderboardLabel.setLayoutY(190);
    }

    public int playGame(MouseEvent mouseEvent) {
        return 1;
    }
}
