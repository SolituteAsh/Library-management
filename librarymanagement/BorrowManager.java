package com.librarymanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class BorrowManager {
    private static final String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String username = "SYSTEM";
    private static final String password = "gnanasai";

    public static boolean borrowBook(int bookId, int memberId, Date checkOutDate, Date returnDate) {
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            if (isBookAvailable(connection, bookId)) {
                updateBookQuantity(connection, bookId, -1);

                String query = "INSERT INTO Borrow (book_id, member_id, check_out_date, return_date, status) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(query)) {
                    statement.setInt(1, bookId);
                    statement.setInt(2, memberId);
                    statement.setDate(3, checkOutDate);
                    statement.setDate(4, returnDate);
                    statement.setString(5, "Not Returned");

                    int rowsInserted = statement.executeUpdate();
                    if (rowsInserted > 0) {
                        System.out.println("Book borrowed successfully!");
                        return true;
                    } else {
                        System.out.println("Failed to borrow book!");
                    }
                }
            } else {
                System.out.println("Book not available for borrowing!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void returnBook(int bookId, int memberId) {
    try (Connection connection = DriverManager.getConnection(url, username, password)) {
        updateBookQuantity(connection, bookId, 1);

        String selectQuery = "SELECT return_date FROM Borrow WHERE book_id = ? AND member_id = ?";
        try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {
            selectStatement.setInt(1, bookId);
            selectStatement.setInt(2, memberId);
            try (ResultSet resultSet = selectStatement.executeQuery()) {
                if (resultSet.next()) {
                    Date returnDate = resultSet.getDate("return_date");
                    Date currentDate = new Date(System.currentTimeMillis());

                    if (currentDate.after(returnDate)) {
                        System.out.println("Late fees applicable!");
                    }

                    String updateQuery = "UPDATE Borrow SET return_date = ?, status = ? WHERE book_id = ? AND member_id = ?";
                    try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                        updateStatement.setDate(1, currentDate);
                        updateStatement.setString(2, "Returned");
                        updateStatement.setInt(3, bookId);
                        updateStatement.setInt(4, memberId);

                        int rowsUpdated = updateStatement.executeUpdate();
                        if (rowsUpdated > 0) {
                            System.out.println("Book returned successfully!");
                        } else {
                            System.out.println("Failed to return book!");
                        }
                    }
                } else {
                    System.out.println("No matching borrow record found!");
                }
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    private static boolean isBookAvailable(Connection connection, int bookId) throws SQLException {
        String query = "SELECT qty FROM Books WHERE book_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, bookId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int quantity = resultSet.getInt("qty");
                    return quantity > 0;
                }
            }
        }
        return false;
    }

    private static void updateBookQuantity(Connection connection, int bookId, int quantityChange) throws SQLException {
        String query = "UPDATE Books SET qty = qty + ? WHERE book_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, quantityChange);
            statement.setInt(2, bookId);
            statement.executeUpdate();
        }
    }
}
