package Model.AppModel.Logic.PlaylistGenerators;

import Model.AppModel.Logic.DTOClasses.PlaylistSong;
import Model.AppModel.Logic.DTOClasses.Song;

import java.util.List;

public interface IPlaylistGenerator {
    List<PlaylistSong> generatePlaylist();
}
