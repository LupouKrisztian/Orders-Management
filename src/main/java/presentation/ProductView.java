package presentation;

import bll.ProductBLL;
import connection.ConnectionFactory;
import model.Product;
import reflection.TableUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ProductView {
    private JFrame frame;
    private JPanel panel;
    private JLabel idLabel, nameLabel, priceLabel, quantityLabel, resultLabel;
    private JTextField idTextField, nameTextField, priceTextField, quantityTextField;
    private JButton addButton, updateButton, deleteButton, findButton, refreshButton, clearButton;
    private JTable table;
    private ProductBLL productBLL;
    private TableUtil tableUtil;

    public ProductView() {
        frame = new JFrame("Product Window");
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
        priceLabel = new JLabel("Price:");
        priceTextField = new JTextField(50);
        quantityLabel = new JLabel("Stock:");
        quantityTextField = new JTextField(50);
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
        panel.add(priceLabel, gbc);
        gbc.gridx = 1;
        panel.add(priceTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(quantityLabel, gbc);
        gbc.gridx = 1;
        panel.add(quantityTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(addButton, gbc);

        gbc.gridy = 5;
        panel.add(updateButton, gbc);

        gbc.gridy = 6;
        panel.add(deleteButton, gbc);

        gbc.gridy = 7;
        panel.add(findButton, gbc);

        gbc.gridy = 8;
        panel.add(refreshButton, gbc);

        gbc.gridy = 9;
        panel.add(clearButton, gbc);

        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(resultLabel, gbc);

        frame.add(panel, BorderLayout.NORTH);

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        productBLL = new ProductBLL();
        tableUtil = new TableUtil();
        updateTableData();

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idTextField.getText().trim());
                    String name = nameTextField.getText();
                    int stock = Integer.parseInt(quantityTextField.getText().trim());
                    int price = Integer.parseInt(priceTextField.getText().trim());
                    Product product = new Product(id, name, stock, price);
                    ConnectionFactory con = new ConnectionFactory();
                    con.createConnection();
                    con.getConnection();
                    productBLL.addProduct(product);
                    updateTableData();
                    con.close();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "ID, stock, and price must be valid integers.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idTextField.getText().trim());
                    String name = nameTextField.getText();
                    int stock = Integer.parseInt(quantityTextField.getText().trim());
                    int price = Integer.parseInt(priceTextField.getText().trim());
                    Product product = new Product(id, name, stock, price);
                    ConnectionFactory con = new ConnectionFactory();
                    con.createConnection();
                    con.getConnection();
                    productBLL.updateProduct(product);
                    updateTableData();
                    con.close();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "ID, stock, and price must be valid integers.", "Error", JOptionPane.ERROR_MESSAGE);
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
                    productBLL.deleteProduct(id);
                    updateTableData();
                    con.close();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "ID must be a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
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
                    Product product = productBLL.getProductById(id);
                    if(product != null) {
                        resultLabel.setText("Found: " + product);
                    } else {
                        resultLabel.setText("Product not found");
                    }
                    con.close();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "ID must be a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
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
        List<Product> products = productBLL.getAllProducts();
        tableUtil.populateTable(table, products);
        resultLabel.setText("");
        con.close();
    }

    private void clearTextFields() {
        idTextField.setText("");
        nameTextField.setText("");
        priceTextField.setText("");
        quantityTextField.setText("");
        resultLabel.setText("");
    }
}