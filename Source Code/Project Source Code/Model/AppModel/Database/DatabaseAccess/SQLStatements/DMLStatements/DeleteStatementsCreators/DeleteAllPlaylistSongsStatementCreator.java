package Model.AppModel.Database.DatabaseAccess.SQLStatements.DMLStatements.DeleteStatementsCreators;

import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;
import Model.AppModel.Logic.DTOClasses.Playlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantExceptionsStrings.DELETE_PLAYLIST_SONGS_STATEMENT_EXCEPTION;
import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantStatementsStrings.DELETE_PLAYLIST_SONGS_PREPARED_STATEMENT;

public class DeleteAllPlaylistSongsStatementCreator implements ISQLStatementCreator {
    private Playlist playlistToRemoveSongsFrom;

    public DeleteAllPlaylistSongsStatementCreator(Playlist playlistToRemoveSongsFrom) {
        this.playlistToRemoveSongsFrom = playlistToRemoveSongsFrom;
    }

    @Override
    public PreparedStatement createSQLStatement(Connection connection) throws SQLException {
        PreparedStatement deletePlaylistSongsStatement =connection.prepareStatement(DELETE_PLAYLIST_SONGS_PREPARED_STATEMENT);
        deletePlaylistSongsStatement.setInt(1,playlistToRemoveSongsFrom.getPlaylistId());
        return deletePlaylistSongsStatement;
    }

    @Override
    public String getExceptionStringForStatement() {
        return DELETE_PLAYLIST_SONGS_STATEMENT_EXCEPTION;
    }
}
