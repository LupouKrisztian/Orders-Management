package bll;

import dao.ClientDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Client;
import model.Orders;
import model.Product;

import java.util.List;

public class OrderBLL {
    private OrderDAO orderDAO;
    private ProductDAO productDAO;

    public OrderBLL() {
        orderDAO = new OrderDAO();
        productDAO = new ProductDAO();
    }

    /**
     * Retrieves all orders from the database.
     * @return A list containing all orders.
     */
    public List<Orders> getAllOrders() {
        return orderDAO.findAll();
    }

    /**
     * Retrieves an order by its ID from the database.
     * @param id The ID of the order to retrieve.
     * @return The order with the specified ID.
     */
    public Orders getOrderById(int id) {
        return orderDAO.findById(id);
    }

    /**
     * Adds a new order to the database.
     * @param order The order to be added.
     */
    public void addOrder(Orders order) {
        Product product = productDAO.findById(order.getIdProduct());

        orderDAO.insert(order);

        int newQuantity = product.getStock() - order.getQuantity();
        productDAO.updateStock(product.getId(), newQuantity);
    }

    /**
     * Deletes an order from the database by its ID.
     * @param id The ID of the order to be deleted.
     */
    public void deleteOrder(int id) {
        Orders order = orderDAO.findById(id);
        Product product = productDAO.findById(order.getIdProduct());

        orderDAO.delete(order.getId());

        int newQuantity = product.getStock() + order.getQuantity();
        productDAO.updateStock(product.getId(), newQuantity);
    }

    /**
     * Updates an existing order in the database.
     * @param newOrder  The updated order.
     * @param lastOrder The previous state of the order.
     */
    public void updateOrder(Orders newOrder, Orders lastOrder) {
        Product lastProduct = productDAO.findById(lastOrder.getIdProduct());
        int updatedQuantity = lastProduct.getStock() + lastOrder.getQuantity();
        productDAO.updateStock(lastProduct.getId(), updatedQuantity);

        orderDAO.update(newOrder);

        Product newProduct = productDAO.findById(newOrder.getIdProduct());
        int newQuantity = newProduct.getStock() - newOrder.getQuantity();
        productDAO.updateStock(newProduct.getId(), newQuantity);
    }
}