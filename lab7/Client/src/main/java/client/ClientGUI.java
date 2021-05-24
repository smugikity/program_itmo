package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


public class ClientGUI extends Application {
    public static String currentLanguage;
    public static Stage stage;
    public static String email="";

    private FXMLLoader loader;

    public static void main(String[] args) {
        Connection connection = new Connection("localhost",6967);
        Application.launch();
    }

    @Override
    public void init() throws IOException {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/login.fxml"));
        currentLanguage="en";
        loader.setResources(ResourceBundle.getBundle("locale", new Locale(currentLanguage)));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setTitle("Client");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
        stage = primaryStage;
    }
}
