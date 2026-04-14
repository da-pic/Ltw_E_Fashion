package util;
import org.mindrot.jbcrypt.BCrypt;

public class PasswordBCrypt {

    // Hash password
    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(10));
    }

    // Check password
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

    // Test
    public static void main(String[] args) {

        String password = "123"; // 👉 đổi password tại đây

        // Mã hóa
        String hashed = hashPassword(password);

        System.out.println("Password gốc: " + password);
        System.out.println("Hash BCrypt: " + hashed);

        // Test verify
        boolean isMatch = checkPassword(password, hashed);
        System.out.println("Verify đúng không: " + isMatch);
    }
}