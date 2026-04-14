package service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import dao.BrandDAO;
import dao.CategoryDAO;
import dao.ProductDAO;
import dao.ProductVariantDAO;
import model.Product;
import model.ProductVariant;

public class ProductService {

    private ProductDAO productDAO = new ProductDAO();
    private ProductVariantDAO variantDAO = new ProductVariantDAO();
    private BrandDAO brandDAO = new BrandDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    public List<Product> getAllProduct() {
        List<Product> list = productDAO.getAllProduct();

        for (Product p : list) {
            p.setBrandName(brandDAO.getBrandNameByID(p.getBrand_id()));
            p.setCategory(categoryDAO.getFullCategory(p.getCategory_id()));
        }

        return list;
    }
    public Product getProductDetail(String productId) {
        Product product = productDAO.getProductById(productId);

        if (product != null) {
            List<ProductVariant> variants = variantDAO.getProductVariantsByProductId(productId);
            product.setProductVariants(variants);

            // 🔥 thêm luôn brand + category cho detail
            product.setBrandName(brandDAO.getBrandNameByID(product.getBrand_id()));
            product.setCategory(categoryDAO.getFullCategory(product.getCategory_id()));
        }

        return product;
    }
    
    
    public boolean addProduct(Product product) {

        if (product == null) return false;

        if (product.getId() == null || product.getId().trim().isEmpty()) {
            return false;
        }

        if (product.getProduct_name() == null || product.getProduct_name().trim().isEmpty()) {
            return false;
        }

        if (product.getBrand_id() == null || product.getBrand_id().trim().isEmpty()) {
            return false;
        }

        if (product.getSupplierID() == null || product.getSupplierID().trim().isEmpty()) {
            return false;
        }

        if (product.getCategory_id() <= 0) {
            return false;
        }

        if (productDAO.getProductById(product.getId()) != null) {
            return false;
        }

        return productDAO.addProduct(product);
    }
    
    
    public void deleteProduct(String productID){
        ProductDAO productDAO = new ProductDAO();
        this.productDAO.deleteProduct(productID);
    }
    
    public boolean updateProductWithVariants(Product product, String[] varIds, String[] varColors, String[] varSizes, String[] varPrices, String[] varStocks, String[] varImages) {
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
                
                String image = (varImages != null && i < varImages.length) ? varImages[i] : null;

                if (vId == null || vId.trim().isEmpty()) {
                    vId = "v-" + UUID.randomUUID().toString().substring(0, 8);
                    ProductVariant newV = new ProductVariant(vId, product.getId(), color, size, image, price, stock, true);
                    variantDAO.addProductVariants(newV);
                } else {
                    ProductVariant updateV = new ProductVariant(vId, product.getId(), color, size, image, price, stock, true);
                    variantDAO.updateVariant(updateV);
                    submittedIds.add(vId); 
                }
            }
        }

        if (oldVariants != null) {
            for (ProductVariant oldV : oldVariants) {
                if (!submittedIds.contains(oldV.getId())) {
                    variantDAO.deleteProductVariant(oldV.getId());
                }
            }
        }
        
        return true;
    }
    
    public boolean addProductWithVariants(Product product, String[] varColors, String[] varSizes, String[] varPrices, String[] varStocks, String[] varImages) {
        
        boolean isProductAdded = addProduct(product);
        if (!isProductAdded) return false; // Nếu lưu sản phẩm gốc lỗi (VD trùng ID) thì dừng luôn

        if (varColors != null) {
            for (int i = 0; i < varColors.length; i++) {
                String vId = "v-" + UUID.randomUUID().toString().substring(0, 8);
                String color = varColors[i];
                String size = varSizes[i];
                int price = (varPrices[i] != null && !varPrices[i].isEmpty()) ? (int) Double.parseDouble(varPrices[i]) : 0;
                int stock = (varStocks[i] != null && !varStocks[i].isEmpty()) ? Integer.parseInt(varStocks[i]) : 0;
                
                String image = (varImages != null && i < varImages.length) ? varImages[i] : null;

                ProductVariant newV = new ProductVariant(vId, product.getId(), color, size, image, price, stock, true);
                variantDAO.addProductVariants(newV);
            }
        }
        return true;
    }

    public boolean restoreProduct(String id) {
        return productDAO.restoreProduct(id);
    }
}