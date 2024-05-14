package bll;

import dao.ProductDAO;
import model.Product;

import java.util.List;

public class ProductBLL {
    private ProductDAO productDAO;

    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    /**
     * Retrieves all products from the database.
     * @return A list containing all products.
     */
    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }

    /**
     * Retrieves a product by its ID from the database.
     * @param id The ID of the product to retrieve.
     * @return The product with the specified ID.
     */
    public Product getProductById(int id) { return productDAO.findById(id); }

    /**
     * Adds a new product to the database.
     * @param product The product to be added.
     * @return The added product.
     */
    public Product addProduct(Product product) { return productDAO.insert(product); }

    /**
     * Updates an existing product in the database.
     * @param product The updated product.
     */
    public void updateProduct(Product product) {
        productDAO.update(product);
    }

    /**
     * Deletes a product from the database by its ID.
     * @param id The ID of the product to be deleted.
     */
    public void deleteProduct(int id) {
        productDAO.delete(id);
    }
}