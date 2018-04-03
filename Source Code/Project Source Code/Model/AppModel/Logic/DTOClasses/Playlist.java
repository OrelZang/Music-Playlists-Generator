package Model.AppModel.Logic.DTOClasses;

import java.text.DecimalFormat;
import java.util.List;

public class Playlist {
    private int playlistId;
    private int uesrId;
    private String playlistName;
    private List<PlaylistSong> playlistSongsList;
    private float averageTempo;
    private float averageLoudness;
    private float averageFamiliarity;


    public Playlist(int playlistId, int uesrId, String playlistName) {
        this.playlistId = playlistId;
        this.uesrId = uesrId;
        this.playlistName = playlistName;
    }

    public Playlist(int uesrId, String playlistName) {
        this.uesrId = uesrId;
        this.playlistName = playlistName;
    }

    public Playlist(int uesrId, String playlistName, List<PlaylistSong> playlistSongsList) {
        this.uesrId = uesrId;
        this.playlistName = playlistName;
        this.playlistSongsList = playlistSongsList;
    }

    public int getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(int playlistId) {
        this.playlistId = playlistId;
    }

    public int getUesrId() {
        return uesrId;
    }

    public void setUesrId(int uesrId) {
        this.uesrId = uesrId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public List<PlaylistSong> getPlaylistSongsList() {
        return playlistSongsList;
    }

    public void setPlaylistSongsList(List<PlaylistSong> playlistSongsList) {
        this.playlistSongsList = playlistSongsList;
    }

    public String getPlaylistDurationStringFormat(){
        float durationSum=0;
        for (PlaylistSong playlistSong :playlistSongsList){
            durationSum+=playlistSong.getSongDuration();
        }

        float durationInMinutes = durationSum / 60;
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(durationInMinutes).replace(".",":");
    }

    public static String getPlaylistDurationStringFormat(List<PlaylistSong> playlistSongs){
        float durationSum=0;
        for (PlaylistSong playlistSong :playlistSongs){
            durationSum+=playlistSong.getSongDuration();
        }

        float durationInMinutes = durationSum / 60;
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(durationInMinutes).replace(".",":");
    }

    public float getAverageTempo() {
        return averageTempo;
    }

    public void setAverageTempo(float averageTempo) {
        this.averageTempo = averageTempo;
    }

    public float getAverageLoudness() {
        return averageLoudness;
    }

    public void setAverageLoudness(float averageLoudness) {
        this.averageLoudness = averageLoudness;
    }

    public float getAverageFamiliarity() {
        return averageFamiliarity;
    }

    public void setAverageFamiliarity(float averageFamiliarity) {
        this.averageFamiliarity = averageFamiliarity;
    }

    @Override
    public String toString() {
        return "\n\n\t\tPlaylist{" +
                "\n\t\tplaylistId=" + playlistId +
                "\n\t\tplaylistName='" + playlistName + '\'' +
                "\n\t\tplaylistSongsList=" + playlistSongsList +
                '}';
    }
}
