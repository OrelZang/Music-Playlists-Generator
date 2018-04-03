package Model.AppModel.Database.DatabaseAccess.SQLStatements.DMLStatements.DeleteStatementsCreators;

import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;
import Model.AppModel.Logic.DTOClasses.Playlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantExceptionsStrings.DELETE_PLAYLIST_STATEMENT_EXCEPTION;
import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantStatementsStrings.DELETE_PLAYLIST_PREPARED_STATEMENT;

public class DeletePlaylistStatementCreator implements ISQLStatementCreator {
    private Playlist playlistToRemove;

    public DeletePlaylistStatementCreator(Playlist playlistToRemove) {
        this.playlistToRemove = playlistToRemove;
    }

    @Override
    public PreparedStatement createSQLStatement(Connection connection) throws SQLException {
        PreparedStatement deletePlaylistStatement =connection.prepareStatement(DELETE_PLAYLIST_PREPARED_STATEMENT);
        deletePlaylistStatement.setInt(1,playlistToRemove.getPlaylistId());
        return deletePlaylistStatement;
    }

    @Override
    public String getExceptionStringForStatement() {
        return DELETE_PLAYLIST_STATEMENT_EXCEPTION;
    }
}
