package Model.AppModel.Database.DatabaseAccess.SQLStatements.DRLStatements.SelectStatementsCreators;

import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;
import Model.AppModel.Logic.DTOClasses.Playlist;
import Model.AppModel.Logic.DTOClasses.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantExceptionsStrings.SELECT_PLAYLIST_SONGS_STATEMENT_EXCEPTION;
import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantStatementsStrings.SELECT_PLAYLISTS_SONGS_WITH_DATA_PREPARED_STATEMENT;
import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantStatementsStrings.SELECT_PLAYLIST_SONGS_PREPARED_STATEMENT;

public class SelectPlaylistSongsDataStatementCreator implements ISQLStatementCreator {
    private User user;

    public SelectPlaylistSongsDataStatementCreator(User user) {
        this.user = user;
    }

    @Override
    public PreparedStatement createSQLStatement(Connection connection) throws SQLException {
        PreparedStatement selectPlaylistSongsStatement =connection.prepareStatement(SELECT_PLAYLISTS_SONGS_WITH_DATA_PREPARED_STATEMENT);
        selectPlaylistSongsStatement.setInt(1, user.getUserID());
        return selectPlaylistSongsStatement;
    }

    @Override
    public String getExceptionStringForStatement() {
        return SELECT_PLAYLIST_SONGS_STATEMENT_EXCEPTION;
    }
}
