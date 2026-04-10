package service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import dao.ProductDAO;
import dao.ProductVariantDAO;
import model.Product;
import model.ProductVariant;
/**
 *
 * @author Chinh
 */
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

    public boolean addProduct(Product product) {
        return productDAO.insertProduct(product);
    }

    public boolean updateProduct(Product product) {
        return productDAO.updateProduct(product);
    }

    public boolean deleteProduct(String id) {
        return productDAO.deleteProduct(id);
    }

    public boolean softDeleteProduct(String id) {
        return productDAO.softDeleteProduct(id);
    }

    public boolean restoreProduct(String id) {
        return productDAO.restoreProduct(id);
    }

    public boolean updateProductWithVariants(Product product, String[] varIds, String[] varColors, String[] varSizes, String[] varPrices, String[] varStocks) {
        boolean pSuccess = productDAO.updateProduct(product);
        if (!pSuccess) return false;

        List<ProductVariant> oldVariants = variantDAO.getProductVariantsByProductId(product.getId());
        Set<String> submittedIds = new HashSet<>();

        if (varIds != null) {
            for (int i = 0; i < varIds.length; i++) {
                String vId = varIds[i];
                String color = varColors[i];
                String size = varSizes[i];
                int price = (varPrices[i] != null && !varPrices[i].isEmpty()) ? (int) Double.parseDouble(varPrices[i]) : 0;
                int stock = (varStocks[i] != null && !varStocks[i].isEmpty()) ? Integer.parseInt(varStocks[i]) : 0;

                if (vId == null || vId.trim().isEmpty()) {
                    vId = "v-" + UUID.randomUUID().toString().substring(0, 8);
                    ProductVariant newV = new ProductVariant(vId, product.getId(), color, size, null, price, stock, true);
                    variantDAO.insertVariant(newV);
                } else {
                    ProductVariant updateV = new ProductVariant(vId, product.getId(), color, size, null, price, stock, true);
                    variantDAO.updateVariant(updateV);
                    submittedIds.add(vId); 
                }
            }
        }

        if (oldVariants != null) {
            for (ProductVariant oldV : oldVariants) {
                if (!submittedIds.contains(oldV.getId())) {
                    variantDAO.deleteVariant(oldV.getId());
                }
            }
        }
        
        return true;
    }
}
