package Model.AppModel;

import Model.AppModel.Logic.DTOClasses.*;
import Model.AppModel.Database.DatabaseAccess.MySQLDatabaseAccess;
import Model.AppModel.Database.IDatabaseAccess;
import Model.AppModel.Logic.Exceptions.UserNotFoundException;
import Model.AppModel.Logic.PlaylistGenerators.PreferencesBasedPlaylistGenerator;
import Model.AppModel.Logic.PlaylistGenerators.SongDistanceBasedPlaylistGenerator;
import Model.IModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import static Model.AppModel.Logic.Exceptions.ConstantExceptionStrings.USER_NOT_FOUND_EXCEPTION_STRING;

public class AppModel implements IModel{
    private IDatabaseAccess databaseAccess;

    public AppModel() {
        databaseAccess=new MySQLDatabaseAccess();
    }

    @Override
    public User searchForUserByUsernameAndPassword(User userToSearch) throws SQLException, UserNotFoundException {
        User user = databaseAccess.searchForUserByUsernameAndPasswordInDB(userToSearch);
        if(user == null){
            throw new UserNotFoundException(USER_NOT_FOUND_EXCEPTION_STRING);
        }

        List<Playlist> userPlaylistsList = databaseAccess.getUserPlaylistsFromDB(user);

        for (Playlist playlist : userPlaylistsList){
            List<PlaylistSong> playlistSongs = databaseAccess.getPlaylistSongsFromDB(playlist);
            playlist.setPlaylistSongsList(playlistSongs);
        }

        user.setPlaylistsList(userPlaylistsList);

        return user;

    }

    @Override
    public Boolean isUsernameAlreadyExistsInDB(User user) throws SQLException {
        return databaseAccess.isUsernameAlreadyExistsInDB(user);
    }

    @Override
    public User addUserToDB(User user) throws SQLException {
        int userID = databaseAccess.addUserToDB(user);
        user.setUserID(userID);
        user.setPlaylistsList(new ArrayList<>());
        return user;
    }

    @Override
    public List<Icon> getAllIcons() throws SQLException {
        return databaseAccess.getAllIconsInDB();
    }

    @Override
    public void closeAllDBConnections() {
        databaseAccess.closeAllDBcConnections();
    }

    @Override
    public void deletePlaylist(Playlist playlist) throws SQLException {
        databaseAccess.deletePlaylistFromDB(playlist);
    }

    @Override
    public PlaylistsStatistics getPlaylistsStatistics(User user,List<Playlist> playlistsList) throws SQLException {
        Hashtable<String,Integer> artistsFrequency = new Hashtable<>();
        Hashtable<String,Integer> yearFrequency = new Hashtable<>();
        ObservableList<XYChart.Series<Number,Number>> tempoChartObservableList=FXCollections.observableArrayList();
        ObservableList<XYChart.Series<Number,Number>> loudnessChartObservableList=FXCollections.observableArrayList();
        ObservableList<XYChart.Series<String,Number>> familiarityChartObservableList=FXCollections.observableArrayList();

        playlistsList = getPlaylistsSongsWithData(user,playlistsList);
        playlistsList = getPlaylistsAverages(user,playlistsList);

        for(Playlist playlist:playlistsList){
            List<PlaylistSong> playlistSongsList = playlist.getPlaylistSongsList();

            XYChart.Series tempoSeries = new XYChart.Series();
            tempoSeries.setName(playlist.getPlaylistName()+"\n (Average: "+String.valueOf(playlist.getAverageTempo())+")");

            XYChart.Series loudnessSeries = new XYChart.Series();
            loudnessSeries.setName(playlist.getPlaylistName()+"\n (Average: "+String.valueOf(playlist.getAverageLoudness())+")");

            XYChart.Series familiaritySeries = new XYChart.Series();
            familiaritySeries.setName(playlist.getPlaylistName());


            int i=1;
            for(PlaylistSong playlistSong:playlistSongsList){
                artistsFrequency.put(playlistSong.getArtistName(), artistsFrequency.getOrDefault(playlistSong.getArtistName(),0)+1);
                String decade=String.valueOf(playlistSong.getYear()/10)+"0s";
                yearFrequency.put(decade, yearFrequency.getOrDefault(decade,0)+1);
                artistsFrequency.put(playlistSong.getArtistName(), artistsFrequency.getOrDefault(playlistSong.getArtistName(),0)+1);

                tempoSeries.getData().add(new XYChart.Data(i, playlistSong.getTempo()));
                loudnessSeries.getData().add(new XYChart.Data(i, playlistSong.getLoudness()));
                i++;
            }
            familiaritySeries.getData().add(new XYChart.Data(playlist.getPlaylistName(), playlist.getAverageFamiliarity()));

            tempoChartObservableList.add(tempoSeries);
            loudnessChartObservableList.add(loudnessSeries);
            familiarityChartObservableList.add(familiaritySeries);
        }

        ObservableList<PieChart.Data> artistsFrequencyObservableList= FXCollections.observableArrayList();
        Set<String> artistsKeys = artistsFrequency.keySet();
        for(String artist: artistsKeys){
            artistsFrequencyObservableList.add(new PieChart.Data(artist, artistsFrequency.get(artist)));
        }

        ObservableList<PieChart.Data> decadesFrequencyObservableList= FXCollections.observableArrayList();
        Set<String> decadeKeys = yearFrequency.keySet();
        for(String decade: decadeKeys){
            decadesFrequencyObservableList.add(new PieChart.Data(decade, yearFrequency.get(decade)));
        }

        Hashtable<String,Integer> genresFrequency = new Hashtable<>();
        ArtistsTags artistsTags = getPlaylistsGenres(user);
        List<String> playlistsGenres = artistsTags.getArtistTagsList();
        for (String genre: playlistsGenres){
            switch (genre) {
                case "hip hop":
                case "hip-hop":
                case "hiphop":
                    genresFrequency.put("Hip Hop", genresFrequency.getOrDefault("Hip Hop", 0) + 1);

                    break;
                case "r b":
                case "rnb":
                    genresFrequency.put("Rhythm and blues", genresFrequency.getOrDefault("Rhythm and blues", 0) + 1);
                    break;
                case "rock and roll":
                case "rock n roll":
                case "rock roll":
                    genresFrequency.put("Rock and roll", genresFrequency.getOrDefault("Rock and roll", 0) + 1);
                    break;
                default:
                    String genreCapital = genre.substring(0, 1).toUpperCase() + genre.substring(1);
                    genresFrequency.put(genreCapital, genresFrequency.getOrDefault(genreCapital, 0) + 1);

                    break;
            }
        }

        ObservableList<PieChart.Data> genresFrequencyObservableList= FXCollections.observableArrayList();
        Set<String> genresKeys = genresFrequency.keySet();
        for(String genre: genresKeys){
            genresFrequencyObservableList.add(new PieChart.Data(genre, genresFrequency.get(genre)));
        }



        return new PlaylistsStatistics(artistsFrequencyObservableList,decadesFrequencyObservableList,genresFrequencyObservableList,tempoChartObservableList,loudnessChartObservableList,familiarityChartObservableList);
    }

    @Override
    public List<Playlist> getPlaylistsSongsWithData(User user, List<Playlist> playlistsList) throws SQLException {
        return databaseAccess.getPlaylistsSongsWithDataFromDB(user,playlistsList);
    }

    @Override
    public List<Playlist> getPlaylistsAverages(User user, List<Playlist> playlistsList) throws SQLException {
        return databaseAccess.getPlaylistsAveragesFromDB(user,playlistsList);
    }

    @Override
    public ArtistsTags getPlaylistsGenres(User user) throws SQLException {
        return databaseAccess.getPlaylistsGenresFromDB(user);
    }

    @Override
    public ArtistsTags getAllArtistsTagsInDB() throws SQLException {


        ArtistsTags artistsTags = databaseAccess.getAllArtistsTagsInDB();
        List<String> playlistsGenres = artistsTags.getArtistTagsList();
        ArtistsTags newArtistsTags = new ArtistsTags();
        for (String genre: playlistsGenres){
            switch (genre) {
                case "hip hop":
                    break;
                case "hip-hop":
                    break;
                case "hiphop":
                    newArtistsTags.addArtistTag("Hip Hop");
                    break;
                case "r b":
                    break;
                case "rnb":
                    newArtistsTags.addArtistTag("Rhythm and blues");
                    break;
                case "rock and roll":
                    break;
                case "rock n roll":
                    break;
                case "rock roll":
                    newArtistsTags.addArtistTag("Rock and roll");
                    break;
                default:
                    String genreCapital = genre.substring(0, 1).toUpperCase() + genre.substring(1);
                    newArtistsTags.addArtistTag(genreCapital);

                    break;
            }
        }


        return newArtistsTags;
    }

    @Override
    public List<PlaylistSong> generatePlaylistByPreferences(PlaylistPreferences playlistPreferences) throws SQLException {
        List<Song> candidateSongs= databaseAccess.getSongsByGenreAndModeFromDB(playlistPreferences);
        PreferencesBasedPlaylistGenerator preferencesBasedPlaylistGenerator = new PreferencesBasedPlaylistGenerator(playlistPreferences,candidateSongs);

        return preferencesBasedPlaylistGenerator.generatePlaylist();
    }

    @Override
    public List<PlaylistSong> generatePlaylistBySong(PlaylistPreferences playlistPreferences) throws SQLException {
        List<Song> candidateSongs= databaseAccess.getCandidateRecommendedSongsBasedOnGivenSongFromDB(playlistPreferences.getReferenceSong());
        SongDistanceBasedPlaylistGenerator songDistanceBasedPlaylistGenerator = new SongDistanceBasedPlaylistGenerator(playlistPreferences,candidateSongs);

        return songDistanceBasedPlaylistGenerator.generatePlaylist();
    }

    @Override
    public void addPlaylistToDB(Playlist playlist) throws SQLException {
        int playlistID=databaseAccess.addPlaylistToDB(playlist);
        playlist.setPlaylistId(playlistID);
    }

    @Override
    public List<Song> getSongsByArtistNameFromDB(Artist artist) throws SQLException {
        return databaseAccess.getSongsByArtistNameFromDB(artist);
    }


}
