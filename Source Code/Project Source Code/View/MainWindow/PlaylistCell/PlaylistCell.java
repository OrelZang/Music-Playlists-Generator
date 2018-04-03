package View.MainWindow.PlaylistCell;

import Model.AppModel.Logic.DTOClasses.PlaylistSong;
import com.jfoenix.controls.JFXListCell;
import javafx.scene.control.ListCell;

public class PlaylistCell extends JFXListCell<PlaylistSong> {

    @Override
    public void updateItem(PlaylistSong playlistSong, boolean empty)
    {



        if (empty || playlistSong == null) {
            setText(null);
            setGraphic(null);
        } else {
            PlaylistCellContent playlistCellContent = new PlaylistCellContent();
            playlistCellContent.setInfo(playlistSong);
            setGraphic(playlistCellContent.getCell());
        }
    }
}
