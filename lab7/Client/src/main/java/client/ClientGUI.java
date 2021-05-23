package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Locale;
import java.util.ResourceBundle;


public class ClientGUI extends Application {
    public static String currentLanguage;
    public static ObjectInputStream input;
    public static ObjectOutputStream output;
    private FXMLLoader loader;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(InetAddress.getLocalHost(),6967);
        socket.setSoTimeout(5000);
        output = new ObjectOutputStream(socket.getOutputStream());
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String result="";
        String line = "";
        while(!line.contains("\0")) {
            result += (line = input.readLine())+"\n";
        }
        System.out.print(result);
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
    }
}
