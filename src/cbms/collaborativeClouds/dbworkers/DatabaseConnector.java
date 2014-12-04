/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cbms.collaborativeClouds.dbworkers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author CollaborativeClouds Software Solutions
 */
public class DatabaseConnector {
    
    Connection mConnect;
    Statement mStatement;
    ResultSet mResult;
    
    public DatabaseConnector() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        mConnect=DriverManager.getConnection("jdbc:mysql://localhost:3306/cbms","root","");
    }
    public ResultSet select_execute(String sql) throws SQLException{
        mStatement=mConnect.createStatement();
        mResult=mStatement.executeQuery(sql);
        return mResult;
    }
    public int insert_execute(String sql) throws SQLException{
        mStatement=mConnect.createStatement();
        int status=mStatement.executeUpdate(sql);
        return status;
    }
}
