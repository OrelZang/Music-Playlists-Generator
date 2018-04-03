package Model.AppModel.Database.DatabaseAccess.SQLStatements.DMLStatements;

import Model.AppModel.Database.DatabaseAccess.DBConnectionsPool;
import Model.AppModel.Database.DatabaseAccess.SQLStatements.ISQLStatementCreator;
import Model.AppModel.Database.DatabaseAccess.SQLStatements.SQLStatementExecutor;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DMLStatementExecutor extends SQLStatementExecutor {
    private List<ISQLStatementCreator> dmlStatementCreatorsList;
    public static int lastGeneratedKey;

    public DMLStatementExecutor(List<ISQLStatementCreator> dmlStatementCreatorsList) {
        this.dmlStatementCreatorsList = dmlStatementCreatorsList;
    }

    public int executeDMLStatement() throws SQLException {
        Boolean isFirstStatement=true;
        Boolean isErrorOccurred=false;
        Connection connection=null;
        ResultSet generatedKey=null;
        int id = 0;
        String exceptionString=dmlStatementCreatorsList.get(0).getExceptionStringForStatement();
        PreparedStatement lastPreparedStatement = null;
        List<PreparedStatement> preparedStatementsList=new ArrayList<>();

        try{
            DBConnectionsPool dbConnectionsPool = DBConnectionsPool.getInstance();
            connection = dbConnectionsPool.getConnection();

            if(dmlStatementCreatorsList.size()>1){
                connection.setAutoCommit(false);
            }

            for(ISQLStatementCreator dmlStatementCreator:dmlStatementCreatorsList){
                PreparedStatement currentPreparedStatement = dmlStatementCreator.createSQLStatement(connection);
                preparedStatementsList.add(currentPreparedStatement);

                int affectedRows = currentPreparedStatement.executeUpdate();


                if (affectedRows == 0) {
                    isErrorOccurred=true;
                }

                if(isFirstStatement){
                    generatedKey = currentPreparedStatement.getGeneratedKeys();
                    if (generatedKey.next()) {
                        id = generatedKey.getInt(1);
                        lastGeneratedKey=id;
                    }

                    isFirstStatement=false;
                }

                lastPreparedStatement=currentPreparedStatement;


            }





            if(dmlStatementCreatorsList.size()==1&&lastPreparedStatement!=null){
                generatedKey = lastPreparedStatement.getGeneratedKeys();



                if (generatedKey.next()) {
                    id = generatedKey.getInt(1);

                }
            }


            if(dmlStatementCreatorsList.size()>1){
                connection.commit();

            }



        } catch (SQLException | ClassNotFoundException e) {
            if(dmlStatementCreatorsList.size()>1){

                if (connection != null) {
                    connection.rollback();
                }

            }
            isErrorOccurred=true;
        } finally {
            if(dmlStatementCreatorsList.size()>1){
                if (connection != null) {
                    connection.setAutoCommit(true);
                }
            }
            super.closeDBResources(connection,preparedStatementsList,generatedKey);


        }

        if(isErrorOccurred){
            throw new SQLException(exceptionString);
        }

        return id;
    }


}
