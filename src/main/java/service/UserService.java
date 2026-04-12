package service;

import dao.UserDAO;
import model.User;
import org.mindrot.jbcrypt.BCrypt;
import java.util.UUID;

public class UserService {
    private UserDAO userDAO = new UserDAO();

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

    public boolean register(User newUser, String plainPassword) {
        if (userDAO.findByUsername(newUser.getUsername()) != null) {
            return false;
        }
        newUser.setId(UUID.randomUUID().toString());
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        newUser.setPasswordHash(hashedPassword);
        
        return userDAO.save(newUser);
    }
    
    public boolean updateInformationByUserId(User user){
        return userDAO.updateInformationByUserId(user);
    }
}