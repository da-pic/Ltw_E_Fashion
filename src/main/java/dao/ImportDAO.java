package dao;

import java.sql.*;
import java.util.*;
import util.DatabaseConnection;
import model.ImportItem;

public class ImportDAO {

    public boolean createImportInvoice(String importID, String supplierID, int totalPrice) {

        String sql = "INSERT INTO import_invoice (id, supplier_id, total_price) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, importID);
            stmt.setString(2, supplierID);
            stmt.setInt(3, totalPrice);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<String> getAllImportInvoice() {

        List<String> list = new ArrayList<>();
        String sql = "SELECT id FROM import_invoice ORDER BY created_at DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                list.add(rs.getString("id"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    
    // Thêm item vào phiếu nhập
    public boolean addImportItem(ImportItem item) {

        String sql = "INSERT INTO import_items (import_id, product_variant_id, unit_price, amount) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, item.getImportID());
            stmt.setString(2, item.getProductVariantID());
            stmt.setInt(3, item.getUnitPrice());
            stmt.setInt(4, item.getAmount());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Lấy danh sách item theo importID
    public List<ImportItem> getImportItemsByImportID(String importID) {

        List<ImportItem> list = new ArrayList<>();

        String sql = "SELECT ii.import_id, ii.product_variant_id, ii.unit_price, ii.amount, " +
                     "p.product_name, pv.image, pv.color, pv.size, s.id AS supplier_id " +
                     "FROM import_items ii " +
                     "JOIN product_variants pv ON ii.product_variant_id = pv.id " +
                     "JOIN product p ON pv.product_id = p.id " +
                     "LEFT JOIN product pr ON pv.product_id = pr.id " +
                     "LEFT JOIN supplier s ON pr.supplier_id = s.id " +
                     "WHERE ii.import_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, importID);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ImportItem item = new ImportItem(
                    rs.getString("import_id"),
                    rs.getString("product_variant_id"),
                    rs.getString("product_name"),
                    rs.getString("supplier_id"),
                    rs.getString("image"),
                    rs.getString("size"),
                    rs.getString("color"),
                    rs.getInt("unit_price"),
                    rs.getInt("amount")
                );

                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // Cập nhật item (giá / số lượng)
    public boolean updateImportItem(ImportItem item) {

        String sql = "UPDATE import_items SET unit_price = ?, amount = ? " +
                         "WHERE import_id = ? AND product_variant_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, item.getUnitPrice());
            stmt.setInt(2, item.getAmount());
            stmt.setString(3, item.getImportID());
            stmt.setString(4, item.getProductVariantID());

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // Xóa item khỏi phiếu nhập
    public boolean deleteImportItem(String importID, String productVariantID) {

        String sql = "DELETE FROM import_items WHERE import_id = ? AND product_variant_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, importID);
            stmt.setString(2, productVariantID);

            return stmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}