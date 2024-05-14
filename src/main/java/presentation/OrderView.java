package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import connection.ConnectionFactory;
import model.Client;
import model.Orders;
import model.Product;
import reflection.TableUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class OrderView {
    private JFrame frame;
    private JPanel panel;
    private JLabel idLabel, clientIdLabel, productIdLabel, quantityLabel, underStockLabel, resultLabel;
    private JTextField idTextField, clientIdTextField, productIdTextField, quantityTextField;
    private JButton addButton, updateButton, deleteButton, findButton, refreshButton, clearButton;
    private OrderBLL orderBLL;
    private ProductBLL productBLL;
    private ClientBLL clientBLL;
    private TableUtil tableUtil;
    private JTable table;

    public OrderView() {
        frame = new JFrame("Order Window");
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
        clientIdLabel = new JLabel("Client ID:");
        clientIdTextField = new JTextField(50);
        productIdLabel = new JLabel("Product ID:");
        productIdTextField = new JTextField(50);
        quantityLabel = new JLabel("Quantity:");
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
        panel.add(clientIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(clientIdTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(productIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(productIdTextField, gbc);

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

        orderBLL = new OrderBLL();
        productBLL = new ProductBLL();
        clientBLL = new ClientBLL();
        tableUtil = new TableUtil();
        updateTableData();

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idTextField.getText());
                    int clientID = Integer.parseInt(clientIdTextField.getText());
                    int productID = Integer.parseInt(productIdTextField.getText());
                    int quantity = Integer.parseInt(quantityTextField.getText());

                    if (quantity < 0) {
                        JOptionPane.showMessageDialog(frame, "Quantity must be a non-negative integer.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    ConnectionFactory con = new ConnectionFactory();
                    con.createConnection();
                    con.getConnection();

                    Product product = productBLL.getProductById(productID);
                    Client client = clientBLL.getClientById(clientID);

                    if (product == null) {
                        resultLabel.setText("Product with id = " + productID + " not found");
                    } else {
                        if (client == null) {
                            resultLabel.setText("Client with id = " + clientID + " not found");
                        } else {
                            if (product.getStock() < quantity) {
                                resultLabel.setText("There are not enough products in stock");
                            } else {
                                con.createConnection();
                                con.getConnection();
                                Orders order = new Orders(id, clientID, productID, quantity);
                                orderBLL.addOrder(order);
                                updateTableData();
                            }
                        }
                    }
                    con.close();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Quantity must be a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idTextField.getText());
                    ConnectionFactory con = new ConnectionFactory();
                    con.createConnection();
                    con.getConnection();
                    Orders order = orderBLL.getOrderById(id);
                    if (order != null) {
                        resultLabel.setText("Found: " + order);
                    } else {
                        resultLabel.setText("Order not found");
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
                    int id = Integer.parseInt(idTextField.getText());
                    ConnectionFactory con = new ConnectionFactory();
                    con.createConnection();
                    con.getConnection();
                    orderBLL.deleteOrder(id);
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
                    int id = Integer.parseInt(idTextField.getText());
                    int clientID = Integer.parseInt(clientIdTextField.getText());
                    int productID = Integer.parseInt(productIdTextField.getText());
                    int quantity = Integer.parseInt(quantityTextField.getText());

                    if (quantity < 0) {
                        JOptionPane.showMessageDialog(frame, "Quantity must be a non-negative integer.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    ConnectionFactory con = new ConnectionFactory();
                    con.createConnection();
                    con.getConnection();

                    Orders lastOrder = orderBLL.getOrderById(id);
                    Product product = productBLL.getProductById(productID);
                    Client client = clientBLL.getClientById(clientID);

                    if (product == null) {
                        resultLabel.setText("Product with id = " + productID + " not found");
                    } else {
                        if (client == null) {
                            resultLabel.setText("Client with id = " + clientID + " not found");
                        } else {
                            if (product.getStock() >= quantity && client != null && product != null) {
                                con.createConnection();
                                con.getConnection();
                                Orders newOrder = new Orders(id, clientID, productID, quantity);
                                orderBLL.updateOrder(newOrder, lastOrder);
                                updateTableData();
                            }
                        }
                    }
                    con.close();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Quantity must be a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
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
        List<Orders> orders = orderBLL.getAllOrders();
        tableUtil.populateTable(table, orders);
        resultLabel.setText("");
        con.close();
    }

    private void clearTextFields() {
        idTextField.setText("");
        clientIdTextField.setText("");
        productIdTextField.setText("");
        quantityTextField.setText("");
        resultLabel.setText("");
    }
}