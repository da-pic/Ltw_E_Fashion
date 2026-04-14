package service;

import dao.UserDAO;
import java.util.List;
import model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;

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
    
    public List<User> getAllUsers() {
        List<User> users = userDAO.getAllUsers();

        for (User u : users) {
            String role = userDAO.getUserRoleByID(u.getId());
            u.setRole(role);
        }

        return users;
    }
    
    public String getRole(String userId) {
        return userDAO.getUserRoleByID(userId);
    }
    
    public User getUserByID(String userId) {
        return userDAO.findByUserID(userId);
    }
    
    public boolean toggleUserStatus(String userId, boolean currentStatus) {
        boolean newStatus = !currentStatus;
        return userDAO.updateUserStatus(userId, newStatus);
    }
    
    public boolean updateUser(User user) {
        return userDAO.updateUser(user);
    }
    
    public boolean updateUserStatus(String userId, boolean status) {
        return userDAO.updateUserStatus(userId, status);
    }
    
    public boolean registerUserWithRole(User newUser, String plainPassword, String roleName) {
        boolean isRegistered = this.register(newUser, plainPassword);
        
        if (isRegistered) {
            return userDAO.assignRoleAndEmployee(newUser.getId(), roleName);
        }
        return false;
    }
    
    
}