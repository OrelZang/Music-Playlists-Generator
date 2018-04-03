package Model.AppModel.Database.DatabaseAccess.SQLStatements.DRLStatements.SelectStatementsCreators;

import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;
import Model.AppModel.Logic.DTOClasses.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantExceptionsStrings.SELECT_PLAYLIST_SONGS_STATEMENT_EXCEPTION;
import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantStatementsStrings.SELECT_PLAYLISTS_AVERAGES_PREPARED_STATEMENT;


public class SelectPlaylistAveragesStatementCreator implements ISQLStatementCreator {
    private User user;

    public SelectPlaylistAveragesStatementCreator(User user) {
        this.user = user;
    }

    @Override
    public PreparedStatement createSQLStatement(Connection connection) throws SQLException {
        PreparedStatement selectPlaylistAverageStatement =connection.prepareStatement(SELECT_PLAYLISTS_AVERAGES_PREPARED_STATEMENT);
        selectPlaylistAverageStatement.setInt(1, user.getUserID());
        return selectPlaylistAverageStatement;
    }

    @Override
    public String getExceptionStringForStatement() {
        return SELECT_PLAYLIST_SONGS_STATEMENT_EXCEPTION;
    }
}
