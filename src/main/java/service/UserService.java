package service;

import dao.UserDAO;
import model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;

public class UserService {
    private UserDAO userDAO = new UserDAO();

   public User login(String username, String plainPassword) {
    User user = userDAO.findByUsername(username);
    if (user == null) return null;

    String hash = user.getPasswordHash();
    if (hash == null) return null;

    // ✅ Đổi $2y$ → $2a$ để tương thích jBCrypt
    if (hash.startsWith("$2y$")) {
        hash = "$2a$" + hash.substring(4);
    }

    // Nếu password trong DB không phải bcrypt
    if (!hash.startsWith("$2")) {
        return plainPassword.equals(hash) ? user : null;
    }

    // Nếu là bcrypt
    try {
        if (BCrypt.checkpw(plainPassword, hash)) {
            if (!user.isActive()) {
                throw new RuntimeException("Tài khoản đã bị khóa!");
            }
            return user;
        }
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }

    return null;
}
    public boolean register(User newUser, String plainPassword) {
        if (userDAO.findByUsername(newUser.getUsername()) != null) {
            return false;
        }
        newUser.setId(UUID.randomUUID().toString());
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        newUser.setPasswordHash(hashedPassword);
        
        return userDAO.save(newUser);
    }
}