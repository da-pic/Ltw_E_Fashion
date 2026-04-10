package service;

import java.util.UUID;

import org.mindrot.jbcrypt.BCrypt;

import dao.UserDAO;
import model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public User login(String username, String plainPassword) {
        User user = userDAO.findByUsername(username);
        if (user != null && BCrypt.checkpw(plainPassword, user.getPasswordHash())) {
            if (!user.isActive()) {
                throw new RuntimeException("Tài khoản đã bị khóa!");
            }
            return user;
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

    public boolean checkRoleAdmin(String userId) {

        return userDAO.isAdmin(userId); 
    }

    public java.util.List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public boolean updateUserStatus(String userId, boolean status) {
        return userDAO.updateUserStatus(userId, status);
    }
}