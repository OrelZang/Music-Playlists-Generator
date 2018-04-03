package Model.AppModel.Database.DatabaseAccess.SQLStatements.DRLStatements.SelectStatementsCreators;

import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;
import Model.AppModel.Logic.DTOClasses.Song;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantExceptionsStrings.SELECT_CANDIDATE_SONGS_STATEMENT_EXCEPTION;
import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantStatementsStrings.SELECT_CANDIDATE_RECOMMENDED_SONGS_BASED_ON_SONG_PREPARED_STATEMENT;

public class SelectCandidateRecommendedSongsBasedOnSongStatementCreator implements ISQLStatementCreator {
    private Song referenceSong;

    public SelectCandidateRecommendedSongsBasedOnSongStatementCreator(Song referenceSong) {
        this.referenceSong = referenceSong;
    }

    @Override
    public PreparedStatement createSQLStatement(Connection connection) throws SQLException {
        PreparedStatement selectSongsStatement =connection.prepareStatement(SELECT_CANDIDATE_RECOMMENDED_SONGS_BASED_ON_SONG_PREPARED_STATEMENT);
        selectSongsStatement.setString(1,referenceSong.getArtist().getArtistID());
        selectSongsStatement.setInt(2,referenceSong.getMode());
        selectSongsStatement.setInt(3,referenceSong.getTimeSignature());
        selectSongsStatement.setString(4,referenceSong.getArtist().getArtistID());
        selectSongsStatement.setInt(5,referenceSong.getMode());
        selectSongsStatement.setInt(6,referenceSong.getTimeSignature());
        return selectSongsStatement;
    }

    @Override
    public String getExceptionStringForStatement() {
        return SELECT_CANDIDATE_SONGS_STATEMENT_EXCEPTION;
    }
}
