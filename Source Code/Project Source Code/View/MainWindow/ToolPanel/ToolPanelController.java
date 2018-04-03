package View.MainWindow.ToolPanel;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ToolPanelController implements Initializable {

    @FXML
    private JFXButton generationMenuButton;
    @FXML
    private JFXButton playlistsMenuButton;
    @FXML
    private JFXButton statisticsMenuButton;
    @FXML
    private JFXButton settingsMenuButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public JFXButton getGenerationMenuButton() {
        return generationMenuButton;
    }

    public JFXButton getPlaylistsMenuButton() {
        return playlistsMenuButton;
    }

    public JFXButton getStatisticsMenuButton() {
        return statisticsMenuButton;
    }

    public JFXButton getSettingsMenuButton() {
        return settingsMenuButton;
    }
}
