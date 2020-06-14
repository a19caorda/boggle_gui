package application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;

/**
 * Esta clase es el controlador de la vista infoPartida se ponen el numero de jugadores y el numero de rondas que se van a jugar
 * Tiene los siguientes métodos:
 *  back(MouseEvent event)
 *  next(ActionEvent event)
 *  initialize()
 * @version 1.0
 */
public class InfoPartidaController {

    @FXML
    private Spinner<Integer> num_jugadores;

    @FXML
    private Spinner<Integer> num_rondas;

  /**
   * Sirve para volver a la vista main desde la vista donde podemos configurar la partida
   * @param event click con el raton en la imagen
   */
    @FXML
    void back(MouseEvent event) {
      try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/main.fxml"));

        ((Node) event.getSource()).getScene().setRoot(fxmlLoader.load());
      } catch (IOException e) {
      }
    }

  /**
   * Carga la siguiente vista para elegir a los jugadores y le pasa el numero de rondas y de jugadores
   * @param event action del boton
   */
    @FXML
    void next(ActionEvent event) {
      try {
        
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/addPlayers.fxml"));
        fxmlLoader.setController(new AddPlayersController(num_rondas.getValue(), num_jugadores.getValue()));

        ((Node) event.getSource()).getScene().setRoot(fxmlLoader.load());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    
  /**
   * Método init del controlador, se ejecuta al cargar la vista, asigna a num_jugadores y a num_rondas el valor del spinner
   */
    @FXML
    void initialize() {
      num_jugadores.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 2));
      num_rondas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 500, 1));
    }

}
