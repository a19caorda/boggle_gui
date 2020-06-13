package application.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import application.boggle.Jugador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class PlayerCellController {

  Jugador player;

  PlayerCellController(Jugador player) {
    this.player = player;
  }

  @FXML
  private Label playerName;

  @FXML
  private Circle playerImg;

  @FXML
  void click(MouseEvent event) {

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/individual.fxml"));
      fxmlLoader.setController(new IndividualController(player));

      ((Node) event.getSource()).getScene().setRoot(fxmlLoader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
    ;

  }

  @FXML
  void initialize() {
    try {
      playerName.setText(player.getNombre());
      playerImg.setFill(new ImagePattern(new Image(new FileInputStream(player.getImageArchivo()))));
    } catch (IllegalArgumentException | FileNotFoundException e) {
      playerImg.setFill(new ImagePattern(new Image( getClass().getResourceAsStream("../images/default.png") )));
    }
  }

}
