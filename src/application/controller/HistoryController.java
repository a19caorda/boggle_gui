package application.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

import application.boggle.Jugador;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * Esta clase es el controlador de la vista history crea los directorios necesarios si no existen, coge los json y crea un jugador por cada json que encuentra,
 * se aplica la vista playerCell a cada jugador
 * Tiene los siguientes métodos:
 *  back(MouseEvent event)
 *  initialize()
 * @version 1.0
 */
public class HistoryController {

  @FXML
  private GridPane playerGrid;

  /**
   * Sirve para volver a la vista main desde la vista de historiales
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
   * Método init del controlador, se ejecuta al cargar la vista crea los directorios necesarios si no existen, coge los json y crea un jugador por cada json que encuentra,
   * se aplica la vista playerCell a cada jugador
   */
  @FXML
  void initialize() {
    try {

      File dir = new File("boggleHistorial");
      if (!dir.exists())
        dir.mkdirs();
      File dir2 = new File("boggleImages");
      if (!dir2.exists())
        dir2.mkdirs();

      File[] files = dir.listFiles((dirl, name) -> name.toLowerCase().endsWith(".json"));
      for (int i = 0; i < files.length; i++) {

        Jugador jugador = new Gson().fromJson( new FileReader(files[i]), Jugador.class );

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/playerCell.fxml"));
        fxmlLoader.setController(new PlayerCellController(jugador));

        playerGrid.add(fxmlLoader.load(), i%3, i/3);

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}