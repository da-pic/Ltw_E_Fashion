package dao;

import java.sql.*;
import java.util.*;
import model.Supplier;
import util.DatabaseConnection;

public class SupplierDAO {

    public List<Supplier> getAllSuppliers() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT * FROM supplier";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Supplier s = new Supplier(
                        rs.getString("id"),
                        rs.getString("supplier_name")
                );
                list.add(s);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    public String addNewSupplier(String supplierName) {
        String sql = "INSERT INTO supplier (id, name) VALUES (?, ?)";

        String uuid = java.util.UUID.randomUUID().toString();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, uuid);
            stmt.setString(2, supplierName);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                return uuid;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}