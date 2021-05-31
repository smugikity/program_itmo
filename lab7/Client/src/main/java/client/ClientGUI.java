package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;


public class ClientGUI extends Application {
    public static String currentLanguage;
    public static String currentMode;
    public static Stage stage;
    public static String email="";

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
        //System.out.println("\u0412\u043E\u0439\u0442\u0438/\n\u0420\u0435\u0433\u0438\u0441\u0442\u0440\u0430\u0446\u0438\u044F");
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/login.fxml"));
        currentLanguage="en_EN";
        currentMode="Light mode";
        loader.setResources(ResourceBundle.getBundle("bundle", new Locale(ClientGUI.currentLanguage.split("_",2)[0],ClientGUI.currentLanguage.split("_",2)[1])));
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
