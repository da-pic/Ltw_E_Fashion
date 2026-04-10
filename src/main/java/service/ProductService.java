package service;
import dao.ProductDAO;
import dao.ProductVariantDAO;
import model.Product;
import model.ProductVariant;
import java.util.*;

public class ProductService {
    private ProductDAO productDAO = new ProductDAO();
    private ProductVariantDAO variantDAO = new ProductVariantDAO();
    
    public List<Product> getAllProduct(){
        return productDAO.getAllProduct();
    }
    
    public Product getProductDetail(String productId) {
        Product product = productDAO.getProductById(productId);
        if (product != null) {
            List<ProductVariant> variants = variantDAO.getProductVariantsByProductId(productId);
            product.setProductVariants(variants);
        }
        
        return product;
    }
}
