package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class ProductVariantDAO {

    public void decreaseStock(Connection conn, String variantId, int amount) throws Exception {
        String sql = "UPDATE product_variants " +
                "SET stock = stock - ? " +
                "WHERE id = ? AND stock >= ? AND is_active = 1";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, amount);
            ps.setString(2, variantId);
            ps.setInt(3, amount);

            int rows = ps.executeUpdate();
            if (rows == 0) {
                throw new Exception("Sản phẩm không đủ tồn kho hoặc đã ngừng bán.");
            }
        }
    }
}