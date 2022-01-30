package com.blz.day35;

import org.jetbrains.annotations.NotNull;

import java.sql.*;
import java.util.Scanner;

public class AddrssBookCRUDoperation {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        EstablishConnection mySQLConnection = new EstablishConnection();

        Connection connection = mySQLConnection.getConnection();

        readFromDataBase(connection);

        editData(connection);

        showByDate(connection);

        retrieveByCityOrState(connection);

        insertData(connection);

    }

    private static void insertData(@NotNull Connection connection) {

        System.out.print("Enter person id = ");
        int id = sc.nextInt();

        System.out.print("Enter person firstname  = ");
        String firstName = sc.next();

        System.out.print("Enter person lastname  = ");
        String lastName = sc.next();

        System.out.print("Enter person date added  = ");
        String date = sc.next();

        System.out.print("Enter person address ID  = ");
        int addressID = sc.nextInt();

        String  sql = " insert into personName value( ?,?,?,?,?)";
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1,id);
            preparedStatement.setString(2,firstName);
            preparedStatement.setString(3,lastName);
            preparedStatement.setString(4,date);
            preparedStatement.setInt(5,addressID);

        }
        catch (SQLException e ) {

            e.printStackTrace();
        }
    }

    private static void retrieveByCityOrState(@NotNull Connection connection) {

        System.out.print("Enter city = ");
        String city = sc.next();

        System.out.print("Enter Sate = ");
        String state = sc.next();

        String sql ="select COUNT(personNAme.Firstname) from personName inner join address on personName.ID = address.personID where address.CITY = ? or address.STATE = ?";

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,city);
            preparedStatement.setString(2,state);
            ResultSet set = preparedStatement.executeQuery();
            while (set.next()) {

                System.out.println("number of persons from "+ city+ " & sate "+state+" = " +set.getInt(1));
            }
        }
        catch (SQLException e) {

            e.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null) connection.close();
                if (connection != null) connection.close();

            }
            catch (SQLException e) {

                e.printStackTrace();
            }
        }

    }

    private static void showByDate(@NotNull Connection connection) {

        System.out.print("Enter star date = ");
        String startDate =sc.next();

        System.out.print("Enter end date = ");
        String endDate = sc.next();

        String sql = "SELECT * FROM personName WHERE DateAdded BETWEEN ? AND ?";
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,startDate);
            preparedStatement.setString(2,endDate);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                System.out.println(
                        "ID = " + resultSet.getInt(1) +
                                " Name = " + resultSet.getString(2)
                );
            }

        }
        catch (SQLException e) {

            e.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null) connection.close();
                if (connection != null) connection.close();

            }
            catch (SQLException e) {

                e.printStackTrace();
            }
        }
    }

    private static void editData(@NotNull Connection connection) {

        System.out.println("Enter new city name = ");
        String city = sc.next();

        System.out.println("Enter existing city name = ");
        String oldCity = sc.next();

        String sql = "update address set city = ?  where city = ?";

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(2,oldCity);
            preparedStatement.setString(1,city);
            int i = preparedStatement.executeUpdate();
            System.out.println("data updated successfully : number of rows affected = " + i);

        }
        catch (SQLException e ) {

            e.printStackTrace();
        }
        finally {
            try {
                if (preparedStatement != null) connection.close();
                if (connection != null) connection.close();

            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }



    private static void readFromDataBase(@NotNull Connection connection) {

        String sql = "select personNAme.Firstname, personName.lastName,address.city,address.state,address.zip,address.phone from personName Inner join address On personName.ID = address.personID";
        Statement statement = null;

        try {

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()){
                System.out.println(
                        "first name = " + resultSet.getString(1) +
                                ", last name = " + resultSet.getString(2) +
                                ", city = " + resultSet.getString(3) +
                                ", state = " + resultSet.getString(4) +
                                ", zip = " + resultSet.getInt(5) +
                                ", phone  = " + resultSet.getBigDecimal(6)
                );
            }

        }
        catch (SQLException e ) {

            e.printStackTrace();

        }
        finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

