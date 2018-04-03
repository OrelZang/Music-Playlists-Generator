package Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters;

public class SQLConstantStatementsStrings {
    public static final String INSERT_USER_PREPARED_STATEMENT="INSERT INTO users_table (user_username, user_password, user_name,user_icon_id) VALUES (?,?,?,?);";
    public static final String INSERT_PLAYLIST_PREPARED_STATEMENT="INSERT INTO users_playlists_table (user_id, playlist_name) VALUES (?,?); ";
    public static final String INSERT_PLAYLIST_SONG_PREPARED_STATEMENT="INSERT INTO playlists_songs_table (playlist_id, song_id) VALUES (?,?); ";


    public static final String DELETE_PLAYLIST_SONG_PREPARED_STATEMENT="DELETE FROM playlists_songs_table WHERE  playlist_id= ? and song_id=?;";
    public static final String DELETE_PLAYLIST_SONGS_PREPARED_STATEMENT="DELETE FROM playlists_songs_table WHERE  playlist_id= ?;";
    public static final String DELETE_PLAYLIST_PREPARED_STATEMENT="DELETE FROM users_playlists_table WHERE  playlist_id= ?;";

    public static final String SELECT_SONGS_BY_ARTIST_PREPARED_STATEMENT="SELECT * FROM songs_table INNER JOIN artists_table ON song_artist_id=artist_id WHERE " +
                                                                         "artist_name=?;";

    public static final String SELECT_ICONS_PREPARED_STATEMENT="SELECT * FROM icons_table;";
    public static final String SELECT_TAGS_PREPARED_STATEMENT="SELECT * FROM tags_table;";
    public static final String SELECT_USER_BY_USERNAME_PREPARED_STATEMENT="SELECT * FROM users_table where user_username=?;";

    public static final String SELECT_USER_PREPARED_STATEMENT="SELECT * FROM users_table INNER JOIN icons_table on user_icon_id=icon_id where user_username=? " +
                                                              "AND user_password=?;";
    public static final String SELECT_USER_PLAYLISTS_PREPARED_STATEMENT="SELECT * FROM users_playlists_table where user_id=?;";

    public static final String SELECT_PLAYLIST_SONGS_PREPARED_STATEMENT="SELECT playlist_id,songs_table.song_id,song_name,song_year,song_album_name," +
                                                                        "artist_name,artist_id,song_duration FROM artists_table INNER JOIN songs_table INNER JOIN playlists_songs_table" +
                                                                        " ON songs_table.song_artist_id = artists_table.artist_id AND playlists_songs_table.song_id = " +
                                                                        "songs_table.song_id WHERE playlist_id=? ORDER BY playlist_song_id;";


    public static final String SELECT_CANDIDATE_RECOMMENDED_SONGS_BASED_ON_SONG_PREPARED_STATEMENT="SELECT artist_name,song_name,artist_familiarity,song_id," +
            "song_artist_id,song_album_name,song_year,song_tempo,song_danceability,song_duration,song_energy,song_key,song_loudness,song_mode,song_time_signature" +
            " FROM similar_artists_table INNER JOIN artists_table INNER JOIN songs_table ON second_artist_id=artist_id AND song_artist_id=artist_id  " +
            "WHERE first_artist_id=?  AND song_mode=? AND song_time_signature=? UNION SELECT artist_name,song_name,artist_familiarity,song_id," +
            "song_artist_id,song_album_name,song_year,song_tempo,song_danceability,song_duration,song_energy,song_key,song_loudness,song_mode,song_time_signature" +
            " FROM similar_artists_table INNER JOIN artists_table INNER JOIN songs_table ON first_artist_id=artist_id AND song_artist_id=artist_id  " +
            "WHERE second_artist_id=?  AND song_mode=? AND song_time_signature=?;";

    public static final String SELECT_PLAYLISTS_SONGS_WITH_DATA_PREPARED_STATEMENT = "SELECT * FROM playlists_songs_table INNER JOIN songs_table INNER JOIN " +
            "users_playlists_table ON  playlists_songs_table.song_id =songs_table.song_id AND users_playlists_table.playlist_id = playlists_songs_table.playlist_id" +
            " WHERE user_id = ?;";

    public static final String SELECT_PLAYLISTS_AVERAGES_PREPARED_STATEMENT = "SELECT users_playlists_table.playlist_id,AVG(song_tempo),AVG(song_loudness)," +
            "AVG(artist_familiarity) FROM playlists_songs_table INNER JOIN songs_table INNER JOIN users_playlists_table INNER JOIN artists_table ON  " +
            "playlists_songs_table.song_id =songs_table.song_id AND users_playlists_table.playlist_id = playlists_songs_table.playlist_id AND " +
            "songs_table.song_artist_id = artists_table.artist_id WHERE user_id = ? GROUP BY users_playlists_table.playlist_id;";

    public static final String SELECT_PLAYLISTS_GENRES_PREPARED_STATEMENT = "SELECT artist_tag FROM playlists_songs_table INNER JOIN songs_table INNER JOIN " +
            "users_playlists_table INNER JOIN artists_tags_table ON  playlists_songs_table.song_id =songs_table.song_id AND users_playlists_table.playlist_id = playlists_songs_table.playlist_id AND artists_tags_table.artist_id = songs_table.song_artist_id WHERE user_id = ?;";

    public static final String SELECT_SONG_BY_GENRE_PREPARED_STATEMENT = "SELECT * FROM  artists_tags_table INNER JOIN songs_table INNER JOIN artists_table ON " +
            "artists_table.artist_id =  song_artist_id AND  song_artist_id = artists_tags_table.artist_id WHERE artist_tag=? AND song_mode=?;";

}
