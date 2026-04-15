package service;

import dao.UserDAO;
import model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    // Đăng nhập
    public User login(String username, String plainPassword) {
        User user = userDAO.findByUsername(username);
        if (user != null && BCrypt.checkpw(plainPassword, user.getPasswordHash())) {
            if (!user.isactive()) {
                throw new RuntimeException("Tài khoản đã bị khóa!");
            }
            return user;
        }
        return null; 
    }
    
    // Đăng ký với logic Validate
    public boolean register(User newUser, String plainPassword) {
        if (newUser.getName() == null || newUser.getName().length() >= 36) return false;
        if (newUser.getUsername() == null || newUser.getUsername().length() >= 36) return false;
        if (plainPassword == null || plainPassword.length() >= 36) return false;

        String phonePattern = "^[0-9]{10,11}$";
        if (newUser.getPhonenumber() == null || !newUser.getPhonenumber().matches(phonePattern)) {
            return false;
        }

        if (userDAO.findByUsername(newUser.getUsername()) != null) {
            return false;
        }

        newUser.setId(UUID.randomUUID().toString());
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        newUser.setPasswordHash(hashedPassword);

        try {
            boolean isSaved = userDAO.save(newUser);
            if (isSaved) {
                userDAO.insertUserRole(newUser.getId(), "ROLE_CUSTOMER");
                newUser.setRole("customer"); 
                return true;
            }
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
        return false;
    }
    
    public boolean updateInformationByUserId(User user){
        return userDAO.updateInformationByUserId(user);
    }
}