package Model.AppModel.Database.DatabaseAccess.SQLStatements.DMLStatements.InsertStatementsCreators;

import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;
import Model.AppModel.Logic.DTOClasses.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantExceptionsStrings.INSERT_USER_STATEMENT_EXCEPTION;
import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLConstantStatementsStrings.INSERT_USER_PREPARED_STATEMENT;

public class InsertUserStatementCreator implements ISQLStatementCreator {
    private User userToInsert;

    public InsertUserStatementCreator(User userToInsert) {
        this.userToInsert = userToInsert;
    }

    @Override
    public PreparedStatement createSQLStatement(Connection connection) throws SQLException {
        PreparedStatement insertUserStatement =connection.prepareStatement(INSERT_USER_PREPARED_STATEMENT);
        insertUserStatement.setString(1,userToInsert.getUsername());
        insertUserStatement.setString(2,userToInsert.getPassword());
        insertUserStatement.setString(3,userToInsert.getName());
        insertUserStatement.setInt(4,userToInsert.getIcon().getIconID());
        return insertUserStatement;
    }

    @Override
    public String getExceptionStringForStatement() {
        return INSERT_USER_STATEMENT_EXCEPTION;
    }
}
