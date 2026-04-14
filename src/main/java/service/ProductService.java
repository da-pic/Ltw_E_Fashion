package service;

import dao.ProductDAO;
import dao.ProductVariantDAO;
import dao.CategoryDAO;
import dao.BrandDAO;
import model.Product;
import model.ProductVariant;
import java.util.*;

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
    
    public boolean updateProductWithVariantsFull(
            Product product,
            String[] varIds,
            String[] varColors,
            String[] varSizes,
            String[] varPrices,
            String[] varImportPrices,
            String[] varStocks,
            String[] varActives,
            List<String> images) {

        try {

            productDAO.updateProduct(product);

            for (int i = 0; i < varColors.length; i++) {

                String id = (varIds != null && i < varIds.length) ? varIds[i] : null;
                String color = varColors[i];
                String size = varSizes[i];

                int price = Integer.parseInt(varPrices[i]);

                int importPrice = (varImportPrices[i] == null || varImportPrices[i].isEmpty())
                        ? 0 : Integer.parseInt(varImportPrices[i]);

                int stock = Integer.parseInt(varStocks[i]);

                String image = (images != null && i < images.size()) ? images.get(i) : null;

                String imagePath = image;

                if (image != null && !image.startsWith("images/")) {
                    imagePath = "/images/" + image;
                }
                
                boolean active = true;

                if (varActives != null && i < varActives.length) {
                    active = "true".equals(varActives[i]);
                }

                if ((varActives == null || i >= varActives.length) && id != null && !id.isEmpty()) {
                    ProductVariant old = variantDAO.getProductVariantsById(id);
                    if (old != null) {
                        active = old.getIs_active();
                    }
                }

                ProductVariant variant = new ProductVariant(
                        (id == null || id.isEmpty()) ? UUID.randomUUID().toString() : id,
                        product.getId(),
                        color,
                        size,
                        imagePath,
                        importPrice,
                        price,
                        stock,
                        active
                );

                if (id == null || id.trim().isEmpty()) {
                    variantDAO.addProductVariants(variant);

                } else {
                    if (image == null) {
                        ProductVariant old = variantDAO.getProductVariantsById(id);
                        if (old != null) {
                            variant.setImage(old.getImage());
                        }
                    }

                    variantDAO.updateVariant(variant);
                }
            }

            return true;

        } catch (Exception e) { 
            e.printStackTrace();
            return false;
        }   
    }
    
    public boolean restoreProduct(String id) {
        return productDAO.restoreProduct(id);
    }
}