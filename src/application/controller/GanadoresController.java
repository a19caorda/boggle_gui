package application.controller;

import java.io.IOException;
import java.util.ArrayList;

import application.boggle.Jugador;
import application.boggle.Partida;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

/**
 * Esta clase es el controlador de la vista ganadores en la que se dividen los jugadores en ganadores y en no ganadores y cada uno carga su vista y se coloca
 * en diferentes grids
 * Tiene los siguientes métodos:
 *  close(MouseEvent event)
 *  initialize()
 * @version 1.0
 */
public class GanadoresController {

  Partida partida;

  /**
   * Constructor de la clase
   * @param partida la partida jugada
   */
  public GanadoresController(Partida partida) {
    this.partida = partida;
  }

  @FXML
  private GridPane ganadoresGrid;

  @FXML
  private GridPane playersGrid;

  /**
   * Sirve para volver a la vista main desde la vista de ganadores
   * @param event click con el raton en la imagen
   */
  @FXML
  void close(MouseEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/main.fxml"));

      ((Node) event.getSource()).getScene().setRoot(fxmlLoader.load());
    } catch (IOException e) {
    }
  }

  /**
   * Método init del controlador, se ejecuta al cargar la vista separa a los ganadores de los no ganadores y
   * los carga dentro de la vista con sus respectivas vistas cada uno
   */
  @FXML
  void initialize() {
    try {

      ArrayList<Jugador> ganadores = partida.getGanadores();
      for (int i = 0; i < ganadores.size(); i++) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/ganadoresPlayerCell.fxml"));
        fxmlLoader.setController(new GanadorPlayerCellController(ganadores.get(i)));
        Node node = fxmlLoader.load();
        node.setStyle("-fx-background-color: #f7b42c; -fx-background-radius: 8px");
        if (i % 2 == 0 /* &&  i < partida.getJugadoresLength() - 1 */)
          ganadoresGrid.getRowConstraints().add(new RowConstraints(184));
        ganadoresGrid.add(node, i % 2, i / 2);
      }

      int j = 0;
      for (int i = 0; i < partida.getJugadoresLength(); i++) {
        Jugador jugador = partida.getJugador(i);

        if (!ganadores.contains(jugador)) {
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/ganadoresPlayerCell.fxml"));
          fxmlLoader.setController(new GanadorPlayerCellController(jugador));
          Node node = fxmlLoader.load();
          node.setStyle("-fx-background-color: #b783c8; -fx-background-radius: 8px");
          if (j % 2 == 0 /* && i < partida.getJugadoresLength() - 1 */)
            playersGrid.getRowConstraints().add(new RowConstraints(184));
          playersGrid.add(node, j % 2, j / 2);
          j += 1;
        }
      }

      partida.guardarArchivo();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
