package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuView {
    private JFrame frame;
    private JPanel panel;
    private JButton clientWin, productWin, orderWin;
    private JLabel options;

    public MenuView() {
        frame = new JFrame("Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new BorderLayout());

        panel = new JPanel(new GridLayout(4, 1));
        options = new JLabel("Options");
        options.setHorizontalAlignment(SwingConstants.CENTER);
        clientWin = new JButton("Client Window");
        productWin = new JButton("Product Window");
        orderWin = new JButton("Order Window");

        panel.add(options);
        panel.add(clientWin);
        panel.add(productWin);
        panel.add(orderWin);

        frame.add(panel);

        clientWin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ClientView clientWindow = new ClientView();
                clientWindow.show();
            }
        });

        productWin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ProductView productWindow = new ProductView();
                productWindow.show();
            }
        });

        orderWin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                OrderView orderWindow = new OrderView();
                orderWindow.show();
            }
        });
    }

    public void show() {
        frame.setVisible(true);
    }
}