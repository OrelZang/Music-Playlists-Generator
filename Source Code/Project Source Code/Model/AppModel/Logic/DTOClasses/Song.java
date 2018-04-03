package Model.AppModel.Logic.DTOClasses;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Song {
    private String songID;
    private String name;
    private Artist artist;
    private String albumName;
    private int year;
    private float tempo;
    private float danceability;
    private float duration;
    private float energy;
    private int key;
    private float loudness;
    private int mode;
    private int timeSignature;
    private float relevanceRank;
    private int seenCounter;
    private float tempoRank;
    private float loudnessRank;
    private float artistRank;
    private float tempoSimilarity;
    private float loudnessSimilarity;
    private float estimatedHappiness;

    public Song(String songID, String name, Artist artist, String albumName, int year, float tempo, float danceability, float duration, float energy, int key, float loudness, int mode, int timeSignature) {
        this.songID = songID;
        this.name = name;
        this.artist = artist;
        this.albumName = albumName;
        this.year = year;
        this.tempo = tempo;
        this.danceability = danceability;
        this.duration = duration;
        this.energy = energy;
        this.key = key;
        this.loudness = loudness;
        this.mode = mode;
        this.timeSignature = timeSignature;
        seenCounter=0;
    }

    public float getTempoRank() {
        return tempoRank;
    }

    public void setTempoRank(float tempoRank) {
        this.tempoRank = tempoRank;
    }

    public float getLoudnessRank() {
        return loudnessRank;
    }

    public void setLoudnessRank(float loudnessRank) {
        this.loudnessRank = loudnessRank;
    }

    public float getArtistRank() {
        return artistRank;
    }

    public void setArtistRank(float artistRank) {
        this.artistRank = artistRank;
    }

    public int getSeenCounter() {
        return seenCounter;
    }

    public void setSeenCounter(int seenCounter) {
        this.seenCounter = seenCounter;
    }

    public String getSongID() {
        return songID;
    }

    public void setSongID(String songID) {
        this.songID = songID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
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

    public float getTempo() {
        return tempo;
    }

    public void setTempo(float tempo) {
        this.tempo = tempo;
    }

    public float getDanceability() {
        return danceability;
    }

    public void setDanceability(float danceability) {
        this.danceability = danceability;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public float getEnergy() {
        return energy;
    }

    public void setEnergy(float energy) {
        this.energy = energy;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public float getLoudness() {
        return loudness;
    }

    public void setLoudness(float loudness) {
        this.loudness = loudness;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public int getTimeSignature() {
        return timeSignature;
    }


    public void setTimeSignature(int timeSignature) {
        this.timeSignature = timeSignature;
    }

    public float getRelevanceRank() {
        return relevanceRank;
    }

    public void setRelevanceRank(float relevanceRank) {
        this.relevanceRank = relevanceRank;
    }

    public SongTableItem getSongTableItem(){
        return new SongTableItem(name,artist.getName(),albumName,String.valueOf(year));
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

    public float getEstimatedHappiness() {
        return estimatedHappiness;
    }

    public void setEstimatedHappiness(float estimatedHappiness) {
        this.estimatedHappiness = estimatedHappiness;
    }

    @Override
    public String toString() {
        return "Song{" +
                "songID='" + songID + '\'' +
                ", name='" + name + '\'' +
                ", artist=" + artist +
                ", albumName='" + albumName + '\'' +
                ", year=" + year +
                ", tempo=" + tempo +
                ", danceability=" + danceability +
                ", duration=" + duration +
                ", energy=" + energy +
                ", key=" + key +
                ", loudness=" + loudness +
                ", mode=" + mode +
                ", timeSignature=" + timeSignature +
                '}';
    }

}
