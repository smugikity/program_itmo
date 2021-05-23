package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class ClientGUI extends Application {
    public static String currentLanguage;
    public static Stage stage;
    FXMLLoader loader;

    public static void main(String[] args) {
        Application.launch();
    }

    @Override
    public void init() {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/login.fxml"));
        currentLanguage="en";
        loader.setResources(ResourceBundle.getBundle("locale", new Locale(currentLanguage)));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setTitle("Client");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }
}
