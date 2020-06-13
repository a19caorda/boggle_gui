package application.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import application.boggle.Partida;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class PartidaController {

  Partida partida;
  int current_player = 0;
  int current_round = 1;

  Set<String> validWordsSet = new HashSet<>();

  public PartidaController(Partida partida) {
    this.partida = partida;
  }

  Task<Void> timeTask = new Task<Void>() {

    @Override
    protected Void call() throws Exception {


      Platform.runLater( () -> setData() );
      while (current_round <= partida.getNumeroRondas()) {
        for (int i = 0; i < partida.getJugadoresLength(); i++) {
          int time = 15 * 1_000;
          while (time > 0) {
            Thread.sleep(1);
            time -= 1;
            updateMessage(millisToMinute(time));
          }
          partida.sumaPuntos(current_player, validWordsSet);
          validWordsSet.clear();
          Platform.runLater( () -> siguienteTurno() );
        }
        
        current_round++;
        Platform.runLater(() -> {
          roundLabel.setText("Ronda " + current_round);
        });
      }
      Platform.runLater(() -> {
        try {
          FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/ganadores.fxml"));
          fxmlLoader.setController(new GanadoresController(partida));

          dices.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
          e.printStackTrace();
        }
      });

      return null;

    }

  };
  Thread timeThread = new Thread(timeTask);

  @FXML
  private VBox insertedWords;

  @FXML
  private Label playerName;

  @FXML
  private VBox validWords;

  @FXML
  private TextField inputWords;

  @FXML
  private Label timeLabel;

  @FXML
  private Label roundLabel;
  @FXML
  private GridPane dices;

  @FXML
  void submitWord(KeyEvent event) {
    if (event.getCode() == KeyCode.ENTER) {

      String text = inputWords.getText();
      partida.addWordToPlayer(current_player, text);
      insertedWords.getChildren().add(new Label("- " + text));
      inputWords.clear();

      new Thread(() -> {
        Platform.runLater(() -> {
          if (!partida.comprueba(text).equals("")) {
            if (validWordsSet.add(text))
              validWords.getChildren().add(new Label("- " + text));
          }
        });
      }).start();

    }
  }

  @FXML
  void initialize() {

    timeLabel.textProperty().bind(timeTask.messageProperty());
    timeThread.start();

  }

  String millisToMinute(int millis) {
    return String.format("%02d:%02d", millis / 60_000, millis / 1000 % 60);
  }

  void siguienteTurno() {

    current_player = (current_player + 1) % partida.getJugadoresLength();

    setData();

    validWords.getChildren().removeIf((c) -> true);
    insertedWords.getChildren().removeIf((c) -> true);

  }

  private void setData() {

    playerName.setText(partida.getJugador(current_player).getNombre());
    char[][] k = partida.cubilete.tirarDados();
    dices.getChildren().removeIf((c) -> true);
    for (int row = 0; row < 5; row++) {
      for (int column = 0; column < 5; column++) {

        Label label = new Label(k[row][column] + "");
        label.getStyleClass().add("labelDice");
        dices.add(label, row, column);

      }
    }
  }

}
