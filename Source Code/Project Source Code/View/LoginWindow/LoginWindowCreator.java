package View.LoginWindow;

import Controller.AppController.AppController;
import Controller.IController;
import Model.AppModel.AppModel;
import Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLServerParameters;
import Model.AppModel.Database.DatabaseAccess.MySQLDatabaseAccess;
import Model.AppModel.Database.IDatabaseAccess;
import Model.AppModel.Logic.DTOClasses.Playlist;
import Model.AppModel.Logic.DTOClasses.PlaylistPreferences;
import Model.AppModel.Logic.DTOClasses.PlaylistSong;
import Model.AppModel.Logic.DTOClasses.Song;
import View.MainWindow.MainWindowController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoginWindowCreator extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception{


        LoginWindowCreator.primaryStage =primaryStage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("loginWindow.fxml"));

        Parent root = fxmlLoader.load();
        LoginWindowController loginWindowController = fxmlLoader.getController();
        loginWindowController.setController(new AppController(new AppModel()));
        primaryStage.setTitle("The Playlist Generator");
        Scene scene = new Scene(root, 600, 335);

        primaryStage.setScene(scene);

        primaryStage.setResizable(false);
        primaryStage.show();





    }

    public void launchWindow(Event event) throws Exception{


        Parent parent = null;
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(getClass().getResource("loginWindow.fxml"));
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        LoginWindowController loginWindowController = fxmlLoader.getController();
        loginWindowController.setController(new AppController(new AppModel()));

        Platform.runLater(() -> {
            appStage.setScene(scene);
            appStage.setWidth(600);
            appStage.setHeight(375);
            appStage.setResizable(false);
            appStage.show();
        });





    }


    public static void main(String[] args) {




        loadDatabaseServerParameters();

        launch(args);
    }

    private static void loadDatabaseServerParameters(){
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("DatabaseServerParameters.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {

            String line = br.readLine();
            SQLServerParameters.SQL_SERVER_IP=line;

            line = br.readLine();
            SQLServerParameters.SQL_SERVER_PORT=line;

            line = br.readLine();
            SQLServerParameters.SQL_SERVER_USERNAME=line;

            line = br.readLine();
            SQLServerParameters.SQL_SERVER_PASSWORD=line;

            line = br.readLine();
            SQLServerParameters.SQL_SERVER_SCHEMA_NAME=line;

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
