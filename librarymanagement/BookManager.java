package com.librarymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BookManager {
    public static void addBook(String bookName, int authorId, int publisherId, int qty) {
    String url = "jdbc:oracle:thin:@localhost:1521:xe";
    String username = "SYSTEM";
    String password = "gnanasai";

    try (Connection connection = DriverManager.getConnection(url, username, password)) {
        PreparedStatement statement = null;

        try {
            String query = "INSERT INTO Books (book_id, book_name, author_id, publisher_id, qty) VALUES (?, ?, ?, ?, ?)";
            statement = connection.prepareStatement(query);
            

            Statement sequenceStatement = connection.createStatement();
            ResultSet sequenceResultSet = sequenceStatement.executeQuery("SELECT book_id_sequence.NEXTVAL FROM DUAL");
            sequenceResultSet.next();
            int bookId = sequenceResultSet.getInt(1);
            

            statement.setInt(1, bookId);
            statement.setString(2, bookName);
            statement.setInt(3, authorId);
            statement.setInt(4, publisherId);
            statement.setInt(5, qty);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Book added successfully!");
            } else {
                System.out.println("Failed to add book!");
            }
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }      
    } catch (SQLException e) {
        e.printStackTrace();
    } 
}

}
