package com.librarymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberManager {
    private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String username = "SYSTEM";
    private static final String password = "gnanasai";

    public static void addMember(String memberName, String phoneNumber, String street, String city, String state, String dues) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {


            String sequenceQuery = "SELECT member_id_sequence.nextval FROM dual";
            PreparedStatement sequenceStatement = connection.prepareStatement(sequenceQuery);
            ResultSet resultSet = sequenceStatement.executeQuery();
            resultSet.next();
            int memberId = resultSet.getInt(1);


            String query = "INSERT INTO Members (member_id, member_name, phone_number, street, city, state, dues) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, memberId);
            statement.setString(2, memberName);
            statement.setString(3, phoneNumber);
            statement.setString(4, street);
            statement.setString(5, city);
            statement.setString(6, state);
            statement.setString(7, dues);


            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Member added successfully!");
            } else {
                System.out.println("Failed to add member!");
            }

            resultSet.close();
            sequenceStatement.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
