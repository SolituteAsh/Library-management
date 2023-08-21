package com.librarymanagement;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class AdminApp extends JFrame implements ActionListener {
    private Connection connection; 

    private JTextField bookNameField;
    private JTextField authorIdField;
    private JTextField publisherIdField;
    private JTextField quantityField;

    private JTextField memberNameField;
    private JTextField phoneNumberField;
    private JTextField streetField;
    private JTextField cityField;
    private JTextField stateField;
    private JCheckBox duesCheckbox;

    public AdminApp(Connection connection) {
        super("Admin App");
        this.connection = connection;


        bookNameField = new JTextField(20);
        authorIdField = new JTextField(10);
        publisherIdField = new JTextField(10);
        quantityField = new JTextField(5);

        memberNameField = new JTextField(20);
        phoneNumberField = new JTextField(10);
        streetField = new JTextField(20);
        cityField = new JTextField(15);
        stateField = new JTextField(15);
        duesCheckbox = new JCheckBox("Dues");

        JButton addBookButton = new JButton("Add Book");
        JButton addMemberButton = new JButton("Add Member");
        JButton logoutButton = new JButton("Logout"); 
        

        addBookButton.addActionListener(this);
        addMemberButton.addActionListener(this);
        logoutButton.addActionListener(this);

        JPanel addBookPanel = new JPanel(new FlowLayout());
        addBookPanel.add(new JLabel("Book Name:"));
        addBookPanel.add(bookNameField);
        addBookPanel.add(new JLabel("Author ID:"));
        addBookPanel.add(authorIdField);
        addBookPanel.add(new JLabel("Publisher ID:"));
        addBookPanel.add(publisherIdField);
        addBookPanel.add(new JLabel("Quantity:"));
        addBookPanel.add(quantityField);
        addBookPanel.add(addBookButton);

        JPanel addMemberPanel = new JPanel(new FlowLayout());
        addMemberPanel.add(new JLabel("Member Name:"));
        addMemberPanel.add(memberNameField);
        addMemberPanel.add(new JLabel("Phone Number:"));
        addMemberPanel.add(phoneNumberField);
        addMemberPanel.add(new JLabel("Street:"));
        addMemberPanel.add(streetField);
        addMemberPanel.add(new JLabel("City:"));
        addMemberPanel.add(cityField);
        addMemberPanel.add(new JLabel("State:"));
        addMemberPanel.add(stateField);
        addMemberPanel.add(duesCheckbox);
        addMemberPanel.add(addMemberButton);
        addMemberPanel.add(logoutButton);


        JPanel mainPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(addBookPanel);
        mainPanel.add(addMemberPanel);

        setContentPane(mainPanel);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals("Add Book")) {
            String bookName = bookNameField.getText();
            int authorId = Integer.parseInt(authorIdField.getText());
            int publisherId = Integer.parseInt(publisherIdField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            BookManager.addBook(bookName, authorId, publisherId, quantity);
            JOptionPane.showMessageDialog(this, "Book added successfully!");
        } else if (actionCommand.equals("Add Member")) {
            String memberName = memberNameField.getText();
            String phoneNumber = phoneNumberField.getText();
            String street = streetField.getText();
            String city = cityField.getText();
            String state = stateField.getText();
            String dues = duesCheckbox.isSelected() ? "yes" : "no";

            MemberManager.addMember(memberName, phoneNumber, street, city, state, dues);
            JOptionPane.showMessageDialog(this, "Member added successfully!");
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
