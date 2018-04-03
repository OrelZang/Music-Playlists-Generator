package Model.AppModel.Logic.PlaylistGenerators;

import Model.AppModel.Logic.DTOClasses.PlaylistPreferences;
import Model.AppModel.Logic.DTOClasses.Song;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static Model.AppModel.Logic.PlaylistGenerators.ConstantNumericValues.*;
import static Model.AppModel.Logic.PlaylistGenerators.ConstantNumericValues.LOUDNESS_MIN;

public abstract class PlaylistGeneratorBaseClass implements IPlaylistGenerator{
    private PlaylistPreferences playlistPreferences;
    private List<Song> candidateSongs;
    public  final static float TEMPO_WEIGHT= (float) 0.4;
    public  final static float LOUDNESS_WEIGHT= (float) 0.4;
    public  final static float ARTIST_WEIGHT= (float) 0.2;
    public  final static float TEMPO_WEIGHT2= (float) 0.5;
    public  final static float LOUDNESS_WEIGHT2= (float) 0.3;
    public  final static float ARTIST_WEIGHT2= (float) 0.2;
    public PlaylistGeneratorBaseClass(PlaylistPreferences playlistPreferences, List<Song> candidateSongs) {
        this.playlistPreferences = playlistPreferences;
        this.candidateSongs = candidateSongs;
    }

    protected float getSongNormalizedTempo(Song song){
        float songTempo = song.getTempo();
        return (songTempo - TEMPO_MIN)/(TEMPO_MAX-TEMPO_MIN);
    }

    protected float getSongNormalizedLoudness(Song song){
        float songLoudness = song.getLoudness();
        return (songLoudness - LOUDNESS_MIN)/(LOUDNESS_MAX-LOUDNESS_MIN);
    }

    public PlaylistPreferences getPlaylistPreferences() {
        return playlistPreferences;
    }

    public void setPlaylistPreferences(PlaylistPreferences playlistPreferences) {
        this.playlistPreferences = playlistPreferences;
    }

    public List<Song> getCandidateSongs() {
        return candidateSongs;
    }

    public void setCandidateSongs(List<Song> candidateSongs) {
        this.candidateSongs = candidateSongs;
    }




}
