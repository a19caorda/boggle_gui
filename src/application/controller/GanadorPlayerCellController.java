package application.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.boggle.Jugador;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * Esta clase es el controlador de la vista ganadoresPlayerCell crea la vista de la celda de los jugadores que ganan o que no ganan
 * Tiene los siguientes métodos:
 *  initialize()
 * @version 1.0
 */
public class GanadorPlayerCellController {

  private Jugador jugador;
  
  /**
   * Constructor de la clase
   * @param jugador un jugador
   */
  public GanadorPlayerCellController(Jugador jugador) {
    this.jugador = jugador;
  }
  
  @FXML
  private Circle imgPlayer;

  @FXML
  private Label playerName;

  @FXML
  private Label puntLabel;
  
  @FXML
  private VBox container;
  
  /**
   * Método init del controlador, se ejecuta al cargar la vista pone la imagen del jugador, su nombre y su puntuación en la celda
   */
  @FXML
  void initialize() {
    
    playerName.setText(jugador.getNombre());
    puntLabel.setText(String.format("Puntuación: %03d", jugador.getPuntuacion()));
    try {
      imgPlayer.setFill(new ImagePattern(new Image(new FileInputStream(jugador.getImageArchivo()))));
    } catch ( IllegalArgumentException | FileNotFoundException e) {
      imgPlayer.setFill(new ImagePattern(new Image( getClass().getResourceAsStream("../images/default.png") )));
    }
    
  }

}
