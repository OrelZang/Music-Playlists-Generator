package Model.AppModel.Database.DatabaseAccess.SQLStatements.DMLStatements.InsertStatementsCreators;

import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;
import Model.AppModel.Logic.DTOClasses.Playlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantExceptionsStrings.INSERT_PLAYLIST_STATEMENT_EXCEPTION;
import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantStatementsStrings.INSERT_PLAYLIST_PREPARED_STATEMENT;

public class InsertPlaylistStatementCreator implements ISQLStatementCreator {
    private Playlist playlistToInsert;

    public InsertPlaylistStatementCreator(Playlist playlistToInsert) {
        this.playlistToInsert = playlistToInsert;
    }

    @Override
    public PreparedStatement createSQLStatement(Connection connection) throws SQLException {
        PreparedStatement insertPlaylistStatement =connection.prepareStatement(INSERT_PLAYLIST_PREPARED_STATEMENT);
        insertPlaylistStatement.setInt(1,playlistToInsert.getUesrId());
        insertPlaylistStatement.setString(2,playlistToInsert.getPlaylistName());
        return insertPlaylistStatement;

    }

    @Override
    public String getExceptionStringForStatement() {
        return INSERT_PLAYLIST_STATEMENT_EXCEPTION;
    }
}
