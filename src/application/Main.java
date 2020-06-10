package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
		  FXMLLoader fxmlLoader = new FXMLLoader( getClass().getResource("views/main.fxml") );
		  
			Scene scene = new Scene(fxmlLoader.load());
			scene.getStylesheets().add(getClass().getResource("./main.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
