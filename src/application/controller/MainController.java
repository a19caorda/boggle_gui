package application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

/**
 * Esta clase es el controlador de la vista main donde podemos empezar a jugar o ver los historiales
 * Tiene los siguientes métodos:
 *  play(ActionEvent event)
 *  history(ActionEvent event)
 * @version 1.0
 */
public class MainController {

  /**
   * Carga la vista (donde configurar las rondas y los jugadores) para poder empezar a jugar
   * @param event click con el raton en el boton
   */
  @FXML
  void play(ActionEvent event) {

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/infoPartida.fxml"));
    
      ((Node)event.getSource()).getScene().setRoot(fxmlLoader.load());
    } catch (IOException e) {

    };  
  }

  /**
   * Carga la vista donde podemos ver los historiales
   * @param event click con el raton en el boton
   */
  @FXML
  void history(ActionEvent event) {

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/history.fxml"));
    
      ((Node)event.getSource()).getScene().setRoot(fxmlLoader.load());
    } catch (IOException e) {

    };    
    
  }

}
