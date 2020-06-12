package application.controller;

import java.io.IOException;

import application.boggle.Partida;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;

public class InfoPartidaController {

    @FXML
    private Spinner<Integer> num_jugadores;

    @FXML
    private Spinner<Integer> num_rondas;

    @FXML
    void back(MouseEvent event) {
      try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/main.fxml"));

        ((Node) event.getSource()).getScene().setRoot(fxmlLoader.load());
      } catch (IOException e) {
      }
    }

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
    
    @FXML
    void initialize() {
      num_jugadores.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 2));
      num_rondas.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 500, 1));
    }

}
