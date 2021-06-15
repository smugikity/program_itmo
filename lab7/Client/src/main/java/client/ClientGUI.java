package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;


public class ClientGUI extends Application {
    private static String currentLanguage;
    public static ResourceBundle resourceBundle;
    public static String currentMode;
    public static Stage stage;
    public static String email="";
    public static String getCurrentLanguage() {
        return currentLanguage;
    }
    public static void setCurrentLanguage(String currentLanguage) {
        ClientGUI.currentLanguage = currentLanguage;
        resourceBundle = ResourceBundle.getBundle("bundle", new Locale(currentLanguage.split("_",2)[0],currentLanguage.split("_",2)[1]));
    }

    private FXMLLoader loader;

    public static void main(String[] args) {
        System.out.println("Program requires 1 variable - 1 port to connect to server." +
                "\nBy default - port: 6767.");
        try {
            Connection connection = new Connection("localhost", (args.length >= 1) ? Integer.parseInt(args[0]) : 6767);
            Thread thread = new Thread(connection);
            thread.start();
        } catch (NumberFormatException e) {
            System.out.println("Wrong port format");
            System.exit(0);
        }
        Application.launch();

    }

    @Override
    public void init() {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/login.fxml"));
        setCurrentLanguage("en_US");
        currentMode="Light mode";
        loader.setResources(resourceBundle);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setTitle(resourceBundle.getString("login.title"));
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
        stage = primaryStage;
    }
}
