package Model.AppModel.Database.DatabaseAccess.SQLStatements;

import Model.AppModel.Database.DatabaseAccess.DBConnectionsPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public abstract class SQLStatementExecutor {
    protected void closeDBResources(Connection connection, List<PreparedStatement> preparedStatementsList, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }


            for(PreparedStatement preparedStatement:preparedStatementsList){
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }


            if (connection != null) {
                DBConnectionsPool dbConnectionsPool = DBConnectionsPool.getInstance();
                dbConnectionsPool.returnConnectionToPool(connection);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }
}
