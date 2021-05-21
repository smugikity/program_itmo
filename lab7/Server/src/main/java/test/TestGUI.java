package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class TestGUI extends Application{
    public static ResourceBundle bundle;
    public static String currentLanguage;
    public static Scene scene;
    public static TestLoginController controller;
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
        bundle = ResourceBundle.getBundle("interface", new Locale(currentLanguage));
        loader.setResources(bundle);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        primaryStage.setResizable(false);
        primaryStage.setTitle("Client");
        scene=  new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
