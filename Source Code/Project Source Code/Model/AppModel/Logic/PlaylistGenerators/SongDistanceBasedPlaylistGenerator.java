package Model.AppModel.Logic.PlaylistGenerators;

import Model.AppModel.Logic.DTOClasses.PlaylistPreferences;
import Model.AppModel.Logic.DTOClasses.PlaylistSong;
import Model.AppModel.Logic.DTOClasses.Song;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static Model.AppModel.Logic.PlaylistGenerators.ConstantNumericValues.*;
import static java.lang.Math.abs;

public class SongDistanceBasedPlaylistGenerator extends PlaylistGeneratorBaseClass  {
    private float currentTempoSimilarity;
    private float currentLoudnessSimilarity;

    public SongDistanceBasedPlaylistGenerator(PlaylistPreferences playlistPreferences, List<Song> candidateSongs) {
        super(playlistPreferences, candidateSongs);
    }



    @Override
    public List<PlaylistSong> generatePlaylist() {
        Song referenceSong=getPlaylistPreferences().getReferenceSong();
//        System.out.println("tempo: "+referenceSong.getTempo());
//        System.out.println("loudness: "+referenceSong.getLoudness());
        List<Song> candidateSongs=getCandidateSongs();
        int numberOfSongsInPlaylist=getPlaylistPreferences().getNumberOfSongs();

        List<PlaylistSong> generatedPlaylistSongs= new ArrayList<>();

        for(Song currentSong:candidateSongs ){
            float currentSimilarityRank = calculateSimilarityBetweenTwoSongs(referenceSong,currentSong);
            currentSong.setRelevanceRank(currentSimilarityRank);

            currentSong.setTempoSimilarity(currentTempoSimilarity);
            currentSong.setLoudnessSimilarity(currentLoudnessSimilarity);
        }

        candidateSongs.sort(Comparator.comparing(Song::getRelevanceRank).reversed());

        int i=0;
        while (i<numberOfSongsInPlaylist && i<candidateSongs.size()){
            Song song = candidateSongs.get(i);
            PlaylistSong playlistSong = new PlaylistSong(-1,song.getSongID(),song.getName(),song.getArtist().getArtistID(),song.getArtist().getName(),song.getAlbumName(),song.getYear(),song.getDuration());
            playlistSong.setTempoSimilarity(song.getTempoSimilarity());
            playlistSong.setLoudnessSimilarity(song.getLoudnessSimilarity());
            generatedPlaylistSongs.add(playlistSong);
            i++;
        }

        return generatedPlaylistSongs;


    }


    private float calculateSimilarityBetweenTwoSongs(Song firstSong, Song secondSong){
        float tempoSimilarity = 1-(abs(getSongNormalizedTempo(firstSong) - getSongNormalizedTempo(secondSong)));
        float loudnessSimilarity = 1-(abs(getSongNormalizedLoudness(firstSong) - getSongNormalizedLoudness(secondSong)));

        currentTempoSimilarity=tempoSimilarity;
        currentLoudnessSimilarity=loudnessSimilarity;

        return TEMPO_WEIGHT*tempoSimilarity+LOUDNESS_WEIGHT*loudnessSimilarity+ARTIST_WEIGHT*secondSong.getArtist().getFamiliarity();
    }
}
