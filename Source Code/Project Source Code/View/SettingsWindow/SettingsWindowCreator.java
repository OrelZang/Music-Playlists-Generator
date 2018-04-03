package View.SettingsWindow;

import Controller.AppController.AppController;
import Model.AppModel.AppModel;
import View.LoginWindow.LoginWindowController;
import View.LoginWindow.LoginWindowCreator;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsWindowCreator {
    public void launchWindow()  {
        Parent parent = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("settingsWindow.fxml"));
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent,300,295);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(LoginWindowCreator.primaryStage);
        stage.setTitle("Database Server Settings");
        SettingsWindowController settingsWindowController = fxmlLoader.getController();
        settingsWindowController.setController(new AppController(new AppModel()));
        stage.setResizable(false);
        //Fill stage with content
        stage.show();



    }


}
