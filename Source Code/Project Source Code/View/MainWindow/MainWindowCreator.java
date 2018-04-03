package View.MainWindow;

import Controller.AppController.AppController;
import Model.AppModel.AppModel;
import Model.AppModel.Logic.DTOClasses.User;
import View.LoginWindow.LoginWindowController;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainWindowCreator {


    public void launchWindow(Event event,User user) throws SQLException {
        Parent parent = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        scene.getStylesheets().add("mainWindow.css");
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        MainWindowController mainWindowController = fxmlLoader.getController();
        mainWindowController.setController(new AppController(new AppModel()));
        mainWindowController.setUser(user);
        mainWindowController.setGenresComboBox();
        mainWindowController.setTable();
        new Thread(mainWindowController::setPlaylistsView).start();
        mainWindowController.prepareUserInfoAppBar();

        Platform.runLater(() -> {
            appStage.setScene(scene);
            appStage.setWidth(1000);
            appStage.setHeight(700);
            appStage.setResizable(false);
            appStage.show();
        });

    }


}
