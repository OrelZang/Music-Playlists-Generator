package Model.AppModel.Database.DatabaseAccess.SQLStatements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface ISQLStatementCreator {
    PreparedStatement createSQLStatement(Connection connection) throws SQLException;
    String getExceptionStringForStatement();
}
