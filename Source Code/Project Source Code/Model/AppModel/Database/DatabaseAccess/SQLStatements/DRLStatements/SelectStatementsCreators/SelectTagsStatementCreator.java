package Model.AppModel.Database.DatabaseAccess.SQLStatements.DRLStatements.SelectStatementsCreators;

import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantExceptionsStrings.SELECT_TAGS_STATEMENT_EXCEPTION;
import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantStatementsStrings.SELECT_TAGS_PREPARED_STATEMENT;

public class SelectTagsStatementCreator implements ISQLStatementCreator {

    @Override
    public PreparedStatement createSQLStatement(Connection connection) throws SQLException {
        return connection.prepareStatement(SELECT_TAGS_PREPARED_STATEMENT);
    }

    @Override
    public String getExceptionStringForStatement() {
        return SELECT_TAGS_STATEMENT_EXCEPTION;
    }
}
