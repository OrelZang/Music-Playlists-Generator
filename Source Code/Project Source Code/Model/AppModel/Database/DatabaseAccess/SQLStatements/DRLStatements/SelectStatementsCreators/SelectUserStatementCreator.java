package Model.AppModel.Database.DatabaseAccess.SQLStatements.DRLStatements.SelectStatementsCreators;

import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;
import Model.AppModel.Logic.DTOClasses.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantExceptionsStrings.SELECT_USER_STATEMENT_EXCEPTION;
import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantStatementsStrings.SELECT_USER_PREPARED_STATEMENT;


public class SelectUserStatementCreator implements ISQLStatementCreator {
    private User userToSearch;

    public SelectUserStatementCreator(User userToSearch) {
        this.userToSearch = userToSearch;
    }

    @Override
    public PreparedStatement createSQLStatement(Connection connection) throws SQLException {
        PreparedStatement selectUserStatement =connection.prepareStatement(SELECT_USER_PREPARED_STATEMENT);
        selectUserStatement.setString(1,userToSearch.getUsername());
        selectUserStatement.setString(2,userToSearch.getPassword());
        return selectUserStatement;
    }

    @Override
    public String getExceptionStringForStatement() {
        return SELECT_USER_STATEMENT_EXCEPTION;
    }
}
