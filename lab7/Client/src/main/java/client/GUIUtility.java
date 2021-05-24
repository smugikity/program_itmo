package client;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class GUIUtility {
    public static boolean checkExceptionLogin(String email, String password) {
        if (!isValidEmailAddress(email)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Dialog");
            alert.setHeaderText("Email error");
            alert.setContentText("Wrong format email");
            alert.showAndWait();
            return false;
        }
        return isValidPassword(password);
    }

    public static boolean isValidPassword(String password) {
        if (password.length()<6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Dialog");
            alert.setHeaderText("Length error");
            alert.setContentText("Password need to larger than 6 chars");
            alert.showAndWait();
            return false;
        }
        if (password.contains("-") || password.contains(",") || password.contains("/") || password.contains(" ")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Dialog");
            alert.setHeaderText("Character error");
            alert.setContentText("Password can't contain character \"-\", \",\", \"/\", \" \".");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    public static void throwException(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(e.getClass().getName());
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    /**
     *
     * @param caller
     * @param resource
     * @param axis positive for X, negative for Y
     * @param miliStep
     */
    public static void switchAnimation(Pane caller, String resource, Integer axis, Integer miliStep, Interpolator interpolator) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientGUI.class.getResource("/"+resource));
        loader.setResources(ResourceBundle.getBundle("locale", new Locale(ClientGUI.currentLanguage)));
        Parent root = loader.load();
        Scene scene = caller.getScene();

        if (axis>0) root.translateXProperty().set((miliStep>0?-1:1)*scene.getWidth());
        else root.translateYProperty().set((miliStep>0?-1:1)*scene.getHeight());
        StackPane stackPane = (StackPane) scene.getRoot();
        stackPane.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv;
        if (axis>0) kv = new KeyValue(root.translateXProperty(),0,interpolator);
        else kv = new KeyValue(root.translateYProperty(),0,interpolator);
        KeyFrame kf = new KeyFrame(Duration.millis(Math.abs(miliStep)), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event->{
            stackPane.getChildren().remove(caller);
        });
        timeline.play();
    }

    public static void login(String email) throws IOException {
        ClientGUI.email = email;
        ClientGUI.stage.close();
        ClientGUI.stage = new Stage();
        ClientGUI.stage.setTitle("Main window");
        ClientGUI.stage.setResizable(false);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientGUI.class.getResource("/main.fxml"));
        loader.setResources(ResourceBundle.getBundle("locale", new Locale(ClientGUI.currentLanguage)));
        ClientGUI.stage.setScene(new Scene(loader.load()));
        ClientGUI.stage.show();
    }
}
