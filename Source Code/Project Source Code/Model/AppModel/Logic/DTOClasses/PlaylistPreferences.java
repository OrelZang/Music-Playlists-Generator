package Model.AppModel.Logic.DTOClasses;

public class PlaylistPreferences {
    private String genre;
    private int happines;
    private int numberOfSongs;
    private Song referenceSong;

    public PlaylistPreferences(String genre, int happines, int numberOfSongs) {
        this.genre = genre;
        this.happines = happines;
        this.numberOfSongs = numberOfSongs;
    }

    public PlaylistPreferences(int numberOfSongs, Song referenceSong) {
        this.numberOfSongs = numberOfSongs;
        this.referenceSong = referenceSong;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getHappines() {
        return happines;
    }

    public void setHappines(int happines) {
        this.happines = happines;
    }

    public int getNumberOfSongs() {
        return numberOfSongs;
    }

    public void setNumberOfSongs(int numberOfSongs) {
        this.numberOfSongs = numberOfSongs;
    }

    public Song getReferenceSong() {
        return referenceSong;
    }

    public void setReferenceSong(Song referenceSong) {
        this.referenceSong = referenceSong;
    }
}
