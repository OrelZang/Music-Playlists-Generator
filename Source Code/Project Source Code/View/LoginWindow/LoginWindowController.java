package View.LoginWindow;

import Controller.IController;
import Model.AppModel.Logic.DTOClasses.Icon;
import Model.AppModel.Logic.DTOClasses.User;
import Model.AppModel.Logic.Exceptions.UserNotFoundException;
import Model.AppModel.Logic.Exceptions.UsernameAlreadyExistsException;
import View.MainWindow.MainWindowCreator;
import View.SettingsWindow.SettingsWindowCreator;
import com.jfoenix.controls.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class LoginWindowController implements Initializable {
    @FXML
    private FontAwesomeIconView icon3;
    @FXML
    private FontAwesomeIconView icon4;
    @FXML
    private FontAwesomeIconView icon5;
    @FXML
    private FontAwesomeIconView icon6;
    @FXML
    private FontAwesomeIconView icon1;
    @FXML
    private FontAwesomeIconView icon2;
    @FXML
    private ImageView logoImage;

    @FXML
    private JFXButton settingsButton;
    @FXML
    private JFXButton backButton;
    @FXML
    private JFXButton registerNextButton;
    @FXML
    private JFXButton registerBackButton;

    @FXML
    private JFXTextField registerNameTextField;

    @FXML
    private JFXPasswordField registerPasswordTextField;

    @FXML
    private JFXTextField registerUsernameTextField;

    private IController controller;

    @FXML
    private JFXComboBox registerIconComboBox;


    @FXML
    private StackPane dialogStackPane;
    @FXML
    private JFXButton signInButton;
    @FXML
    private JFXButton registerButton;
    @FXML
    private JFXButton nextButton;
    @FXML
    private JFXPasswordField passwordTextField;
    @FXML
    private JFXTextField usernameTextField;

    private JFXDialog userNotFoundErrorMessage;
    private JFXDialog sqlErrorMessage;
    private JFXDialog usernameExistsErrorMessage;

    @FXML
    private JFXSpinner loadingSpinner;

    private List<Icon> iconsList;




    @FXML
    private void onClickEventRegisterNextButton(ActionEvent event) {
        Thread searchUserThread = new Thread(() -> register(event,registerUsernameTextField.getText(),registerPasswordTextField.getText(),registerNameTextField.getText(),((Label)registerIconComboBox.getValue()).getText()));
        searchUserThread.start();

    }

    private void register(Event event,String username, String password, String name, String iconName){
        int iconID = 0;
        String iconFileName = null;
        for (Icon icon : iconsList){
            if(icon.getIconName().equals(iconName)){
                iconID=icon.getIconID();
                iconFileName=icon.getIconFileName();
                break;

            }
        }

        registerBackButton.setVisible(false);
        registerNextButton.setVisible(false);

        registerUsernameTextField.setVisible(false);
        registerPasswordTextField.setVisible(false);
        registerNameTextField.setVisible(false);
        registerIconComboBox.setVisible(false);
        icon3.setVisible(false);
        icon4.setVisible(false);
        icon5.setVisible(false);
        icon6.setVisible(false);
        loadingSpinner.setVisible(true);
        try {

            User newRegisteredUser=controller.addNewUser(new User(username,password,name,new Icon(iconID,iconFileName,iconName)));
            MainWindowCreator mainWindowCreator = new MainWindowCreator();
            mainWindowCreator.launchWindow(event,newRegisteredUser);
        } catch (SQLException e) {
            Platform.runLater(() -> {
                registerBackButton.setVisible(true);
                registerNextButton.setVisible(true);

                registerUsernameTextField.setVisible(true);
                registerPasswordTextField.setVisible(true);
                registerNameTextField.setVisible(true);
                registerIconComboBox.setVisible(true);
                icon3.setVisible(true);
                icon4.setVisible(true);
                icon5.setVisible(true);
                icon6.setVisible(true);
                loadingSpinner.setVisible(false);
                dialogStackPane.setVisible(true);
                sqlErrorMessage.show();

            });
        } catch (UsernameAlreadyExistsException e) {
            Platform.runLater(() -> {
                registerBackButton.setVisible(true);
                registerNextButton.setVisible(true);

                registerUsernameTextField.setVisible(true);
                registerPasswordTextField.setVisible(true);
                registerNameTextField.setVisible(true);
                registerIconComboBox.setVisible(true);
                icon3.setVisible(true);
                icon4.setVisible(true);
                icon5.setVisible(true);
                icon6.setVisible(true);
                loadingSpinner.setVisible(false);
                dialogStackPane.setVisible(true);
                usernameExistsErrorMessage.show();
            });
        }



    }



    @FXML
    private void onClickEventNextButton(ActionEvent event) {
        Thread searchUserThread = new Thread(() -> signIn(new User(usernameTextField.getText(),passwordTextField.getText()),event));
        searchUserThread.start();

    }

    private void signIn(User userToSearch,Event event){
        try {
            nextButton.setVisible(false);
            backButton.setVisible(false);

            usernameTextField.setVisible(false);
            passwordTextField.setVisible(false);
            icon1.setVisible(false);
            icon2.setVisible(false);
            loadingSpinner.setVisible(true);

            User user = controller.searchForUserByUsernameAndPassword(userToSearch);

            MainWindowCreator mainWindowCreator = new MainWindowCreator();
            mainWindowCreator.launchWindow(event,user);

        } catch (SQLException e) {
            Platform.runLater(() -> {
                loadingSpinner.setVisible(false);
                usernameTextField.setVisible(true);
                passwordTextField.setVisible(true);
                nextButton.setVisible(true);
                backButton.setVisible(true);
                icon1.setVisible(true);
                icon2.setVisible(true);
                dialogStackPane.setVisible(true);
                sqlErrorMessage.show();

            });
        } catch (UserNotFoundException e) {
            Platform.runLater(() -> {
                loadingSpinner.setVisible(false);
                usernameTextField.setVisible(true);
                passwordTextField.setVisible(true);
                nextButton.setVisible(true);
                backButton.setVisible(true);
                icon1.setVisible(true);
                icon2.setVisible(true);
                dialogStackPane.setVisible(true);
                userNotFoundErrorMessage.show();

            });

        }
    }

    @FXML
    private void onClickEventSignInButton(ActionEvent event) {


        usernameTextField.setVisible(true);
        passwordTextField.setVisible(true);
        nextButton.setVisible(true);
        backButton.setVisible(true);
        signInButton.setVisible(false);
        registerButton.setVisible(false);
        usernameTextField.setText("");
        passwordTextField.setText("");
        nextButton.setDisable(true);
        settingsButton.setVisible(false);
        icon1.setVisible(true);
        icon2.setVisible(true);

    }

    @FXML
    private void onClickEventBackButton(ActionEvent event) {

        usernameTextField.setVisible(false);
        passwordTextField.setVisible(false);
        nextButton.setVisible(false);
        backButton.setVisible(false);
        signInButton.setVisible(true);
        registerButton.setVisible(true);
        settingsButton.setVisible(true);
        icon1.setVisible(false);
        icon2.setVisible(false);
    }

    @FXML
    private void onClickEventRegisterButton(ActionEvent event) {
        Thread prepareRegistrationFormThread = new Thread(this::prepareReistrationForm);
        prepareRegistrationFormThread.start();

    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        signInButton.setFocusTraversable(false);
        registerButton.setFocusTraversable(false);
        nextButton.setFocusTraversable(false);
        settingsButton.setFocusTraversable(false);

        JFXDialogLayout userErrorDialogLayout = new JFXDialogLayout();
        userErrorDialogLayout.setHeading(new Text("Error"));
        userErrorDialogLayout.setBody(new Text("Incorrect username or password\nPlease try again"));
        userNotFoundErrorMessage = new JFXDialog(dialogStackPane,userErrorDialogLayout,JFXDialog.DialogTransition.CENTER);
        JFXButton closeButton = new JFXButton("Close");
        closeButton.setOnAction(event -> {
            dialogStackPane.setVisible(false);
            userNotFoundErrorMessage.close();
        });
        closeButton.setButtonType(JFXButton.ButtonType.RAISED);

        userErrorDialogLayout.setActions(closeButton);

        closeButton.setStyle("-fx-background-color: #4285f4;-fx-text-fill: #fff");





        JFXDialogLayout sqlErrorDialogLayout = new JFXDialogLayout();
        sqlErrorDialogLayout.setHeading(new Text("Error"));
        sqlErrorDialogLayout.setBody(new Text("An error occurred with the database server\nPlease try again"));
        sqlErrorMessage = new JFXDialog(dialogStackPane,sqlErrorDialogLayout,JFXDialog.DialogTransition.CENTER);
        JFXButton closeButton2 = new JFXButton("Close");
        closeButton2.setOnAction(event -> {
            dialogStackPane.setVisible(false);
            sqlErrorMessage.close();
        });
        closeButton2.setButtonType(JFXButton.ButtonType.RAISED);

        sqlErrorDialogLayout.setActions(closeButton2);

        closeButton2.setStyle("-fx-background-color: #4285f4;-fx-text-fill: #fff");


        JFXDialogLayout usernameExistsErrorDialogLayout = new JFXDialogLayout();
        usernameExistsErrorDialogLayout.setHeading(new Text("Error"));
        usernameExistsErrorDialogLayout.setBody(new Text("The given username is already taken\nPlease try again with a different username"));
        usernameExistsErrorMessage = new JFXDialog(dialogStackPane,usernameExistsErrorDialogLayout,JFXDialog.DialogTransition.CENTER);
        JFXButton closeButton3 = new JFXButton("Close");
        closeButton3.setOnAction(event -> {
            dialogStackPane.setVisible(false);
            usernameExistsErrorMessage.close();
        });
        closeButton3.setButtonType(JFXButton.ButtonType.RAISED);

        usernameExistsErrorDialogLayout.setActions(closeButton3);

        closeButton3.setStyle("-fx-background-color: #4285f4;-fx-text-fill: #fff");
        ClassLoader classLoader = getClass().getClassLoader();
        logoImage.setImage(new Image(getClass().getResourceAsStream("logo.png")));
    }



    public void setController(IController controller) {
        this.controller = controller;
    }

    private void setNextButtonDisabledProperty(){
        if(usernameTextField.getText().length()>0&&passwordTextField.getText().length()>0){
            nextButton.setDisable(false);
        }else{
            nextButton.setDisable(true);
        }
    }

    private void setRegisterNextButtonDisabledProperty(){
        if(registerUsernameTextField.getText().length()>0&&registerPasswordTextField.getText().length()>0&&registerNameTextField.getText().length()>0
                &&!registerIconComboBox.getSelectionModel().isEmpty()){
            registerNextButton.setDisable(false);
        }else{
            registerNextButton.setDisable(true);
        }
    }

    @FXML
    private void onKeyReleasedEventPasswordTextField(KeyEvent keyEvent) {
        Platform.runLater(this::setNextButtonDisabledProperty);

    }

    @FXML
    private void onKeyReleasedEventUsernameTextField(KeyEvent keyEvent) {
        Platform.runLater(this::setNextButtonDisabledProperty);
    }

    @FXML
    private void onKeyReleasedEventRegisterUsernameTextField(KeyEvent keyEvent) {
        Platform.runLater(this::setRegisterNextButtonDisabledProperty);
    }

    @FXML
    private void onKeyReleasedEventRegisterPasswordTextField(KeyEvent keyEvent) {
        Platform.runLater(this::setRegisterNextButtonDisabledProperty);
    }

    @FXML
    private void onKeyReleasedEventRegisterNameTextField(KeyEvent keyEvent) {
        Platform.runLater(this::setRegisterNextButtonDisabledProperty);
    }


    private void prepareReistrationForm(){
        iconsList=null;
        try {
            loadingSpinner.setVisible(true);
            signInButton.setVisible(false);
            registerButton.setVisible(false);
            settingsButton.setVisible(false);
            iconsList = controller.getAllIcons();
        } catch (SQLException e) {
            Platform.runLater(() -> {
                loadingSpinner.setVisible(false);
                signInButton.setVisible(true);
                registerButton.setVisible(true);
                settingsButton.setVisible(true);

                dialogStackPane.setVisible(true);
                sqlErrorMessage.show();

            });

            return;
        }

        ObservableList iconComboboxOptions = FXCollections.observableArrayList();

        for (Icon icon : iconsList){
            Label currentLabel = new Label(icon.getIconName());
            Image currentImage = new Image(getClass().getResourceAsStream(icon.getIconFileName()));
            ImageView currentImageView = new ImageView();
            currentImageView.setImage(currentImage);
            currentImageView.setFitHeight(48);
            currentImageView.setFitWidth(46);
            currentLabel.setGraphic(currentImageView);
            iconComboboxOptions.add(currentLabel);
        }



        registerIconComboBox.setItems(iconComboboxOptions);
        registerIconComboBox.setVisibleRowCount(1);
        loadingSpinner.setVisible(false);
        signInButton.setVisible(false);
        registerButton.setVisible(false);
        settingsButton.setVisible(false);
        registerUsernameTextField.setVisible(true);
        registerPasswordTextField.setVisible(true);
        registerNameTextField.setVisible(true);
        registerIconComboBox.setVisible(true);
        icon3.setVisible(true);
        icon4.setVisible(true);
        icon5.setVisible(true);
        icon6.setVisible(true);
        registerBackButton.setVisible(true);
        registerNextButton.setVisible(true);


    }

    @FXML
    private void onClickEventRegisterBackButton(ActionEvent actionEvent) {
        registerUsernameTextField.setVisible(false);
        registerPasswordTextField.setVisible(false);
        registerNameTextField.setVisible(false);
        registerIconComboBox.setVisible(false);
        registerBackButton.setVisible(false);
        registerNextButton.setVisible(false);
        signInButton.setVisible(true);
        registerButton.setVisible(true);
        registerUsernameTextField.setText("");
        registerPasswordTextField.setText("");
        registerNameTextField.setText("");
        registerIconComboBox.setValue(null);
        registerNextButton.setDisable(false);
        settingsButton.setVisible(true);
        icon3.setVisible(false);
        icon4.setVisible(false);
        icon5.setVisible(false);
        icon6.setVisible(false);
    }

    @FXML
    private void onSelectedEventIconComboBox(ActionEvent actionEvent) {
        Platform.runLater(this::setRegisterNextButtonDisabledProperty);
    }

    public void onClickEventSettingsButton(ActionEvent actionEvent) {
        SettingsWindowCreator settingsWindowCreator = new SettingsWindowCreator();
        settingsWindowCreator.launchWindow();
    }
}
