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

        // 1. Validate dữ liệu
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
}