package presentation;

import bll.ClientBLL;
import connection.ConnectionFactory;
import model.Client;
import reflection.TableUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ClientView {
    private JFrame frame;
    private JPanel panel;
    private JLabel idLabel, nameLabel, addressLabel, emailLabel, ageLabel, resultLabel;
    private JTextField idTextField, nameTextField, addressTextField, emailTextField, ageTextField;
    private JButton addButton, updateButton, deleteButton, findButton, refreshButton, clearButton;
    private JTable table;
    private ClientBLL clientBLL;
    private TableUtil tableUtil;

    public ClientView() {
        frame = new JFrame("Client Window");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        idLabel = new JLabel("ID:");
        idTextField = new JTextField(50);
        nameLabel = new JLabel("Name:");
        nameTextField = new JTextField(50);
        ageLabel = new JLabel("Age:");
        ageTextField = new JTextField(50);
        addressLabel = new JLabel("Address:");
        addressTextField = new JTextField(50);
        emailLabel = new JLabel("Email:");
        emailTextField = new JTextField(50);
        addButton = new JButton("Add");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        findButton = new JButton("Find");
        refreshButton = new JButton("Refresh");
        clearButton = new JButton("Clear");
        resultLabel = new JLabel();

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(ageLabel, gbc);
        gbc.gridx = 1;
        panel.add(ageTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(addressLabel, gbc);
        gbc.gridx = 1;
        panel.add(addressTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(emailLabel, gbc);
        gbc.gridx = 1;
        panel.add(emailTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addButton, gbc);

        gbc.gridy = 6;
        panel.add(updateButton, gbc);

        gbc.gridy = 7;
        panel.add(deleteButton, gbc);

        gbc.gridy = 8;
        panel.add(findButton, gbc);

        gbc.gridy = 9;
        panel.add(refreshButton, gbc);

        gbc.gridy = 10;
        panel.add(clearButton, gbc);

        gbc.gridy = 11;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(resultLabel, gbc);

        frame.add(panel, BorderLayout.NORTH);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        clientBLL = new ClientBLL();
        tableUtil = new TableUtil();
        updateTableData();

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String idText = idTextField.getText().trim();
                    String ageText = ageTextField.getText().trim();

                    if (idText.isEmpty() || ageText.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "ID and age cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int id = Integer.parseInt(idText);
                    int age = Integer.parseInt(ageText);

                    String name = nameTextField.getText();
                    String address = addressTextField.getText();
                    String email = emailTextField.getText();

                    Client client = new Client(id, name, age, address, email);
                    ConnectionFactory con = new ConnectionFactory();
                    con.createConnection();
                    con.getConnection();
                    clientBLL.addClient(client);
                    updateTableData();
                    con.close();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "ID and age must be valid integers.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idTextField.getText().trim());

                    ConnectionFactory con = new ConnectionFactory();
                    con.createConnection();
                    con.getConnection();
                    Client client = clientBLL.getClientById(id);
                    if (client != null) {
                        resultLabel.setText("Found: " + client);
                    } else {
                        resultLabel.setText("Client not found");
                    }
                    con.close();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "ID must be a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idTextField.getText().trim());

                    ConnectionFactory con = new ConnectionFactory();
                    con.createConnection();
                    con.getConnection();
                    clientBLL.deleteClient(id);
                    updateTableData();
                    con.close();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "ID must be a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idTextField.getText().trim());
                    int age = Integer.parseInt(ageTextField.getText().trim());

                    String name = nameTextField.getText();
                    String address = addressTextField.getText();
                    String email = emailTextField.getText();

                    Client client = new Client(id, name, age, address, email);
                    ConnectionFactory con = new ConnectionFactory();
                    con.createConnection();
                    con.getConnection();
                    clientBLL.updateClient(client);
                    updateTableData();
                    con.close();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "ID and age must be valid integers.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTableData();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearTextFields();
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }

    private void updateTableData() {
        ConnectionFactory con = new ConnectionFactory();
        con.createConnection();
        con.getConnection();
        List<Client> clients = clientBLL.getAllClients();
        tableUtil.populateTable(table, clients);
        resultLabel.setText("");
        con.close();
    }

    private void clearTextFields() {
        idTextField.setText("");
        nameTextField.setText("");
        ageTextField.setText("");
        addressTextField.setText("");
        emailTextField.setText("");
        resultLabel.setText("");
    }
}