package application.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

public class MainController {

  @FXML
  void play(ActionEvent event) {

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/history.fxml"));
    
      ((Node)event.getSource()).getScene().setRoot(fxmlLoader.load());
    } catch (IOException e) {

    };  
  }

  @FXML
  void history(ActionEvent event) {

    try {
      FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../views/history.fxml"));
    
      ((Node)event.getSource()).getScene().setRoot(fxmlLoader.load());
    } catch (IOException e) {

    };    
    
  }

}
