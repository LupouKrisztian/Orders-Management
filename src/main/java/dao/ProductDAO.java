package dao;

import connection.ConnectionFactory;
import model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static connection.ConnectionFactory.getConnection;

public class ProductDAO extends AbstractDAO<Product>{
    /**
     * Updates the stock value of a product in the database.
     * @param productId The ID of the product to update.
     * @param newStockValue The new stock value to set for the product.
     */
    public void updateStock(int productId, int newStockValue) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = getConnection();
            String query = "UPDATE product SET stock = ? WHERE id = ?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, newStockValue);
            statement.setInt(2, productId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}