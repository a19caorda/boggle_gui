package application.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import application.boggle.Jugador;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * Esta clase es otro controlador de la vista playerCell que carga el nombre y la imagen del jugador
 * Tiene los siguientes métodos:
 *  initialize()
 * @version 1.0
 */
public class PlayerSelectController {

  Jugador player;

  PlayerSelectController(Jugador player) { this.player = player; }

  @FXML
  private Label playerName;

  @FXML
  private Circle playerImg;

  @FXML
  void click(MouseEvent event) {}

  /**
   * Método init del controlador, se ejecuta al cargar la vista, pone el nombre del jugador y la imagen asociada a ese jugador, si no tiene imagen se
   * selecciona una por defecto
   */
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
