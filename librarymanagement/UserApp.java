package com.librarymanagement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.List;
import java.sql.Date;

class Book {
    private int id;
    private String name;
    private int authorId;
    private int publisherId;
    private int quantity;

    public Book(int id, String name, int authorId, int publisherId, int quantity) {
        this.id = id;
        this.name = name;
        this.authorId = authorId;
        this.publisherId = publisherId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAuthorId() {
        return authorId;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public int getQuantity() {
        return quantity;
    }
}

public class UserApp extends JFrame implements ActionListener {
    private Connection connection;

    private JTextField borrowBookIdField;
    private JTextField borrowMemberIdField;

    private JTextField borrowBookIdField1;
    private JTextField borrowMemberIdField1;

    private JTextField bookNameField1;

    private JTextArea searchResultArea;

    public UserApp(Connection connection) {
        super("User App");
        this.connection = connection;

        borrowBookIdField = new JTextField(10);
        borrowMemberIdField = new JTextField(10);

        borrowBookIdField1 = new JTextField(10);
        borrowMemberIdField1 = new JTextField(10);

        bookNameField1 = new JTextField(20);

        searchResultArea = new JTextArea(10, 30);
        searchResultArea.setEditable(false);

        JButton borrowBookButton = new JButton("Borrow Book");
        JButton returnBookButton = new JButton("Return Book");
        JButton searchBookButton = new JButton("Search Book");
        JButton logoutButton = new JButton("Logout"); 
        

        borrowBookButton.addActionListener(this);
        returnBookButton.addActionListener(this);
        searchBookButton.addActionListener(this);
        logoutButton.addActionListener(this);

        JPanel borrowBookPanel = new JPanel(new FlowLayout());
        borrowBookPanel.add(new JLabel("Book ID:"));
        borrowBookPanel.add(borrowBookIdField);
        borrowBookPanel.add(new JLabel("Member ID:"));
        borrowBookPanel.add(borrowMemberIdField);
        borrowBookPanel.add(borrowBookButton);

        JPanel returnBookPanel = new JPanel(new FlowLayout());
        returnBookPanel.add(new JLabel("Book ID:"));
        returnBookPanel.add(borrowBookIdField1);
        returnBookPanel.add(new JLabel("Member ID:"));
        returnBookPanel.add(borrowMemberIdField1);
        returnBookPanel.add(returnBookButton);

        JPanel searchBookPanel = new JPanel(new FlowLayout());
        searchBookPanel.add(new JLabel("Book Name:"));
        searchBookPanel.add(bookNameField1);
        searchBookPanel.add(searchBookButton);
        searchBookPanel.add(new JScrollPane(searchResultArea));
        searchBookPanel.add(logoutButton);
        

        JPanel mainPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(borrowBookPanel);
        mainPanel.add(returnBookPanel);
        mainPanel.add(searchBookPanel);

        setContentPane(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    boolean temp;

    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Borrow Book")) {
            int bookId = Integer.parseInt(borrowBookIdField.getText());
            int memberId = Integer.parseInt(borrowMemberIdField.getText());
            java.util.Date currentDate = new java.util.Date();
            java.sql.Date checkOutDate = new java.sql.Date(currentDate.getTime());
            Date returnDate = new Date(checkOutDate.getTime() + (7 * 24 * 60 * 60 * 1000)); 

            temp = BorrowManager.borrowBook(bookId, memberId, checkOutDate, returnDate);
            if(temp){
                JOptionPane.showMessageDialog(this, "Book Borrowed successfully!");
            } else{
                JOptionPane.showMessageDialog(this, "Book not available to borrow!");
            } 
            
        } else if (actionCommand.equals("Return Book")) {
            int bookId = Integer.parseInt(borrowBookIdField1.getText());
            int memberId = Integer.parseInt(borrowMemberIdField1.getText());

            BorrowManager.returnBook(bookId, memberId);
            JOptionPane.showMessageDialog(this, "Book Returned successfully!");
        } else if (actionCommand.equals("Search Book")) {
            String bookName = bookNameField1.getText();

            List<Book> books = BookSearch.searchBook(bookName);

            if (books.isEmpty()) {
                searchResultArea.setText("No results found.");
            } else {
                StringBuilder resultText = new StringBuilder();
                for (Book book : books) {
                    resultText.append("Book ID: ").append(book.getId()).append(", ");
                    resultText.append("Book Name: ").append(book.getName()).append(", ");
                    resultText.append("Author ID: ").append(book.getAuthorId()).append(", ");
                    resultText.append("Publisher ID: ").append(book.getPublisherId()).append(", ");
                    resultText.append("Quantity: ").append(book.getQuantity()).append("\n");
                }
                searchResultArea.setText(resultText.toString());
        } 
        } else if (actionCommand.equals("Logout")) {
            dispose(); 

            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new App(connection);
                }
            });
        }
    }
}

