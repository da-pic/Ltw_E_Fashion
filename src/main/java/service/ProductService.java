package service;
 
import dao.ProductDAO;
import model.Product;
import java.util.List;
 
public class ProductService {
 
    private final ProductDAO productDAO = new ProductDAO();
 
    public List<Product> getAllProducts() {
        return productDAO.getAllProducts();
    }
 
    public Product getProductById(String id) {
        return productDAO.getProductById(id);
    }
    
    public boolean addProduct(Product product) {
        if(product.getId() == null || product.getId().trim().isEmpty()) return false;
        if(product.getProductName() == null || product.getProductName().trim().isEmpty()) return false;
        return productDAO.addProduct(product);
    }
 
    public boolean updateProduct(Product product) {
        if (product.getProductName() == null || product.getProductName().trim().isEmpty()) {
            return false;
        }
        return productDAO.updateProduct(product);
    }
 
    public boolean deleteProduct(String id) {
        if (id == null || id.trim().isEmpty()) {
            return false;
        }
        return productDAO.deleteProduct(id);
    }
}
 