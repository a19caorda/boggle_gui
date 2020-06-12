package application.controller;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

import application.boggle.Jugador;
import application.boggle.Partida;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Path;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class AddPlayersController {

  private int num_rondas;
  private int num_players;
  private Jugador[] players;
  private ArrayList<Jugador> loaded_players;

  public AddPlayersController(int num_rondas, int num_players) {
    this.num_players = num_players;
    this.num_rondas = num_rondas;
    players = new Jugador[num_players];
  }

  @FXML
  private Label titleLabel;

  @FXML
  private TextField namePlayer;

  @FXML
  private GridPane playerCells;

  private File imgPlayer;
  private int currentPlayer = -1;

  @FXML
  void imgPlayer(ActionEvent event) {

    FileChooser fChooser = new FileChooser();
    fChooser.getExtensionFilters().add(new ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg"));
    File file = fChooser.showOpenDialog(((Node) event.getSource()).getScene().getWindow());
    imgPlayer = file;

  }

  @FXML
  void next(ActionEvent event) {

    try {
      Jugador jugador = new Jugador(namePlayer.getText());
      players[currentPlayer] = jugador;

      Files.newBufferedWriter(jugador.getHistorialArchivo().toPath(), StandardOpenOption.CREATE).append(String.format(
          "{\"nombre\":\"%s\",\"puntuacion_maxima\":0,\"puntuacion_acumulada\":0,\"partidas_jugadas\":0,\"partidas_ganadas\":0,\"partidas_perdidas\":0,\"partidas\":[]}",
          jugador.getNombre())).close();
      if (imgPlayer != null)
        Files.copy(imgPlayer.toPath(), jugador.getImageArchivo().toPath(), StandardCopyOption.REPLACE_EXISTING);

      clearData();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

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
      Arrays.sort(files);
      
      loaded_players = new ArrayList<Jugador>();
      for (int i = 0; i < files.length; i++) {

        Jugador jugador = new Gson().fromJson(new FileReader(files[i]), Jugador.class);
        loaded_players.add(jugador);

      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    clearData();
  }

  void clearData() {
    try {
      currentPlayer++;
      if (currentPlayer < num_players) {
        playerCells.getChildren().removeIf((c) -> true);
        playerCells.getColumnConstraints().removeIf((c) -> true);

        for (int i = 0; i < loaded_players.size(); i++) {
          Jugador jugador = loaded_players.get(i);

          int new_i = i;

          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/playerCell.fxml"));
          fxmlLoader.setController(new PlayerSelectController(jugador));

          ColumnConstraints column = new ColumnConstraints(200);
          playerCells.getColumnConstraints().add(column);
          Node node;
          node = fxmlLoader.load();
          node.setOnMouseClicked((ev) -> {

            players[currentPlayer] = jugador;
            loaded_players.remove(new_i);

            clearData();
          });
          playerCells.add(node, i, 0);

        }

        namePlayer.clear();
        titleLabel.setText(String.format("%dº jugador/a", currentPlayer + 1));
        imgPlayer = null;
      } else {
        try {
          Partida partida = new Partida(num_rondas, players);
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/partida.fxml"));
          fxmlLoader.setController(new PartidaController(partida));

          titleLabel.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

}
