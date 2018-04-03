package View.SettingsWindow;

import Controller.IController;
import Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLServerParameters;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLServerParameters.*;

public class SettingsWindowController implements Initializable {


    @FXML
    private JFXTextField serverIPTextField;
    @FXML
    private JFXTextField serverPortTextField;
    @FXML
    private JFXTextField serverUsernameTextField;
    @FXML
    private JFXPasswordField serverPasswordTextField;
    @FXML
    private JFXTextField serverSchemaNameTextField;
    @FXML
    private JFXButton saveButton;
    private IController controller;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        serverIPTextField.setText(SQL_SERVER_IP);
        serverPortTextField.setText(SQL_SERVER_PORT);
        serverUsernameTextField.setText(SQL_SERVER_USERNAME);
        serverPasswordTextField.setText(SQL_SERVER_PASSWORD);
        serverSchemaNameTextField.setText(SQL_SERVER_SCHEMA_NAME);
    }

    public void onClickEventSaveButton(MouseEvent mouseEvent) {
        SQL_SERVER_IP=serverIPTextField.getText();
        SQL_SERVER_PORT=serverPortTextField.getText();
        SQL_SERVER_USERNAME=serverUsernameTextField.getText();
        SQL_SERVER_PASSWORD=serverPasswordTextField.getText();
        SQLServerParameters.SQL_SERVER_SCHEMA_NAME=serverSchemaNameTextField.getText();
        controller.closeAllDBConnections();
        Writer writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("DatabaseServerParameters.txt"), "utf-8"));
            writer.write(SQL_SERVER_IP+"\n");
            writer.write(SQL_SERVER_PORT+"\n");
            writer.write(SQL_SERVER_USERNAME+"\n");
            writer.write(SQL_SERVER_PASSWORD+"\n");
            writer.write(SQL_SERVER_SCHEMA_NAME+"\n");
        } catch (IOException ex) {
            // report
        } finally {
            try {writer.close();} catch (Exception ex) {/*ignore*/}
        }
        ((Node)(mouseEvent.getSource())).getScene().getWindow().hide();
    }

    public void setController(IController controller) {
        this.controller = controller;
    }
}
