package com.blz.day35;

import java.sql.*;


public class EstablishConnection {
    String url = "jdbc:mysql://localhost:3306/addressbookservice";

    String username ="root";

    String password ="Pass@123";

    public Connection getConnection() {

        Connection connection = null;
        try {

            connection =  DriverManager.getConnection(url,username,password);
            return connection;
        }
        catch (SQLException e ) {

            e.printStackTrace();
        }
        return null;
    }

}