package service;

import dao.OrderDAO;
import java.util.List;
import java.util.Map;

public class OrderService {

    private OrderDAO orderDAO = new OrderDAO();

    public List<Map<String, Object>> getOrderHistory(String userId) {
        if (userId == null || userId.trim().isEmpty()) {
            return null;
        }
        return orderDAO.getOrderHistory(userId);
    }

    public boolean cancelOrder(String orderId, String userId) {
        if (orderId == null || orderId.trim().isEmpty() || userId == null || userId.trim().isEmpty()) {
            return false;
        }
        return orderDAO.cancelOrder(orderId, userId);
    }
}