package application.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import application.boggle.Jugador;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class GanadorPlayerCellController {

  private boolean esGanador;
  private Jugador jugador;
  
  public GanadorPlayerCellController(Jugador jugador, boolean esGanador) {
    this.esGanador = esGanador;
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
