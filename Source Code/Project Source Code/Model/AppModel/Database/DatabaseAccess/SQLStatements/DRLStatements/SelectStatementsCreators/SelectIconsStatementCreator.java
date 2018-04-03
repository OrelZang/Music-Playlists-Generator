package Model.AppModel.Database.DatabaseAccess.SQLStatements.DRLStatements.SelectStatementsCreators;

import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantExceptionsStrings.SELECT_ICONS_STATEMENT_EXCEPTION;
import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantStatementsStrings.SELECT_ICONS_PREPARED_STATEMENT;

public class SelectIconsStatementCreator implements ISQLStatementCreator {


    @Override
    public PreparedStatement createSQLStatement(Connection connection) throws SQLException {
        PreparedStatement selectIconsStatement =connection.prepareStatement(SELECT_ICONS_PREPARED_STATEMENT);
        return selectIconsStatement;
    }

    @Override
    public String getExceptionStringForStatement() {
        return SELECT_ICONS_STATEMENT_EXCEPTION;
    }
}
