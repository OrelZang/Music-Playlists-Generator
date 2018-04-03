package Model.AppModel.Database.DatabaseAccess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

import static Model.AppModel.Database.DatabaseAccess.ConstantStringsAndParameters.SQLServerParameters.*;


public class DBConnectionsPool {
    private static DBConnectionsPool ourInstance = new DBConnectionsPool();
    private Queue<Connection> connectionsList = new LinkedList<>();

    public static DBConnectionsPool getInstance() {
        return ourInstance;
    }

    public synchronized Connection getConnection() throws ClassNotFoundException, SQLException {
        if (connectionsList.isEmpty()){
            Class.forName(SQL_DRIVER_CLASS);
            return DriverManager.getConnection(SQL_SERVER_BASE_ADDRESS+"//"+SQL_SERVER_IP+":"+SQL_SERVER_PORT+"/"+SQL_SERVER_SCHEMA_NAME,SQL_SERVER_USERNAME,SQL_SERVER_PASSWORD);
        }

        return connectionsList.remove();
    }

    public synchronized void returnConnectionToPool(Connection connection){
        connectionsList.add(connection);
    }

    public void closeAllDBConnections(){
        while (!connectionsList.isEmpty()){
            try {
                connectionsList.remove().close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private DBConnectionsPool() {

    }
}
