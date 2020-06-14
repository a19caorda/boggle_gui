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

/**
 * Esta clase es el controlador de la vista playerCell en la que si se hace click se carga la vista individual y carga el nombre y la imagen del jugador
 * Tiene los siguientes métodos:
 *  click(MouseEvent event)
 *  initialize()
 * @version 1.0
 */
public class PlayerCellController {

  Jugador player;

  /**
   * Constructor de la clase
   * @param player un jugador
   */
  PlayerCellController(Jugador player) {
    this.player = player;
  }

  @FXML
  private Label playerName;

  @FXML
  private Circle playerImg;

  /**
   * Carga la vista del jugador individual donde podemos ver sus estadisticas
   * @param event hacer click en la celda
   */
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
