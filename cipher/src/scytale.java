package cipher;
import java.util.Random;

public class scytale {

    public static char randomVal() {
        Random r = new Random();
        return (char) (r.nextInt(26) + 'a'); // Fix: Ensure random letters cover a-z
    }

    public static String encryptScytale(String key, int thickness) {
        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < key.length(); i++) {
            encrypted.append(key.charAt(i));
            for (int j = 0; j < thickness; j++) {
                encrypted.append(randomVal());
            }
        }
        return encrypted.toString();
    }

    public static String decryptScytale(String pass, int thickness) {
        StringBuilder decrypted = new StringBuilder();
        for (int i = 0; i < pass.length(); i += (thickness + 1)) { // Skip random characters
            decrypted.append(pass.charAt(i));
        }
        return decrypted.toString();
    }
}
