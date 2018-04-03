package Model.AppModel.Logic.PlaylistGenerators;

import Model.AppModel.Logic.DTOClasses.Playlist;
import Model.AppModel.Logic.DTOClasses.PlaylistPreferences;
import Model.AppModel.Logic.DTOClasses.PlaylistSong;
import Model.AppModel.Logic.DTOClasses.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.abs;

public class PreferencesBasedPlaylistGenerator extends PlaylistGeneratorBaseClass {
    private float currentEstimatedHappiness;

    public PreferencesBasedPlaylistGenerator(PlaylistPreferences playlistPreferences, List<Song> candidateSongs) {
        super(playlistPreferences, candidateSongs);
    }

    @Override
    public List<PlaylistSong> generatePlaylist() {
        List<Song> candidateSongs=getCandidateSongs();
        List<PlaylistSong> generatedPlaylistSongs= new ArrayList<>();
        for(Song currentSong:candidateSongs ){
            currentSong.setRelevanceRank(calculateSongRank(currentSong));
            currentSong.setEstimatedHappiness(currentEstimatedHappiness);

        }

        if(getPlaylistPreferences().getHappines()>=0){
            candidateSongs.sort(Comparator.comparing(Song::getRelevanceRank));

        }else{
            candidateSongs.sort(Comparator.comparing(Song::getRelevanceRank).reversed());

        }

        int happiness=abs(getPlaylistPreferences().getHappines());
        if(happiness<0){
            happiness=-happiness;
        }

        int i= (int) ((happiness/100.0)*(candidateSongs.size()-1));



        while(i>=0&&i<candidateSongs.size()&&generatedPlaylistSongs.size()!=getPlaylistPreferences().getNumberOfSongs()){
            Song song = candidateSongs.get(i);
            PlaylistSong playlistSong = new PlaylistSong(-1,song.getSongID(),song.getName(),song.getArtist().getArtistID(),song.getArtist().getName(),song.getAlbumName(),song.getYear(),song.getDuration());
            playlistSong.setEstimatedHappiness(song.getEstimatedHappiness());
            generatedPlaylistSongs.add(playlistSong);
            i--;
        }

        return generatedPlaylistSongs;
    }

    private float calculateSongRank(Song song){
        float songTempo = getSongNormalizedTempo(song);
        float songLoudness = getSongNormalizedLoudness(song);
        float artistFamiliarity=song.getArtist().getFamiliarity();
        currentEstimatedHappiness= (float) (0.5*songTempo+0.5*songLoudness);
        if(getPlaylistPreferences().getHappines()<0){
            currentEstimatedHappiness=-currentEstimatedHappiness;
        }

        if(getPlaylistPreferences().getHappines()>=0){
            return TEMPO_WEIGHT2*songTempo+LOUDNESS_WEIGHT2*songLoudness+ARTIST_WEIGHT2*artistFamiliarity;
        }else{
            return TEMPO_WEIGHT2*songTempo+LOUDNESS_WEIGHT2*songLoudness+ARTIST_WEIGHT2*-artistFamiliarity;

        }

    }
}
