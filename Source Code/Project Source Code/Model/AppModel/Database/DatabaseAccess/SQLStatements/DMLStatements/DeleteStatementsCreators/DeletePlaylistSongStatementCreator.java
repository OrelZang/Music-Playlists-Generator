package Model.AppModel.Database.DatabaseAccess.SQLStatements.DMLStatements.DeleteStatementsCreators;

import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;
import Model.AppModel.Logic.DTOClasses.PlaylistSong;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantExceptionsStrings.DELETE_PLAYLIST_SONG_STATEMENT_EXCEPTION;
import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantStatementsStrings.DELETE_PLAYLIST_SONG_PREPARED_STATEMENT;

public class DeletePlaylistSongStatementCreator implements ISQLStatementCreator {
    private PlaylistSong playlistSongToDelete;

    public DeletePlaylistSongStatementCreator(PlaylistSong playlistSongToDelete) {
        this.playlistSongToDelete = playlistSongToDelete;
    }

    @Override
    public PreparedStatement createSQLStatement(Connection connection) throws SQLException {
        PreparedStatement deletePlaylistSongStatement =connection.prepareStatement(DELETE_PLAYLIST_SONG_PREPARED_STATEMENT);
        deletePlaylistSongStatement.setInt(1,playlistSongToDelete.getPlaylistID());
        deletePlaylistSongStatement.setString(2,playlistSongToDelete.getSongID());
        return deletePlaylistSongStatement;
    }

    @Override
    public String getExceptionStringForStatement() {
        return DELETE_PLAYLIST_SONG_STATEMENT_EXCEPTION;
    }
}
