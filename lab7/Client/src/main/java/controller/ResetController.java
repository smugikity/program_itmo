package controller;

import client.ClientGUI;
import client.Connection;
import client.GUIUtility;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.io.IOException;

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
    protected void handleSendButtonAction() {
        try {
            if (GUIUtility.isValidEmailAddress(textField.getText())) {
                Connection.getInstance().writeLine("send "+textField.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reset Dialog");
                alert.setHeaderText("Returning message");
                //alert.setContentText(Connection.getInstance().read());
                alert.showAndWait();
            } else if (GUIUtility.isValidPassword(passwordField.getText())){
                Connection.getInstance().writeLine("reset "+textField.getText()+","+passwordField.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Reset Dialog");
                alert.setHeaderText("Returning message");
                //alert.setContentText(Connection.getInstance().read());
                alert.showAndWait();
            }
        } catch (Exception e) {
            GUIUtility.throwException(e);
        }
    }
    @FXML
    protected void handleSwitchButtonAction() throws IOException {
        GUIUtility.switchAnimation(pane,"setting.fxml",1,-300, Interpolator.EASE_IN, ClientGUI.currentMode);
    }
}
