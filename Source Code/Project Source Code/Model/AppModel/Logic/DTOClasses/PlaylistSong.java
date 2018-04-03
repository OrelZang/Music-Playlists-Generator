package Model.AppModel.Logic.DTOClasses;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class PlaylistSong {
    private int playlistID;
    private String songID;
    private String songName;
    private String artistID;
    private String artistName;
    private String albumName;
    private int year;
    private float songDuration;
    private float tempo;
    private float loudness;
    private float tempoSimilarity;
    private float loudnessSimilarity;
    private float estimatedHappiness;

    public PlaylistSong(int playlistID, String songID) {
        this.playlistID = playlistID;
        this.songID = songID;
        tempoSimilarity=-1;
        loudnessSimilarity=-1;
        estimatedHappiness=-2;
    }

    public PlaylistSong(int playlistID, String songID, String songName, String artistID, String artistName, String albumName, int year,float songDuration) {
        this.playlistID = playlistID;
        this.songID = songID;
        this.songName = songName;
        this.artistID = artistID;
        this.artistName = artistName;
        this.albumName = albumName;
        this.year = year;
        this.songDuration=songDuration;
        tempoSimilarity=-1;
        loudnessSimilarity=-1;
        estimatedHappiness=-2;
    }

    public int getPlaylistID() {
        return playlistID;
    }

    public String getSongID() {
        return songID;
    }

    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistID() {
        return artistID;
    }

    public void setArtistID(String artistID) {
        this.artistID = artistID;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getSongDuration() {
        return songDuration;
    }

    public void setSongDuration(float songDuration) {
        this.songDuration = songDuration;
    }

    public float getTempo() {
        return tempo;
    }

    public void setTempo(float tempo) {
        this.tempo = tempo;
    }

    public float getLoudness() {
        return loudness;
    }

    public void setLoudness(float loudness) {
        this.loudness = loudness;
    }

    public float getTempoSimilarity() {
        return tempoSimilarity;
    }

    public void setTempoSimilarity(float tempoSimilarity) {
        this.tempoSimilarity = tempoSimilarity;
    }

    public float getLoudnessSimilarity() {
        return loudnessSimilarity;
    }

    public void setLoudnessSimilarity(float loudnessSimilarity) {
        this.loudnessSimilarity = loudnessSimilarity;
    }

    public String getSongDurationStringFormat() {
        float durationInMinutes = songDuration / 60;
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(durationInMinutes).replace(".",":");
    }

    public float getEstimatedHappiness() {
        return estimatedHappiness;
    }

    public void setEstimatedHappiness(float estimatedHappiness) {
        this.estimatedHappiness = estimatedHappiness;
    }
}
