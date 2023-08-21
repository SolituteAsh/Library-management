package com.librarymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookSearch {
    public static List<Book> searchBook(String bookName) {
    String url = "jdbc:oracle:thin:@localhost:1521:xe";
    String username = "SYSTEM";
    String password = "gnanasai";

    try (Connection connection = DriverManager.getConnection(url, username, password)) {
        String query = "SELECT * FROM books WHERE Book_name = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, bookName);

            try (ResultSet resultSet = statement.executeQuery()) {
                List<Book> books = new ArrayList<>();

                System.out.println("Searching for books...");

                while (resultSet.next()) {
                    int id = resultSet.getInt("Book_id");
                    String name = resultSet.getString("Book_name");
                    int authorId = resultSet.getInt("Author_id");
                    int publisherId = resultSet.getInt("Publisher_id");
                    int quantity = resultSet.getInt("qty");

                    Book book = new Book(id, name, authorId, publisherId, quantity);
                    books.add(book);
                }

                System.out.println("Search complete.");

                return books;
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    return new ArrayList<>(); 
}

}
