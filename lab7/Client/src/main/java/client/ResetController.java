package client;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ResetController {
    @FXML
    private Pane pane;
    @FXML
    private TextField textField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button sendButton;
    @FXML
    private Button switchButton;

    @FXML
    protected void handleSwitchButtonAction() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClientGUI.class.getResource("/setting.fxml"));
        loader.setResources(ResourceBundle.getBundle("locale", new Locale(ClientGUI.currentLanguage)));
        Parent root = loader.load();
        Scene scene = switchButton.getScene();

        root.translateXProperty().set(scene.getWidth());
        StackPane stackPane = (StackPane) scene.getRoot();
        stackPane.getChildren().add(root);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(root.translateXProperty(),0, Interpolator.EASE_IN);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event->{
            stackPane.getChildren().remove(pane);
        });
        timeline.play();
    }

    @FXML
    protected void handleSendButtonAction() throws IOException {}
}
