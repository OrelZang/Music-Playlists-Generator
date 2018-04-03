package Model.AppModel.Database.DatabaseAccess.SQLStatements.DRLStatements.SelectStatementsCreators;

import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;
import Model.AppModel.Logic.DTOClasses.Artist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantExceptionsStrings.SELECT_SONGS_BY_ARTIST_STATEMENT_EXCEPTION;
import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantStatementsStrings.SELECT_SONGS_BY_ARTIST_PREPARED_STATEMENT;

public class SelectSongsByArtistStatementCreator implements ISQLStatementCreator {
    private Artist artistToFindSongsFor;

    public SelectSongsByArtistStatementCreator(Artist artistToFindSongsFor) {
        this.artistToFindSongsFor = artistToFindSongsFor;
    }

    @Override
    public PreparedStatement createSQLStatement(Connection connection) throws SQLException {
        PreparedStatement selectSongsStatement =connection.prepareStatement(SELECT_SONGS_BY_ARTIST_PREPARED_STATEMENT);
        selectSongsStatement.setString(1, artistToFindSongsFor.getName());
        return selectSongsStatement;
    }

    @Override
    public String getExceptionStringForStatement() {
        return SELECT_SONGS_BY_ARTIST_STATEMENT_EXCEPTION;
    }
}
