package com.desolation.library.controller;

import com.desolation.library.model.User;
import java.sql.*;

/**
 *
 * @author nekres
 */
public class SQLUtils {
    public static Connection connection = null;
    public static final String URL = "jdbc:mysql://localhost:3306/library";
    public static final boolean openConnection(final String user,final String pass){
        try {
            connection = DriverManager.getConnection(URL, user,pass);
            System.out.println("Соединение установлено.");
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public static final ResultSet executeQuery(final String query) throws SQLException{
        Statement s = connection.createStatement();
        ResultSet result = s.executeQuery(query);
        return result;
    }
    public static final int update(final String query) throws SQLException{
        Statement s = connection.createStatement();
        int r = s.executeUpdate(query);
        return r;
    }
    public static final boolean isLoginExist(final String login) throws SQLException{
        PreparedStatement s = connection.prepareStatement("select isLoginExist(?)");
        s.setString(1, login);
        ResultSet r = s.executeQuery();
        int i = 0;
        while(r.next())
            i = r.getInt(1);
        if (i == 1)
            return true;
        else return false;        
    }
    public static final int signIn(final String login, final String password) throws SQLException{
        PreparedStatement ps = connection.prepareStatement("select signIn(?,?)");
        ps.setString(1, login);
        ps.setString(2, password);
        ResultSet r = ps.executeQuery();
        int result = -1;
        while(r.next())
            result = r.getInt(1);
        
       return result;
    }
    public static final void insert(final String query){
        
    }
    public static final boolean addUser(final String login, final String password, final User user) throws SQLException{
        String procedure = "{call addAccount(?,?,?,?,?,?,?,?)}";
        CallableStatement cs = connection.prepareCall(procedure);
        cs.setString(1, user.getLogin());
        cs.setString(2, user.getPassword());
        cs.setString(3, user.getName());
        cs.setString(4, user.getSurname());
        cs.setString(5, user.getMiddle_name());
        cs.setString(6, user.getAddress());
        cs.setString(7, user.getPhone());
        cs.setString(8, user.getBirthday());
        boolean result = cs.execute();
        cs.close();
        return result;
    }
    
}
