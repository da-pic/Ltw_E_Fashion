package service;

import dao.ProductDAO;
import dao.ProductVariantDAO;
import model.Product;
import model.ProductVariant;
import java.util.*;

/**
 *
 * @author Chinh
 */
public class ProductService {
    
    private ProductDAO productDAO = new ProductDAO();
    private ProductVariantDAO variantDAO = new ProductVariantDAO();
    private dao.CategoryDAO categoryDAO = new dao.CategoryDAO();
    
    //Lấy tất cả sản phẩm
    public List<Product> getAllProduct() {
        return productDAO.getAllProduct();
    }
    
    //Lấy danh sách phân loại cho sản phẩm
    public Product getProductDetail(String productId) {
        Product product = productDAO.getProductById(productId);
        
        if (product != null) {
            List<ProductVariant> variants = variantDAO.getProductVariantsByProductId(productId);
            product.setProductVariants(variants);
        }
        
        return product;
    }
    
    //Lấy danh sách sản phẩm đề xuất
    public List<Product> getProductsForHomeDisplay(String userId) {
        return productDAO.getAllProductsSortedByPreference(userId);
    }

    //Ghi log cho view
    public void logInteraction(String userId, String productId, String type) {
        productDAO.logUserInteraction(userId, productId, type);
    }
    
    //Tìm kiếm sản phẩm
    public List<Product> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>(); 
        }
        return productDAO.searchProducts(keyword.trim());
    }
    
    //Lấy danh sách danh mục
    public List<model.Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }
    
    //lấy sản phẩm theo id danh mục
    public List<Product> getProductsByCategory(int categoryId) {
        return productDAO.getProductsByCategory(categoryId);
    }
    
    //Ghi log cho search
    public void logSearchInteractions(String userId, List<Product> products) {
        productDAO.logSearchInteractionsBatch(userId, products);
    }
    
    //Lấy top sản phẩm đc tìm kiếm nhiều nhất
    public List<Product> getTopSearchedProducts() {
        return productDAO.getTopSearchedProducts();
    }
    
    //Lấy top sản phẩm áp đc voucher có % giảm nhiều nhất
    public List<Product> getFlashSaleProducts() {
        return productDAO.getFlashSaleProducts();
    }
}