package application.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.JsonObject;

import application.boggle.Jugador;
import application.boggle.Partida;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class IndividualController {

  Jugador player;

  IndividualController(Jugador player) {
    this.player = player;
  }

  @FXML
  private Label punt_max;

  @FXML
  private Label part_ganadas;

  @FXML
  private Circle playerImg;

  @FXML
  private ImageView back;

  @FXML
  private VBox list_partidas;

  @FXML
  private Label part_perdidas;

  @FXML
  private Label player_name;

  @FXML
  private Label part_jugadas;

  @FXML
  private Label punt_total;

  @FXML
  void back(MouseEvent event) {
    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/history.fxml"));

      ((Node) event.getSource()).getScene().setRoot(fxmlLoader.load());
    } catch (IOException e) {

    }
    ;
  }

  @FXML
  void initialize() {

    try {
      playerImg.setFill(new ImagePattern(new Image(new FileInputStream(player.getImageArchivo()))));
    } catch (IllegalArgumentException | FileNotFoundException e) {
      playerImg.setFill(new ImagePattern(new Image( getClass().getResourceAsStream("../images/default.png") )));
    }
    
    player_name.setText(player.getNombre());
    punt_max.setText(player.getPuntuacion_maxima() + "");
    part_ganadas.setText(player.getPartidas_ganadas() + "");
    part_perdidas.setText(player.getPartidas_perdidas() + "");
    part_jugadas.setText(player.getPartidas_jugadas() + "");
    punt_total.setText(player.getPuntuacion_acumulada() + "");

    player.getPartidas().forEach(element -> {
      try {
        JsonObject jObject = (JsonObject) element;

        String fecha = jObject.get("fecha").getAsString();
        Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy").parse(fecha);
        int p = jObject.get("puntuacion").getAsInt();

        list_partidas.getChildren().add(new Label(String
            .format("Partida hecha el día %td/%tm/%tY a las %tT, conseguiste %d punto/s.", date, date, date, date, p)));

      } catch (ParseException e) {
        e.printStackTrace();
      }
    });

  }

}