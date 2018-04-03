package Model.AppModel.Database.DatabaseAccess.SQLStatements.DRLStatements.SelectStatementsCreators;

import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;
import Model.AppModel.Logic.DTOClasses.Playlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantExceptionsStrings.SELECT_PLAYLIST_SONGS_STATEMENT_EXCEPTION;
import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantStatementsStrings.SELECT_PLAYLIST_SONGS_PREPARED_STATEMENT;

public class SelectPlaylistSongsStatementCreator implements ISQLStatementCreator {
    private Playlist playlistToSearchSongsFor;

    public SelectPlaylistSongsStatementCreator(Playlist playlistToSearchSongsFor) {
        this.playlistToSearchSongsFor = playlistToSearchSongsFor;
    }

    @Override
    public PreparedStatement createSQLStatement(Connection connection) throws SQLException {
        PreparedStatement selectPlaylistSongsStatement =connection.prepareStatement(SELECT_PLAYLIST_SONGS_PREPARED_STATEMENT);
        selectPlaylistSongsStatement.setInt(1, playlistToSearchSongsFor.getPlaylistId());
        return selectPlaylistSongsStatement;
    }

    @Override
    public String getExceptionStringForStatement() {
        return SELECT_PLAYLIST_SONGS_STATEMENT_EXCEPTION;
    }
}
